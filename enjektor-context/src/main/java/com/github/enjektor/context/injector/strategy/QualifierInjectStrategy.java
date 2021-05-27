package com.github.enjektor.context.injector.strategy;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.context.injector.lambda.InjectionTriConsumer;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import com.github.enjektor.utils.NamingUtils;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Field;

public class QualifierInjectStrategy implements InjectStrategy {

    private final Injector injector;
    private final TByteObjectMap<InjectionTriConsumer> injectionTriConsumerStrategy = new TByteObjectHashMap<>(2);

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

        injectionTriConsumerStrategy.put((byte) 0, nonQualifierInjection);
        injectionTriConsumerStrategy.put((byte) 1, qualifierInjection);
    }

    @Override
    public void inject(Bean bean, Object object, Field field) throws IllegalAccessException {
        final Inject inject = field.getAnnotation(Inject.class);
        final byte isSetAnyQualifier = (byte) (inject.qualifier() != UnsetQualifier.class ? 1 : 0);
        injectionTriConsumerStrategy.get(isSetAnyQualifier).accept(bean, object, field);
    }
}
