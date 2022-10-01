package com.github.enjektor.context.injection.behaviour;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.Map;

public interface InjectionBehaviour {
    void act(ApplicationContext applicationContext, Object object, Field field) throws IllegalAccessException, InstantiationException;
}
