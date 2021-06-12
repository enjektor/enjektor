package com.github.enjektor.context.injector.strategy;

import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.context.injector.lambda.InjectionTriConsumer;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import com.github.enjektor.utils.NamingUtils;

import java.lang.reflect.Field;

public class QualifierInjectStrategy implements InjectStrategy {

    private final Injector injector;
    private static final byte INITIAL_CAPACITY = (byte) 0x2;
    private final InjectionTriConsumer[] injectionTriConsumerStrategy = new InjectionTriConsumer[INITIAL_CAPACITY];

    public QualifierInjectStrategy(Injector injector) {
        this.injector = injector;
        init();
    }

    private void init() {
        final InjectionTriConsumer qualifierInjection = (bean, object, field) -> {
            Inject inject = field.getAnnotation(Inject.class);
            final Class<?> qualifier = inject.qualifier();
            final String s = NamingUtils.beanCase(qualifier.getName());
            final Object beanInstance = bean.getDependency(s);
            injector.inject(beanInstance);
        };

        final InjectionTriConsumer nonQualifierInjection = (bean, object, field) -> {
            final Object dependency = bean.getDependency("");
            injector.inject(dependency);
        };

        injectionTriConsumerStrategy[0x0] = nonQualifierInjection;
        injectionTriConsumerStrategy[0x1] = qualifierInjection;
    }

    @Override
    public void inject(Bean bean, Object object, Field field) throws IllegalAccessException {
        final Inject inject = field.getAnnotation(Inject.class);
        final byte isSetAnyQualifier = (byte) (inject.qualifier() != UnsetQualifier.class ? 0x1 : 0x0);
        injectionTriConsumerStrategy[isSetAnyQualifier].accept(bean, object, field);
    }
}
