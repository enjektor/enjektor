package com.github.enjektor.context.dependency.traverser;

import com.github.enjektor.core.annotations.Dependency;

import java.util.Set;

public class DefaultDependencyTraverser implements DependencyTraverser {

    @Override
    public final Set<Class<?>> traverse(final Class<?> mainClass) {
        return scanner.scan(mainClass, Dependency.class);
    }
}
