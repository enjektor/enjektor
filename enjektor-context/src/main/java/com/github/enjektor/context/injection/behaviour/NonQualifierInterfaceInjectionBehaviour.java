package com.github.enjektor.context.injection.behaviour;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.annotations.Inject;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NonQualifierInterfaceInjectionBehaviour implements InjectionBehaviour {

    private final Reflections reflections;

    public NonQualifierInterfaceInjectionBehaviour(final Reflections reflections) {
        this.reflections = reflections;
    }

    @Override
    public void act(final ApplicationContext applicationContext,
                    final Object object,
                    final Field field) throws IllegalAccessException, InstantiationException {

        final Inject inject = field.getAnnotation(Inject.class);
        final String value = inject.value();

        final Class<?> type = field.getType();
        final Set<Class<?>> implementations = reflections.getSubTypesOf((Class<Object>) type);

        if (implementations.size() == 1) {
            Class<?> beanType = null;
            for (Class<?> implementation : implementations) {
                beanType = implementation;
                break;
            }

            final Object beanInstance = applicationContext.getBean(beanType);
            field.set(object, beanInstance);
        } else {
            final Map<String, Class<?>> map = new HashMap<>(implementations.size());

            for (final Class<?> implementation : implementations) {
                final String key = implementation.getSimpleName().toLowerCase().intern();
                map.put(key, implementation);
            }

            final Class<?> beanType = map.get(value);
            final Object beanInstance = applicationContext.getBean(beanType);
            field.set(object, beanInstance);
        }
    }
}
