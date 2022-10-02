package com.github.enjektor.context.injection.qualifier;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.RecursiveFieldInjectionManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.Map;

public class NonQualifierImplementationInjectionHandler implements InjectionHandler {

    private final RecursiveFieldInjectionManager recursiveFieldInjectionManager;

    public NonQualifierImplementationInjectionHandler(final RecursiveFieldInjectionManager recursiveFieldInjectionManager) {
        this.recursiveFieldInjectionManager = recursiveFieldInjectionManager;
    }

    @Override
    public void handle(final ApplicationContext applicationContext,
                       final Object object,
                       final Field field) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final String value = inject.value();
        final Class<?> type = field.getType();
        final Object beanInstance = applicationContext.getBean(type, value);
        field.set(object, beanInstance);
    }

    @Override
    public void inject(final ApplicationContext applicationContext,
                       final Object object, Field field) throws IllegalAccessException, InstantiationException {
        final Class<?> type = field.getType();
        final Bean bean = applicationContext.getNativeBean(type);
        final Object dependency = bean.getSingularDependency();
        recursiveFieldInjectionManager.inject(dependency);
    }
}
