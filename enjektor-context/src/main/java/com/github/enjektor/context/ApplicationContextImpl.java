package com.github.enjektor.context;

import com.github.enjektor.context.autowire.AutowireMechanism;
import com.github.enjektor.context.autowire.BasicAutowireMechanism;
import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.utils.NamingUtils;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {

    private static ApplicationContextImpl instance;
    private Map<Class<?>, Bean> applicationContext = new HashMap<>();
    private AutowireMechanism autowireMechanism = new BasicAutowireMechanism();

    private ApplicationContextImpl(final Class<?> mainClass) {
        final DependencyInitializer dependencyInitializer = new DefaultDependencyInitializer();
        dependencyInitializer.initialize(mainClass, applicationContext);
    }

    @Override
    public final <T> T getBean(final Class<T> classType) throws IllegalAccessException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public final <T> T getBean(final Class<T> classType,
                               final String fieldName) throws IllegalAccessException {
        final Bean bean = applicationContext.get(classType);
        final Object existObject = bean.getDependency(fieldName);

        autowireMechanism.autowire(existObject, applicationContext);
        return (T) existObject;
    }

    public static ApplicationContext getInstance(final Class<?> mainClass) {
        if (instance == null) instance = new ApplicationContextImpl(mainClass);
        return instance;
    }
}
