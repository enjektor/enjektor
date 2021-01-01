package com.github.enjektor.context.dependency.traverser;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class AnnotationConfigDependencyTraverser implements DependencyTraverser {

    @Override
    public final Set<Class<?>> traverse(final Class<?> mainClass) {
        final Set<Class<?>> scanned = scanner.scan(mainClass, Dependencies.class);
        final Set<Class<?>> dependencies = new HashSet<>();

        for (final Class<?> dependency : scanned) {
            final Method[] methods = dependency.getDeclaredMethods();
            for (final Method method : methods) {
                boolean isPublic = (method.getModifiers() & Modifier.PUBLIC) != 0;
                if (method.isAnnotationPresent(Dependency.class) && isPublic) {
                    final Class<?> returnType = method.getReturnType();
                    final Class<? extends Constructor> aClass = returnType.getConstructors()[0].getClass();
                }
            }
        }

        return dependencies;
    }
}
