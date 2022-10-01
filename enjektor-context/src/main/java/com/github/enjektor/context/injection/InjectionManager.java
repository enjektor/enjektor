package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.behaviour.InjectionBehaviour;
import com.github.enjektor.context.injection.behaviour.NonQualifierInjectionBehaviour;
import com.github.enjektor.context.injection.behaviour.QualifierInjectionBehaviour;
import org.reflections.Reflections;

import java.lang.reflect.Field;

public final class InjectionManager {

    private static final byte INITIAL_CAPACITY = (byte) 0x2;
    private static final byte NON_QUALIFIER_BEHAVIOUR = 0x0;
    private static final byte QUALIFIER_BEHAVIOUR = 0x1;

    private static final InjectionBehaviour[] injectionBehaviours = new InjectionBehaviour[INITIAL_CAPACITY];
    private final ApplicationContext applicationContext;
    private final Reflections reflections;

    public InjectionManager(final ApplicationContext applicationContext,
                            final Reflections reflections) {
        this.applicationContext = applicationContext;
        this.reflections = reflections;
        init();
    }

    private void init() {
        injectionBehaviours[NON_QUALIFIER_BEHAVIOUR] = new NonQualifierInjectionBehaviour(reflections);
        injectionBehaviours[QUALIFIER_BEHAVIOUR] = new QualifierInjectionBehaviour();
    }

    public void manage(final byte isSetAnyQualifier,
                       final Object object,
                       final Field field) throws IllegalAccessException, InstantiationException {
        injectionBehaviours[isSetAnyQualifier].act(applicationContext, object, field);
    }

}
