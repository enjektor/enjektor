package com.github.enjektor.context.consumer;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.core.annotations.Dependency;

import java.util.Set;
import java.util.function.Consumer;

public class BeanConsumer implements Consumer<Class<?>> {
    private final Bean bean;
    private final Set<Class<?>> scannedClassTypes;

    public BeanConsumer(final Bean bean,
                        final Set<Class<?>> scannedClassTypes) {
        this.bean = bean;
        this.scannedClassTypes = scannedClassTypes;
    }

    @Override
    public void accept(final Class<?> classType) {
        final Dependency dependency = classType.getDeclaredAnnotation(Dependency.class);
        if (dependency.name().isEmpty()) bean.register(classType);
        else bean.register(classType, dependency.name());
        scannedClassTypes.add(classType);
    }
}
