package com.github.enjektor.context.autowire;

import com.github.enjektor.context.bean.Bean;

import java.util.Map;

public interface AutowireMechanism {
    void autowire(Object object, Map<Class<?>, Bean> applicationContext) throws IllegalAccessException;
}
