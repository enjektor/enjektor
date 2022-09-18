package com.github.enjektor.context;

import com.github.enjektor.context.consumer.BeanInstantiateBiConsumer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.context.injection.InjectionManager;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.core.util.NamingUtils;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class PrimitiveApplicationContext implements ApplicationContext, DeAllocationHandler {

    private Map<Class<?>, Bean> beans = new WeakHashMap<>();
    private ApplicationContext applicationContext;

    public PrimitiveApplicationContext(final Class<?> mainClass,
                                       final List<DependencyInitializer> dependencyInitializers,
                                       final List<Pair> pairs) {
        this.applicationContext = new DefaultApplicationContext(mainClass, beans, dependencyInitializers);
        init(pairs);
    }

    @Override
    public void init(List<Pair> pairs) {
        for (Pair pair : pairs) beans.put(pair.getType(), pair.getBean());
        final InjectionManager injectionManager = new InjectionManager(applicationContext, beans);
        final BiConsumer<Class<?>, Bean> beanBiConsumer = new BeanInstantiateBiConsumer(injectionManager);
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
    public <T> Bean getNativeBean(Class<T> classType) throws IllegalAccessException, InstantiationException {
        return beans.get(classType);
    }

    @Override
    public void clean() {
        applicationContext.destroy();
        applicationContext = null;
        beans.forEach((k, ignored) -> k = null);
        beans = null;
    }
}
