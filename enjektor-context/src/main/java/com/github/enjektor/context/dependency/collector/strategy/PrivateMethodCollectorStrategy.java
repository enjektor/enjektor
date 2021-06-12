package com.github.enjektor.context.dependency.collector.strategy;

import com.github.enjektor.context.bean.Bean;

import java.lang.reflect.Method;
import java.util.Map;

public class PrivateMethodCollectorStrategy implements CollectorStrategy {

    @Override
    public void act(Method method, Map<Class<?>, Bean> context, Object dependenciesInstance) {
        System.out.println("Do nothing");
    }
}
