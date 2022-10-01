package com.github.enjektor.context.injection.behaviour;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.annotations.Inject;

import java.lang.reflect.Field;

public class NonQualifierImplementationInjectionBehaviour implements InjectionBehaviour {

    @Override
    public void act(final ApplicationContext applicationContext,
                    final Object object,
                    final Field field) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final String value = inject.value();
        final Class<?> type = field.getType();
        final Object beanInstance = applicationContext.getBean(type, value);
        field.set(object, beanInstance);
    }
}
