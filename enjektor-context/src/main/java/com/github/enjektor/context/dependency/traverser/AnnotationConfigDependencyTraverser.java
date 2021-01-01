package com.github.enjektor.context.dependency.traverser;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class AnnotationConfigDependencyTraverser implements DependencyTraverser {

    @Dependencies
    static class ExampleDependencies {

        @Dependency
        public String name() {
            return "string";
        }

        @Dependency
        public int seedNumb() {
            return 32;
        }

    }


    @Override
    public final Set<Class<?>> traverse(final Class<?> mainClass) {
        final Set<Class<?>> scanned = scanner.scan(mainClass, Dependencies.class);
        final Set<Class<?>> dependencies = new HashSet<>();

        for (final Class<?> dependency : scanned) {
            for (final Field field : dependency.getDeclaredFields()) {
                if (field.isAnnotationPresent(Dependency.class)) {
                    dependencies.add(field.getType());
                }
            }
        }

        return dependencies;
    }
}
