package com.github.enjektor.context.consumer;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.BeanManager;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public class BeanInstantiateBiConsumer implements BiConsumer<Class<?>, Bean>, BeanManager {

    @FunctionalInterface
    interface Cons {
        void accept(Object object, Field field, ApplicationContext applicationContext) throws InstantiationException, IllegalAccessException;
    }

    private final ApplicationContext applicationContext;
    private static final byte INITIAL_CAPACITY = (byte) 0x2;
    private static final Cons[] qualifierInjectionBehaviour = new Cons[INITIAL_CAPACITY];

    static {
        final Cons qualifierCase = (object, field, applicationContext) -> {
            final Inject inject = field.getAnnotation(Inject.class);
            final String value = inject.value();
            if (value.isEmpty()) {
                final Class<?> qualifier = inject.qualifier();
                final Object beanInstance = applicationContext.getBean(qualifier);
                field.set(object, beanInstance);
            } else {
                final Class<?> qualifier = inject.qualifier();
                final Object beanInstance = applicationContext.getBean(qualifier, value);
                field.set(object, beanInstance);
            }
        };

        final Cons nonQualifierCase = (object, field, applicationContext) -> {
            final Inject inject = field.getAnnotation(Inject.class);
            final String value = inject.value();
            if (value.isEmpty()) {
                final Object bean = applicationContext.getBean(field.getType());
                field.set(object, bean);
            } else {
                final Object bean = Optional.ofNullable(applicationContext.getBean(field.getType(), value))
                    .orElseThrow(() -> new IllegalArgumentException("There is no such property ${" + value + "} in your configuration file."));
                field.set(object, bean);
            }
        };

        qualifierInjectionBehaviour[0x0] = nonQualifierCase;
        qualifierInjectionBehaviour[0x1] = qualifierCase;
    }

    public BeanInstantiateBiConsumer(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void accept(final Class<?> klass, final Bean bean) {
        final TByteObjectMap<Object> instancesOnRuntime = bean.getInstancesOnRuntime();
        for (Object runtimeInstance : instancesOnRuntime.values()) {
            final Field[] declaredFields = runtimeInstance.getClass().getDeclaredFields();
            Arrays
                .stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        manage(runtimeInstance, field);
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
        }

//        for (final Map.Entry<String, Object> beanObjectEntry : instancesOnRuntime.entrySet()) {
//            final Object runtimeInstance = beanObjectEntry.getValue();
//
//        }
    }

    @Override
    public void manage(Object object,
                       Field field) throws IllegalAccessException, InstantiationException {
        Inject inject = field.getAnnotation(Inject.class);
        final byte isSetAnyQualifier = (byte) (inject.qualifier() != UnsetQualifier.class ? 0x1 : 0x0);
        qualifierInjectionBehaviour[isSetAnyQualifier].accept(object, field, applicationContext);
    }
}
