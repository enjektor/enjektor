package com.github.enjektor.context.consumer;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.bean.BeanManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.annotations.Qualifier;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiConsumer;

public class BeanInstantiateBiConsumer implements BiConsumer<Class<?>, Bean>, BeanManager {

    private final ApplicationContext applicationContext;

    public BeanInstantiateBiConsumer(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void accept(final Class<?> klass, final Bean bean) {
        final Map<String, Object> instancesOnRuntime = bean.getInstancesOnRuntime();
        for (final Map.Entry<String, Object> beanObjectEntry : instancesOnRuntime.entrySet()) {
            final Object runtimeInstance = beanObjectEntry.getValue();
            final Field[] declaredFields = runtimeInstance.getClass().getDeclaredFields();
            for (final Field declaredField : declaredFields) {
                try {
                    manage(runtimeInstance, declaredField);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void manage(Object object,
                       Field field) throws IllegalAccessException, InstantiationException {
        final Class<?> type = field.getType();
        final int modifier = field.getModifiers();

        field.setAccessible(true);
        if (modifier == 18) {
            if (field.isAnnotationPresent(Qualifier.class)) {
                final Qualifier qualifier = field.getAnnotation(Qualifier.class);
                final Object fieldInstance = applicationContext.getBean(type, qualifier.value());
                field.set(object, fieldInstance);
            } else {
                final Object fieldInstance = applicationContext.getBean(type);
                field.set(object, fieldInstance);
            }
        }

        if (field.isAnnotationPresent(Inject.class)) {
            if (field.isAnnotationPresent(Qualifier.class)) {
                final Qualifier qualifier = field.getAnnotation(Qualifier.class);
                final Object fieldInstance = applicationContext.getBean(type, qualifier.value());
                field.set(object, fieldInstance);
            } else {
                final Object fieldInstance = applicationContext.getBean(type);
                field.set(object, fieldInstance);
            }
        }
    }
}
