package com.github.enjektor.context.dependency.collector;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.collector.strategy.CollectorStrategy;
import com.github.enjektor.context.dependency.collector.strategy.PrivateMethodCollectorStrategy;
import com.github.enjektor.context.dependency.collector.strategy.PublicMethodCollectorStrategy;
import com.github.enjektor.core.annotations.Dependencies;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

public class DependenciesAnnotationCollector implements Collector {

    private final CollectorStrategy[] collectorStrategies = new CollectorStrategy[0x2];

    public DependenciesAnnotationCollector() {
        init();
    }

    private void init() {
        collectorStrategies[0x0] = new PrivateMethodCollectorStrategy();
        collectorStrategies[0x1] = new PublicMethodCollectorStrategy();
    }

    @Override
    public final Set<Class<?>> collect(final Class<?> mainClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void collect(Class<?> mainClass, Map<Class<?>, Bean> beanMap) {
        final Set<Class<?>> classesWithDependenciesAnnotation = CLASS_SCANNER.scan(mainClass, Dependencies.class);

        for (Class<?> dependenciesClass : classesWithDependenciesAnnotation) {
            final Object dependenciesInstance = newDependenciesInstance(dependenciesClass);
            final Method[] declaredMethods = dependenciesClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                int isPublic = (method.getModifiers() & Modifier.PUBLIC) != 0 ? 1 : 0;
                collectorStrategies[isPublic].act(method, beanMap, dependenciesInstance);
            }
        }
    }

    private Object newDependenciesInstance(Class<?> classWithDependenciesAnnotation) {
        try {
            return classWithDependenciesAnnotation.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
