package com.github.enjektor.context;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.context.injector.RecursiveInjector;
import com.github.enjektor.utils.NamingUtils;

import java.util.Map;

public class DefaultApplicationContext implements ApplicationContext {

    private final Class<?> mainClass;
    private final Map<Class<?>, Bean> applicationContextMap;
    private final DependencyInitializer dependencyInitializer;
    private final Injector recursiveInjector;

    public DefaultApplicationContext(final Class<?> mainClass,
                                     final Map<Class<?>, Bean> applicationContextMap) {
        this.applicationContextMap = applicationContextMap;
        this.mainClass = mainClass;
        dependencyInitializer = new DefaultDependencyInitializer(applicationContextMap);
        recursiveInjector = new RecursiveInjector(applicationContextMap);
        initialize();
    }

    private void initialize() {
        dependencyInitializer.initialize(mainClass);
    }

    @Override
    public <T> T getBean(Class<T> classType) throws IllegalAccessException, InstantiationException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public <T> T getBean(Class<T> classType, String fieldName) throws IllegalAccessException, InstantiationException {
        final Bean bean = applicationContextMap.get(classType);
        final Object existObject = bean.getDependency(fieldName);

        recursiveInjector.inject(existObject);
        return (T) existObject;
    }

}
