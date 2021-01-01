package com.github.enjektor.context.dependency;


import com.github.enjektor.context.bean.Bean;

import java.util.Map;

public interface DependencyInitializer {
    void initialize(Class<?> mainClass, Map<Class<?>, Bean> applicationContextMap);
}
