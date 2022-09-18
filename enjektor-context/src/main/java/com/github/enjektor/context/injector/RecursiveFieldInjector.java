package com.github.enjektor.context.injector;

import com.github.enjektor.context.injector.strategy.InjectStrategy;
import com.github.enjektor.context.injector.strategy.NonQualifierInjectStrategy;
import com.github.enjektor.context.injector.strategy.QualifierInjectStrategy;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import com.github.enjektor.core.scanner.field.FieldScanner;
import com.github.enjektor.core.scanner.field.InjectAnnotationFieldScanner;
import com.github.enjektor.core.util.NamingUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class RecursiveFieldInjector implements Injector {

    private static final byte INJECTOR_STRATEGIES_INITIAL_SIZE = 2;
    private static final byte INJECTOR_STRATEGIES_NON_QUALIFIER = 0;
    private static final byte INJECTOR_STRATEGIES_QUALIFIER = 1;
    private final InjectStrategy[] injectStrategies = new InjectStrategy[INJECTOR_STRATEGIES_INITIAL_SIZE];

    private final Map<Class<?>, Bean> beans;
    private final FieldScanner fieldScanner;

    public RecursiveFieldInjector(final Map<Class<?>, Bean> beans,
                                  final FieldScanner fieldScanner) {
        this.beans = beans;
        this.fieldScanner = fieldScanner;
        init();
    }

    private void init() {
        injectStrategies[INJECTOR_STRATEGIES_NON_QUALIFIER] = new NonQualifierInjectStrategy(this);
        injectStrategies[INJECTOR_STRATEGIES_QUALIFIER] = new QualifierInjectStrategy(this);
    }

    @Override
    public final void inject(final Object object) throws IllegalAccessException {
        final Set<Field> allFieldsThatAnnotatedByInject = fieldScanner.scan(object.getClass());
        if (allFieldsThatAnnotatedByInject.size() == 0) return;

        for (final Field field : allFieldsThatAnnotatedByInject) {
            final Class<?> fieldClassType = field.getType();
            final Bean bean = beans.get(fieldClassType);
            final Inject inject = field.getAnnotation(Inject.class);
            final byte isSetAnyQualifier = (byte) (inject.qualifier() != UnsetQualifier.class ? 0x1 : 0x0);

            if (fieldClassType.isInterface()) {
                injectStrategies[isSetAnyQualifier].inject(bean, object, field);
            } else {
                final String beanName = NamingUtils.beanCase(fieldClassType.getSimpleName());
                final Object beanInstance = bean.getDependency(beanName);
                field.set(object, beanInstance);
                inject(beanInstance);
            }
        }
    }
}
