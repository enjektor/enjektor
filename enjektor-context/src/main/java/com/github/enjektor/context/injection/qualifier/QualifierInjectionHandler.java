package com.github.enjektor.context.injection.qualifier;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.RecursiveFieldInjectionManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.util.NamingUtils;

import java.lang.reflect.Field;

public class QualifierInjectionHandler implements InjectionHandler {

    private final RecursiveFieldInjectionManager recursiveFieldInjectionManager;

    public QualifierInjectionHandler(final RecursiveFieldInjectionManager recursiveFieldInjectionManager) {
        this.recursiveFieldInjectionManager = recursiveFieldInjectionManager;
    }

    @Override
    public void handle(final ApplicationContext applicationContext,
                       final Object object,
                       final Field field) throws IllegalAccessException, InstantiationException {
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

    @Override
    public void inject(final ApplicationContext applicationContext,
                       final Object object,
                       final Field field) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final Class<?> qualifier = inject.qualifier();
        final String s = NamingUtils.beanCase(qualifier.getName());
        final Bean bean = applicationContext.getNativeBean(qualifier);
        final Object beanInstance = bean.getDependency(s);
        recursiveFieldInjectionManager.inject(beanInstance);
    }
}
