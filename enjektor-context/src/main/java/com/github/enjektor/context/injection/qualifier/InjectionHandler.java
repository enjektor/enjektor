package com.github.enjektor.context.injection.qualifier;

import com.github.enjektor.context.ApplicationContext;

import java.lang.reflect.Field;

public interface InjectionHandler {
    void handle(final ApplicationContext applicationContext, Object object, Field field) throws IllegalAccessException, InstantiationException;

    void inject(final ApplicationContext applicationContext, Object object, Field field) throws IllegalAccessException, InstantiationException;
}
