package com.github.enjektor.context.consumer;

import com.github.enjektor.context.injection.InjectionManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.BiConsumer;

public final class BeanInstantiateBiConsumer implements BiConsumer<Class<?>, Bean> {

    private static final byte NON_QUALIFIER_BEHAVIOUR = 0x0;
    private static final byte QUALIFIER_BEHAVIOUR = 0x1;

    private final InjectionManager injectionManager;

    public BeanInstantiateBiConsumer(final InjectionManager injectionManager) {
        this.injectionManager = injectionManager;
    }

    @Override
    public void accept(final Class<?> klass,
                       final Bean bean) {
        final TByteObjectMap<Object> instancesOnRuntime = bean.getInstancesOnRuntime();

        for (final Object runtimeInstance : instancesOnRuntime.values()) {
            final Field[] declaredFields = runtimeInstance.getClass().getDeclaredFields();
            Arrays
                .stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        manage(runtimeInstance, field);
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    private void manage(final Object object,
                        final Field field) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final byte isSetAnyQualifier = inject.qualifier() != UnsetQualifier.class ? QUALIFIER_BEHAVIOUR : NON_QUALIFIER_BEHAVIOUR;
        injectionManager.manage(isSetAnyQualifier, object, field);
    }
}
