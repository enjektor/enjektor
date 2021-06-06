package com.github.enjektor.context;

import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.context.injector.RecursiveFieldInjector;
import com.github.enjektor.utils.NamingUtils;

import java.util.List;
import java.util.Map;

public class DefaultApplicationContext implements ApplicationContext, DeAllocationHandler {

    private final Map<Class<?>, Bean> beanHashMap;
    private Injector recursiveInjector;
    private List<DependencyInitializer> dependencyInitializers;

    public DefaultApplicationContext(final Class<?> mainClass,
                                     final Map<Class<?>, Bean> beanHashMap,
                                     final List<DependencyInitializer> dependencyInitializers) {
        this.beanHashMap = beanHashMap;
        this.recursiveInjector = new RecursiveFieldInjector(beanHashMap);
        this.dependencyInitializers = dependencyInitializers;
        init(mainClass);
    }

    private void init(final Class<?> mainClass) {
        for (final DependencyInitializer dependencyInitializer : dependencyInitializers) {
            beanHashMap.putAll(dependencyInitializer.initialize(mainClass));
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {
        clean();
    }

    @Override
    public <T> T getBean(final Class<T> classType) throws IllegalAccessException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException {
        final Bean bean = beanHashMap.get(classType);
        final Object existObject = bean.getDependency(fieldName);

        recursiveInjector.inject(existObject);
        return (T) existObject;
    }

    @Override
    public void clean() {
        recursiveInjector = null;
        dependencyInitializers = null;
    }
}
