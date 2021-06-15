package com.github.enjektor.context.consumer;

import com.github.enjektor.context.injection.InjectionManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.BeanManager;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class BeanInstantiateBiConsumer implements BiConsumer<Class<?>, Bean>, BeanManager {

    private final InjectionManager injectionManager;

    public BeanInstantiateBiConsumer(final InjectionManager injectionManager) {
        this.injectionManager = injectionManager;
    }

    @Override
    public void accept(final Class<?> klass, final Bean bean) {
        final TByteObjectMap<Object> instancesOnRuntime = bean.getInstancesOnRuntime();

        for (Object runtimeInstance : instancesOnRuntime.values()) {
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

    @Override
    public void manage(Object object,
                       Field field) throws IllegalAccessException, InstantiationException {
        Inject inject = field.getAnnotation(Inject.class);
        final byte isSetAnyQualifier = (byte) (inject.qualifier() != UnsetQualifier.class ? 0x1 : 0x0);
        injectionManager.manage(isSetAnyQualifier, object, field);
    }
}
