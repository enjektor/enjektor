package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.Map;

public interface InjectionStrategy {
    void inject(Object object,
                Field field,
                String value,
                ApplicationContext applicationContext) throws InstantiationException, IllegalAccessException;
}
