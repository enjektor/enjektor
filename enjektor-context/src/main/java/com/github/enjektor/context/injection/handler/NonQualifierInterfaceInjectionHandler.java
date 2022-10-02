package com.github.enjektor.context.injection.handler;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.RecursiveFieldInjectionManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.util.NamingUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NonQualifierInterfaceInjectionHandler implements InjectionHandler {

    private final Reflections reflections;
    private final RecursiveFieldInjectionManager recursiveFieldInjectionManager;

    public NonQualifierInterfaceInjectionHandler(final Reflections reflections,
                                                 final RecursiveFieldInjectionManager recursiveFieldInjectionManager) {
        this.reflections = reflections;
        this.recursiveFieldInjectionManager = recursiveFieldInjectionManager;
    }

    @Override
    public void handle(final ApplicationContext applicationContext,
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

    @Override
    public void inject(final ApplicationContext applicationContext,
                       final Object object,
                       final Field field) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final Class<?> type = field.getType();
        final String value = inject.value() != null ? inject.value() : NamingUtils.beanCase(type.getSimpleName());

        final Set<Class<?>> implementations = reflections.getSubTypesOf((Class<Object>) type);

        if (implementations.size() == 1) {
            Class<?> beanType = null;
            for (Class<?> implementation : implementations) {
                beanType = implementation;
                break;
            }

            final Bean bean = applicationContext.getNativeBean(beanType);
            final Object beanInstance = bean.getDependency(value);
            field.set(object, beanInstance);
            recursiveFieldInjectionManager.inject(beanInstance);
        } else {
            final Map<String, Class<?>> map = new HashMap<>(implementations.size());

            for (final Class<?> implementation : implementations) {
                final String key = NamingUtils.beanCase(implementation.getSimpleName()).intern();
                map.put(key, implementation);
            }

            final Class<?> beanType = map.get(value);
            final Bean bean = applicationContext.getNativeBean(beanType);
            final Object beanInstance = bean.getDependency(value);
            field.set(object, beanInstance);
            recursiveFieldInjectionManager.inject(beanInstance);
        }
    }


}
