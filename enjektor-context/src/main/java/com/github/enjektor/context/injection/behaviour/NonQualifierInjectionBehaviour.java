package com.github.enjektor.context.injection.behaviour;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.collection.BooleanMap;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Map;

public class NonQualifierInjectionBehaviour implements InjectionBehaviour {

    private final Reflections reflections;
    private final Map<Boolean, InjectionBehaviour> nonQualifierInjectionBehaviorBooleanMap = new BooleanMap<>(InjectionBehaviour.class);

    public NonQualifierInjectionBehaviour(final Reflections reflections) {
        this.reflections = reflections;
        init();
    }

    private void init() {
        nonQualifierInjectionBehaviorBooleanMap.put(true, new NonQualifierInterfaceInjectionBehaviour(reflections));
        nonQualifierInjectionBehaviorBooleanMap.put(false, new NonQualifierImplementationInjectionBehaviour());
    }

    @Override
    public void act(final ApplicationContext applicationContext,
                    final Object object,
                    final Field field) throws IllegalAccessException, InstantiationException {
        final Class<?> type = field.getType();
        final boolean isFieldInterface = type.isInterface();
        nonQualifierInjectionBehaviorBooleanMap.get(isFieldInterface).act(applicationContext, object, field);
    }
}
