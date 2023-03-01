package com.github.enjektor.context.aggregator;

import com.github.enjektor.core.EnjektorDependency;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SingleImplementationDependencyAnnotationAggregator implements AnnotationAggregator {

    @Override
    public List<EnjektorDependency> aggregate(final Set<Class<?>> classes) {
        return mapNonInterfaceClasses(classes).collect(Collectors.toList());
    }

    private Predicate<Class<?>> mustHaveOnlyDefaultConstructor() {
        return classWithAnnotatedWithDependency -> {
            try {
                classWithAnnotatedWithDependency.getDeclaredConstructor(null);
                return true;
            } catch (NoSuchMethodException ignored) {
            }

            return false;
        };
    }

    private Predicate<Class<?>> mustBeImplementation() {
        return classWithAnnotatedWithDependency -> !classWithAnnotatedWithDependency.isInterface();
    }

    private Stream<EnjektorDependency> mapNonInterfaceClasses(final Set<Class<?>> classes) {
        final Predicate<Class<?>> filter = mustBeImplementation().and(mustHaveOnlyDefaultConstructor());
        return classes.stream().filter(filter).map(EnjektorDependency::new);
    }
}
