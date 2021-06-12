package com.github.enjektor.context;

import com.github.enjektor.context.consumer.BeanInstantiateBiConsumer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.utils.NamingUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class PrimitiveApplicationContext implements ApplicationContext, DeAllocationHandler {

    private Map<Class<?>, Bean> beans;
    private ApplicationContext applicationContext;

    public PrimitiveApplicationContext(final Class<?> mainClass,
                                       final List<DependencyInitializer> dependencyInitializers,
                                       final Map<Class<?>, Bean> beans) {
        this.beans = beans;
        this.applicationContext = new DefaultApplicationContext(mainClass, beans, dependencyInitializers);
        init();
    }

    @Override
    public void init() {
        final BiConsumer<Class<?>, Bean> beanBiConsumer = new BeanInstantiateBiConsumer(applicationContext);
        beans.forEach(beanBiConsumer);
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
        final Bean bean = beans.get(classType);
        final Object existObject = bean.getDependency(fieldName);
        return (T) existObject;
    }

    @Override
    public void clean() {
        applicationContext.destroy();
        applicationContext = null;
        beans.forEach((k, ignored) -> k = null);
        beans = null;
    }
}
