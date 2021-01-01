package com.github.enjektor.context;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {

    private static ApplicationContextImpl instance;
    private Map<Class<?>, Bean> applicationContext = new HashMap<>();
    private Class<?> mainClass;

    private ApplicationContextImpl(final Class<?> mainClass) {
        final DependencyInitializer dependencyInitializer = new DefaultDependencyInitializer();
        dependencyInitializer.initialize(mainClass, applicationContext);
    }

    @Override
    public final <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public final <T> T getBean(final Class<T> classType,
                               final String fieldName) throws IllegalAccessException, InstantiationException {
        return null;
    }

    public static ApplicationContext getInstance(final Class<?> mainClass) {
        if (instance == null) instance = new ApplicationContextImpl(mainClass);
        return instance;
    }
}
