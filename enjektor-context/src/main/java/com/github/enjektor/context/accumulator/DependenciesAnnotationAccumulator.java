package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DependenciesAnnotationAccumulator extends AbstractAnnotationAccumulator {

    @Override
    public List<Bean> accumulate(Class<?> mainClass, Map<Class<?>, Bean> beans) {
        final Set<Class<?>> classesWithDependenciesAnnotation = annotationReflectionScanner.scan(mainClass, Dependencies.class);

        for (final Class<?> dependenciesClass : classesWithDependenciesAnnotation) {
            final Object dependenciesInstance = newInstance(dependenciesClass);
            final Method[] methods = dependenciesClass.getDeclaredMethods();

            for (final Method method : methods) {
                boolean isPublic = (method.getModifiers() & Modifier.PUBLIC) != 0;
                if (isPublic) {
                    try {
                        final Class<?> methodSignatureType = method.getReturnType();
                        final Object instance = method.invoke(dependenciesInstance, null);
                        final String name = method.getName();
                        final Bean bean = beans.get(methodSignatureType);

                        Optional
                            .ofNullable(bean)
                            .ifPresentOrElse((present -> present.register(name, instance)), () -> {
                                final Bean newBean = new Bean(methodSignatureType);
                                beans.put(methodSignatureType, newBean);
                            });
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

    private Object newInstance(final Class<?> classWithDependenciesAnnotation) {
        try {
            return classWithDependenciesAnnotation.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
