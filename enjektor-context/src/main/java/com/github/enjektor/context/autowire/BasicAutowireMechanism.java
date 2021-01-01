package com.github.enjektor.context.autowire;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.core.annotations.Qualifier;
import com.github.enjektor.core.scanner.FieldScanner;
import com.github.enjektor.core.scanner.FieldScannerImpl;
import com.github.enjektor.utils.NamingUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class BasicAutowireMechanism implements AutowireMechanism {

    private final static FieldScanner fieldScanner = new FieldScannerImpl();

    @Override
    public final void autowire(final Object object,
                               final Map<Class<?>, Bean> applicationContext) throws IllegalAccessException {
        final Set<Field> allFieldsThatAnnotatedByInject = fieldScanner.scan(object.getClass());
        if (allFieldsThatAnnotatedByInject.size() == 0) return;

        for (final Field field : allFieldsThatAnnotatedByInject) {
            final Class<?> fieldClassType = field.getType();
            final Bean bean = applicationContext.get(fieldClassType);

            field.setAccessible(true);
            if (fieldClassType.isInterface()) {
                if (field.isAnnotationPresent(Qualifier.class)) {
                    final Qualifier qualifier = field.getAnnotation(Qualifier.class);
                    final Object beanInstance = bean.getDependency(qualifier.value());
                    field.set(object, beanInstance);
                    autowire(beanInstance, applicationContext);
                } else {
                    final Object beanInstance = bean.getDependency("");
                    field.set(object, beanInstance);
                    autowire(beanInstance, applicationContext);
                }
            } else {
                final String beanName = NamingUtils.beanCase(fieldClassType.getSimpleName());
                final Object beanInstance = bean.getDependency(beanName);
                field.set(object, beanInstance);
                autowire(beanInstance, applicationContext);
            }
        }
    }
}
