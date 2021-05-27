package com.github.enjektor.context.consumer;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.bean.BeanManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.qualifier.UnsetQualifier;

import java.lang.reflect.Field;
import java.util.Map;
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
            final Class<?> qualifier = inject.qualifier();
            final Object beanInstance = applicationContext.getBean(qualifier);
            field.set(object, beanInstance);
        };

        final Cons nonQualifierCase = (object, field, applicationContext) -> {
            final Object bean = applicationContext.getBean(field.getType());
            field.set(object, bean);
        };

        qualifierInjectionBehaviour[0] = nonQualifierCase;
        qualifierInjectionBehaviour[1] = qualifierCase;
    }

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
        field.setAccessible(true);

        if (field.isAnnotationPresent(Inject.class)) {
            Inject inject = field.getAnnotation(Inject.class);
            final byte isSetAnyQualifier = (byte) (inject.qualifier() != UnsetQualifier.class ? 1 : 0);
            qualifierInjectionBehaviour[isSetAnyQualifier].accept(object, field, applicationContext);
        }
    }
}
