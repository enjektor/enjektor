package com.github.enjektor.context.injection;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.wrapper.StringListWrapper;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class ListInjectionStrategy implements InjectionStrategy {

    private final static Map<Class<?>, Class<?>> wrappers = new HashMap<>(1);

    static {
        wrappers.put(String.class, StringListWrapper.class);
    }

    @Override
    public void inject(Object object,
                       Field field,
                       String value,
                       ApplicationContext applicationContext,
                       Map<Class<?>, Bean> beans) throws IllegalAccessException {
        final ParameterizedType listType = (ParameterizedType) field.getGenericType();
        final Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
        final Class<?> wrapperClass = wrappers.get(listClass);

        final String qualifier = value != null ? value : wrapperClass.getSimpleName();
        final Object dependency = beans.get(wrapperClass).getDependency(qualifier);

        try {
            final Object values = new PropertyDescriptor("values", StringListWrapper.class).getReadMethod().invoke(dependency);
            field.set(object, values);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }
}
