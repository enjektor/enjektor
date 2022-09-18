package com.github.enjektor.context.injector.strategy;

import com.github.enjektor.context.injector.Injector;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;

public class NonQualifierInjectStrategy implements InjectStrategy {

    private final Injector injector;

    public NonQualifierInjectStrategy(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public void inject(final Bean bean,
                       final Object object,
                       final Field field) throws IllegalAccessException {
        final Object dependency = bean.getSingularDependency();
        injector.inject(dependency);
    }
}
