package com.github.enjektor.context.injector;

import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.context.injector.strategy.InjectStrategy;
import com.github.enjektor.context.injector.strategy.QualifierInjectStrategy;
import com.github.enjektor.core.scanner.field.FieldScanner;
import com.github.enjektor.core.scanner.field.InjectAnnotationFieldScanner;
import com.github.enjektor.core.util.NamingUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class RecursiveFieldInjector implements Injector {

    private static final FieldScanner fieldScanner = new InjectAnnotationFieldScanner();
    private final Map<Class<?>, Bean> beanHolderMap;
    private final InjectStrategy injectStrategy;

    public RecursiveFieldInjector(Map<Class<?>, Bean> beanHolderMap) {
        this.beanHolderMap = beanHolderMap;
        this.injectStrategy = new QualifierInjectStrategy(this);
    }

    @Override
    public final void inject(final Object object) throws IllegalAccessException {
        final Set<Field> allFieldsThatAnnotatedByInject = fieldScanner.scan(object.getClass());
        if (allFieldsThatAnnotatedByInject.size() == 0) return;

        for (final Field field : allFieldsThatAnnotatedByInject) {
            field.setAccessible(true);
            final Class<?> fieldClassType = field.getType();
            final Bean bean = beanHolderMap.get(fieldClassType);

            if (fieldClassType.isInterface()) {
                injectStrategy.inject(bean, object, field);
            } else {
                final String beanName = NamingUtils.beanCase(fieldClassType.getSimpleName());
                final Object beanInstance = bean.getDependency(beanName);
                field.set(object, beanInstance);
                inject(beanInstance);
            }
        }
    }
}
