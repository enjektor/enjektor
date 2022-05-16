package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.wrapper.IntegerListWrapper;
import com.github.enjektor.core.wrapper.StringListWrapper;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class ListInjectionStrategy implements InjectionStrategy {

    private final static Map<Class<?>, Class<?>> wrappers = new HashMap<>(3);

    static {
        wrappers.put(String.class, StringListWrapper.class);
        wrappers.put(Integer.class, IntegerListWrapper.class);
    }

    @Override
    public void inject(Object object,
                       Field field,
                       String value,
                       ApplicationContext applicationContext) throws IllegalAccessException {
        final ParameterizedType listType = (ParameterizedType) field.getGenericType();
        final Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
        final Class<?> wrapperClass = wrappers.get(listClass);

        final String qualifier = value != null ? value : wrapperClass.getSimpleName();
        try {
            final Object dependency = applicationContext.getNativeBean(wrapperClass).getDependency(qualifier);

            try {
                final Object values = new PropertyDescriptor("values", wrapperClass).getReadMethod().invoke(dependency);
                field.set(object, values);
            } catch (InvocationTargetException | IntrospectionException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }


    }
}
