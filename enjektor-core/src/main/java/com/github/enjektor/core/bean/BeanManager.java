package com.github.enjektor.core.bean;

import java.lang.reflect.Field;

public interface BeanManager {
    void manage(Object object, Field field) throws IllegalAccessException, InstantiationException;
}
