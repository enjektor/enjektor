package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.EnjektorDependency;
import com.github.enjektor.core.annotations.Dependency;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DefaultConstructorOnlyDependencyAnnotationAccumulator extends AbstractAnnotationAccumulator {

    @Override
    public List<EnjektorDependency> accumulate(Class<?> mainClass, Map<Class<?>, EnjektorDependency> enjektorDependencyHolder) {
        final Set<Class<?>> classes = annotationReflectionScanner.scan(mainClass, Dependency.class);
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
