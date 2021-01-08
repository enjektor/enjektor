package com.github.enjektor.context;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.context.injector.RecursiveInjector;
import com.github.enjektor.utils.NamingUtils;

import java.util.Map;

public class DefaultApplicationContext implements ApplicationContext {

    private final Map<Class<?>, Bean> applicationContextMap;
    private final Injector recursiveInjector;

    public DefaultApplicationContext(final Class<?> mainClass,
                                     final Map<Class<?>, Bean> applicationContextMap) {
        this.applicationContextMap = applicationContextMap;
        recursiveInjector = new RecursiveInjector(applicationContextMap);
        final DependencyInitializer dependencyInitializer = new DefaultDependencyInitializer(applicationContextMap);
        dependencyInitializer.initialize(mainClass);
    }

    @Override
    public <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException {
        final Bean bean = applicationContextMap.get(classType);
        final Object existObject = bean.getDependency(fieldName);

        recursiveInjector.inject(existObject);
        return (T) existObject;
    }

}
