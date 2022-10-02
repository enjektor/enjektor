package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.qualifier.NonQualifierImplementationInjectionHandler;
import com.github.enjektor.context.injection.qualifier.NonQualifierInterfaceInjectionHandler;
import com.github.enjektor.context.injection.qualifier.QualifierInjectionHandler;
import com.github.enjektor.context.injection.qualifier.InjectionHandler;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.qualifier.UnsetQualifier;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import org.reflections.Reflections;

import java.lang.reflect.Field;

public final class QualifierInjectionManager {

    private static final byte INITIAL_CAPACITY = (byte) 4;

    private static final byte QUALIFIER_FLAG = (byte) 8;
    private static final byte NON_QUALIFIER_FLAG = (byte) 0;

    private static final byte NON_QUALIFIER_AND_INTERFACE_FLAG = (byte) 2;
    private static final byte NON_QUALIFIER_AND_IMPLEMENTATION_FLAG = (byte) 3;

    private static final byte QUALIFIER_AND_IMPLEMENTATION_FLAG = (byte) 10;
    private static final byte QUALIFIER_AND_INTERFACE_FLAG = (byte) 11;

    private static final TByteObjectMap<InjectionHandler> behaviours = new TByteObjectHashMap<>(INITIAL_CAPACITY);
    private final ApplicationContext applicationContext;
    private final Reflections reflections;

    public QualifierInjectionManager(final ApplicationContext applicationContext,
                                     final Reflections reflections) {
        this.applicationContext = applicationContext;
        this.reflections = reflections;
        init();
    }

    private void init() {
        final InjectionHandler qualifierInjectionBehaviour = new QualifierInjectionHandler(null);
        final InjectionHandler nonQualifierImplementationInjectionBehaviour = new NonQualifierImplementationInjectionHandler(null);
        final InjectionHandler nonQualifierInterfaceInjectionBehaviour = new NonQualifierInterfaceInjectionHandler(reflections, null);

        behaviours.put(NON_QUALIFIER_AND_IMPLEMENTATION_FLAG, nonQualifierImplementationInjectionBehaviour);
        behaviours.put(NON_QUALIFIER_AND_INTERFACE_FLAG, nonQualifierInterfaceInjectionBehaviour);
        behaviours.put(QUALIFIER_AND_IMPLEMENTATION_FLAG, qualifierInjectionBehaviour);
        behaviours.put(QUALIFIER_AND_INTERFACE_FLAG, qualifierInjectionBehaviour);
    }

    /**
     * Qualifier:
     * 8 | 2 = 10
     * 8 | 3 = 11
     * <p>
     * ----------
     * Non Qualifier:
     * 0 | 2 = 2
     * 0 | 3 = 3
     */

    public void manage(final Object object,
                       final Field field) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final Class<?> type = field.getType();
        final byte interfaceValue = type.isInterface() ? NON_QUALIFIER_AND_INTERFACE_FLAG : NON_QUALIFIER_AND_IMPLEMENTATION_FLAG;

        byte flag = (byte) (NON_QUALIFIER_FLAG | interfaceValue);
        if (inject.qualifier() != UnsetQualifier.class) flag |= QUALIFIER_FLAG;

        behaviours.get(flag).handle(applicationContext, object, field);
    }
}
