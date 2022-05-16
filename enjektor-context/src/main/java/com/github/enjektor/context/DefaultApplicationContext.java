package com.github.enjektor.context;

import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.context.injector.RecursiveFieldInjector;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.core.util.NamingUtils;

import java.util.List;
import java.util.Map;

public class DefaultApplicationContext implements ApplicationContext, DeAllocationHandler {

    private static final byte STRING_TYPE = (byte) 1;
    private static final byte OBJECT_TYPE = (byte) 0;
    private static final byte NULL_CASE = (byte) 0;
    private static final byte NON_NULL_CASE = (byte) 1;

    private final Map<Class<?>, Bean> beanHashMap;
    private Injector recursiveInjector;

    public DefaultApplicationContext(final Class<?> mainClass,
                                     final Map<Class<?>, Bean> beanHashMap,
                                     final List<DependencyInitializer> dependencyInitializers) {
        this.beanHashMap = beanHashMap;
        this.recursiveInjector = new RecursiveFieldInjector(beanHashMap);
        init(mainClass, dependencyInitializers);
    }

    private void init(final Class<?> mainClass, List<DependencyInitializer> dependencyInitializers) {
        for (final DependencyInitializer dependencyInitializer : dependencyInitializers)
            beanHashMap.putAll(dependencyInitializer.initialize(mainClass));
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {
        clean();
    }

    @Override
    public void putDependency(Pair pair) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getBean(final Class<T> classType) throws IllegalAccessException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException {
        final Bean bean = beanHashMap.get(classType);
        final byte stringOrNot = classType.equals(String.class) ? STRING_TYPE : OBJECT_TYPE;
        final byte nullOrNot = bean != null ? NON_NULL_CASE : NULL_CASE;

        switch (nullOrNot) {
            case NULL_CASE:
                switch (stringOrNot) {
                    case STRING_TYPE:
                        throw new IllegalArgumentException("There is no such property ${" + fieldName + "} in your configuration file.");
                    case OBJECT_TYPE:
                        final String s = NamingUtils.reverseBeanCase(classType.getSimpleName());
                        throw new IllegalArgumentException("There is no instance of \" " + s + " \" class in Enjektor IoC container. Make sure that you create any instance from this class with @Dependency or @Dependencies");
                }
                break;
        }

        final Object existObject = bean.getDependency(fieldName);

        recursiveInjector.inject(existObject);
        return (T) existObject;
    }

    @Override
    public void clean() {
        recursiveInjector = null;
    }
}
