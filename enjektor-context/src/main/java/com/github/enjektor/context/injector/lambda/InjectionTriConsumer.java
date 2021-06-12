package com.github.enjektor.context.injector.lambda;

import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;

@FunctionalInterface
public interface InjectionTriConsumer {
    void accept(Bean bean, Object object, Field field) throws IllegalAccessException;
}
