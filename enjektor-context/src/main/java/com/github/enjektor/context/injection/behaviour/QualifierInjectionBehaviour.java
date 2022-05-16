package com.github.enjektor.context.injection.behaviour;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.Map;

public class QualifierInjectionBehaviour implements InjectionBehaviour {

    @Override
    public void act(Object object,
                    Field field,
                    ApplicationContext applicationContext) throws IllegalAccessException, InstantiationException {
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
    }
}
