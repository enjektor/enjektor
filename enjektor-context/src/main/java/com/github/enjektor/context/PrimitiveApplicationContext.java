package com.github.enjektor.context;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.consumer.BeanInstantiateBiConsumer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.utils.NamingUtils;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class PrimitiveApplicationContext implements ApplicationContext, DeAllocationHandler {

    private Map<Class<?>, Bean> beanHashMap = new WeakHashMap<>();
    private ApplicationContext applicationContext;

    public PrimitiveApplicationContext(final Class<?> mainClass,
                                       final List<DependencyInitializer> dependencyInitializers) {
        this.applicationContext = new DefaultApplicationContext(mainClass, beanHashMap, dependencyInitializers);
        init();
    }

    @Override
    public void init() {
        final BiConsumer<Class<?>, Bean> beanBiConsumer = new BeanInstantiateBiConsumer(applicationContext);
        beanHashMap.forEach(beanBiConsumer);
    }

    @Override
    public void destroy() {
        clean();
    }

    @Override
    public final <T> T getBean(final Class<T> classType) throws IllegalAccessException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public final <T> T getBean(final Class<T> classType,
                               final String fieldName) throws IllegalAccessException {
        final Bean bean = beanHashMap.get(classType);
        final Object existObject = bean.getDependency(fieldName);
        return (T) existObject;
    }

    @Override
    public void clean() {
        applicationContext.destroy();
        applicationContext = null;
        beanHashMap.forEach((k, ignored) -> k = null);
        beanHashMap = null;
    }
}
