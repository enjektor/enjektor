package com.github.enjektor.context.injector;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.core.annotations.Qualifier;
import com.github.enjektor.core.scanner.field.FieldScanner;
import com.github.enjektor.core.scanner.field.InjectAnnotationFieldScanner;
import com.github.enjektor.utils.NamingUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class RecursiveFieldInjector implements Injector {

    private static final FieldScanner fieldScanner = new InjectAnnotationFieldScanner();
    private final Map<Class<?>, Bean> beanHolderMap;

    public RecursiveFieldInjector(Map<Class<?>, Bean> beanHolderMap) {
        this.beanHolderMap = beanHolderMap;
    }

    @Override
    public final void inject(final Object object) throws IllegalAccessException {
        final Set<Field> allFieldsThatAnnotatedByInject = fieldScanner.scan(object.getClass());
        if (allFieldsThatAnnotatedByInject.size() == 0) return;

        for (final Field field : allFieldsThatAnnotatedByInject) {
            final Class<?> fieldClassType = field.getType();
            final Bean bean = beanHolderMap.get(fieldClassType);

            field.setAccessible(true);
            if (fieldClassType.isInterface()) {
                if (field.isAnnotationPresent(Qualifier.class)) {
                    final Qualifier qualifier = field.getAnnotation(Qualifier.class);
                    final Object beanInstance = bean.getDependency(qualifier.value());
                    field.set(object, beanInstance);
                    inject(beanInstance);
                } else {
                    final Object beanInstance = bean.getDependency("");
                    field.set(object, beanInstance);
                    inject(beanInstance);
                }
            } else {
                final String beanName = NamingUtils.beanCase(fieldClassType.getSimpleName());
                final Object beanInstance = bean.getDependency(beanName);
                field.set(object, beanInstance);
                inject(beanInstance);
            }
        }
    }
}
