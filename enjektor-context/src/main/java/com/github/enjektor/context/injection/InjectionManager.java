package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.behaviour.InjectionBehaviour;
import com.github.enjektor.context.injection.behaviour.NonQualifierInjectionBehaviour;
import com.github.enjektor.context.injection.behaviour.QualifierInjectionBehaviour;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.Map;

public class InjectionManager {

    private static final InjectionBehaviour[] injectionBehaviours =
        new InjectionBehaviour[]{new NonQualifierInjectionBehaviour(), new QualifierInjectionBehaviour()};
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, Bean> beans;

    public InjectionManager(ApplicationContext applicationContext,
                            Map<Class<?>, Bean> beans) {
        this.applicationContext = applicationContext;
        this.beans = beans;
    }

    public void manage(byte isSetAnyQualifier,
                       Object object,
                       Field field) throws IllegalAccessException, InstantiationException {
        injectionBehaviours[isSetAnyQualifier].act(object, field, applicationContext);
    }

}
