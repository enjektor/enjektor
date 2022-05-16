package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.Map;

public class DefaultInjectionStrategy implements InjectionStrategy {

    @Override
    public void inject(Object object,
                       Field field,
                       String value,
                       ApplicationContext applicationContext) throws InstantiationException, IllegalAccessException {
        final Object bean = applicationContext.getBean(field.getType());
        field.set(object, bean);
    }
}
