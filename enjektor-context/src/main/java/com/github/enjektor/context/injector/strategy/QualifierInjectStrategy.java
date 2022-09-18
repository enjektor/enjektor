package com.github.enjektor.context.injector.strategy;

import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.util.NamingUtils;

import java.lang.reflect.Field;

public class QualifierInjectStrategy implements InjectStrategy {

    private final Injector injector;

    public QualifierInjectStrategy(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public void inject(final Bean bean,
                       final Object object,
                       final Field field) throws IllegalAccessException {
        final Inject inject = field.getAnnotation(Inject.class);
        final Class<?> qualifier = inject.qualifier();
        final String s = NamingUtils.beanCase(qualifier.getName());
        final Object beanInstance = bean.getDependency(s);
        injector.inject(beanInstance);
    }
}
