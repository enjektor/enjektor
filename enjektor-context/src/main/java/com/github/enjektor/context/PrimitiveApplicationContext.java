package com.github.enjektor.context;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.consumer.BeanInstantiateBiConsumer;
import com.github.enjektor.utils.NamingUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class PrimitiveApplicationContext implements ApplicationContext {

    private static PrimitiveApplicationContext instance;
    private final Map<Class<?>, Bean> applicationContext = new HashMap<>();
    private final ApplicationContext defaultApplicationContext;

    private PrimitiveApplicationContext(final Class<?> mainClass) {
        defaultApplicationContext = new DefaultApplicationContext(mainClass, applicationContext);
        initialize();
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
        return (T) existObject;
    }

    public static ApplicationContext getInstance(final Class<?> mainClass) {
        if (instance == null) instance = new PrimitiveApplicationContext(mainClass);
        return instance;
    }

    private void initialize() {
        final BiConsumer<Class<?>, Bean> beanBiConsumer = new BeanInstantiateBiConsumer(defaultApplicationContext);
        applicationContext.forEach(beanBiConsumer);
    }

}
