package com.github.enjektor.context.injector.strategy;

import com.github.enjektor.context.bean.Bean;

import java.lang.reflect.Field;

public interface InjectStrategy {
    void inject(Bean bean, Object object, Field field) throws IllegalAccessException;
}
