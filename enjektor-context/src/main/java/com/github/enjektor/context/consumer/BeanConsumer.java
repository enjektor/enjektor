package com.github.enjektor.context.consumer;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.core.annotations.Dependency;

import java.util.function.Consumer;

public class BeanConsumer implements Consumer<Class<?>> {
    private final Bean bean;

    public BeanConsumer(final Bean bean) {
        this.bean = bean;
    }

    @Override
    public synchronized void accept(final Class<?> classType) {
        final Dependency dependency = classType.getDeclaredAnnotation(Dependency.class);
        if (dependency.name().isEmpty()) bean.register(classType);
        else bean.register(classType, dependency.name());
    }
}
