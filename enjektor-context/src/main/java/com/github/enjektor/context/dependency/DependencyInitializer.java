package com.github.enjektor.context.dependency;

import com.github.enjektor.core.bean.Bean;

import java.util.Map;

public interface DependencyInitializer {
    Map<Class<?>, Bean> initialize(Class<?> mainClass, Map<Class<?>, Bean> beans);
}
