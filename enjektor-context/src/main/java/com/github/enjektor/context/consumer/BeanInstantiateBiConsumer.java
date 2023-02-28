package com.github.enjektor.context.consumer;

import com.github.enjektor.context.injection.QualifierInjectionManager;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

public final class BeanInstantiateBiConsumer implements BiConsumer<Class<?>, Bean> {

    private final QualifierInjectionManager qualifierInjectionManager;

    public BeanInstantiateBiConsumer(final QualifierInjectionManager qualifierInjectionManager) {
        this.qualifierInjectionManager = qualifierInjectionManager;
    }

    @Override
    public void accept(final Class<?> alass,
                       final Bean bean) {
        final TByteObjectMap<Object> instancesOnRuntime = bean.getInstancesOnRuntime();

        try {
            for (final Object runtimeInstance : instancesOnRuntime.values()) {
                final Field[] fields = runtimeInstance.getClass().getDeclaredFields();

                for (final Field field : fields)
                    if (field.isAnnotationPresent(Inject.class)) {
                        field.setAccessible(true);
                        qualifierInjectionManager.manage(runtimeInstance, field);
                    }
            }
        } catch (Exception ignored) {
        }
    }
}
