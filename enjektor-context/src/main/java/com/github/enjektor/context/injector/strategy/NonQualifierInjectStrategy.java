package com.github.enjektor.context.injector.strategy;

import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;

public class NonQualifierInjectStrategy implements InjectStrategy {

    @Override
    public void inject(Bean bean, Object object, Field field) throws IllegalAccessException {

    }
}
