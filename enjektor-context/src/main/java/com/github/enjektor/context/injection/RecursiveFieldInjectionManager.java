package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.handler.InjectionHandler;
import com.github.enjektor.context.injection.handler.NonQualifierImplementationInjectionHandler;
import com.github.enjektor.context.injection.handler.NonQualifierInterfaceInjectionHandler;
import com.github.enjektor.context.injection.handler.QualifierInjectionHandler;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import com.github.enjektor.core.reflection.scanner.field.FieldScanner;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

public class RecursiveFieldInjectionManager {

    private static final byte INITIAL_CAPACITY = (byte) 4;
    private static final byte QUALIFIER_FLAG = (byte) 8;
    private static final byte NON_QUALIFIER_FLAG = (byte) 0;

    private static final byte NON_QUALIFIER_AND_INTERFACE_FLAG = (byte) 2;
    private static final byte NON_QUALIFIER_AND_IMPLEMENTATION_FLAG = (byte) 3;

    private static final byte QUALIFIER_AND_IMPLEMENTATION_FLAG = (byte) 10;
    private static final byte QUALIFIER_AND_INTERFACE_FLAG = (byte) 11;

    private static final TByteObjectMap<InjectionHandler> handlers = new TByteObjectHashMap<>(INITIAL_CAPACITY);

    private final ApplicationContext applicationContext;
    private final FieldScanner fieldScanner;
    private final Reflections reflections;

    public RecursiveFieldInjectionManager(final ApplicationContext applicationContext,
                                          final FieldScanner fieldScanner,
                                          final Reflections reflections) {
        this.applicationContext = applicationContext;
        this.fieldScanner = fieldScanner;
        this.reflections = reflections;
        init();
    }

    private void init() {
        final InjectionHandler qualifierInjectionBehaviour = new QualifierInjectionHandler(this);
        final InjectionHandler nonQualifierImplementationInjectionBehaviour = new NonQualifierImplementationInjectionHandler(this);
        final InjectionHandler nonQualifierInterfaceInjectionBehaviour = new NonQualifierInterfaceInjectionHandler(reflections, this);

        handlers.put(NON_QUALIFIER_AND_IMPLEMENTATION_FLAG, nonQualifierImplementationInjectionBehaviour);
        handlers.put(NON_QUALIFIER_AND_INTERFACE_FLAG, nonQualifierInterfaceInjectionBehaviour);
        handlers.put(QUALIFIER_AND_IMPLEMENTATION_FLAG, qualifierInjectionBehaviour);
        handlers.put(QUALIFIER_AND_INTERFACE_FLAG, qualifierInjectionBehaviour);
    }

    public final void inject(final Object object) throws IllegalAccessException, InstantiationException {
        final Set<Field> allFieldsThatAnnotatedByInject = fieldScanner.scan(object.getClass());
        if (allFieldsThatAnnotatedByInject.size() == 0) return;

        for (final Field field : allFieldsThatAnnotatedByInject) {
            final Class<?> type = field.getType();
            final Inject inject = field.getAnnotation(Inject.class);
            final byte interfaceValue = type.isInterface() ? NON_QUALIFIER_AND_INTERFACE_FLAG : NON_QUALIFIER_AND_IMPLEMENTATION_FLAG;

            byte flag = (byte) (NON_QUALIFIER_FLAG | interfaceValue);
            if (inject.qualifier() != UnsetQualifier.class) flag |= QUALIFIER_FLAG;

            handlers.get(flag).inject(applicationContext, object, field);
        }
    }
}
