package com.github.enjektor.context.dependency.traverser;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.utils.RuntimeAnnotations;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationConfigDependencyTraverser implements DependencyTraverser {

    @Override
    public final Set<Class<?>> traverse(final Class<?> mainClass) {
        final Set<Class<?>> scanned = CLASS_SCANNER.scan(mainClass, Dependencies.class);
        final Set<Class<?>> dependencies = new HashSet<>();

        for (final Class<?> dependency : scanned) {
            final Method[] declaredMethods = dependency.getDeclaredMethods();
            for (final Method method : declaredMethods) {
                boolean isPublic = (method.getModifiers() & Modifier.PUBLIC) != 0;
                if (method.isAnnotationPresent(Dependency.class) && isPublic) {
                    final Class<?> returnType = method.getReturnType();
                    final Map<String, Object> valuesMap = new HashMap<>();
                    valuesMap.put("name", "");
                    RuntimeAnnotations.putAnnotation(returnType, Dependency.class, valuesMap);
                    dependencies.add(returnType);
                }
            }
        }

        return dependencies;
    }
}
