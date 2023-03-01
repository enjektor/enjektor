package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.EnjektorDependency;
import com.github.enjektor.core.annotations.Dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DependencyAnnotationAccumulator extends AbstractAnnotationAccumulator {

    @Override
    public List<EnjektorDependency> accumulate(final Class<?> mainClass,
                                               final Map<Class<?>, EnjektorDependency> beans) {
        final Set<Class<?>> classes = annotationReflectionScanner.scan(mainClass, Dependency.class);
        final List<EnjektorDependency> interfaceBeans = accumulateInterfaces(mainClass, classes);
        final Stream<EnjektorDependency> implementationBeansStream = mapNonInterfaceClasses(classes);

        return Stream
            .concat(interfaceBeans.stream(), implementationBeansStream)
            .collect(Collectors.toList());
    }

    private List<EnjektorDependency> accumulateInterfaces(final Class<?> mainClass, final Set<Class<?>> classes) {
        final List<Class<?>> interfaces = filterInterfaces(classes);
        final List<EnjektorDependency> enjektorDependencies = new ArrayList<>(interfaces.size());

        for (final Class<?> anInterface : interfaces) {
            final Set<Class<?>> allClassesThatInterfaceHas = subclassReflectionScanner.scan(mainClass, anInterface);
            final EnjektorDependency enjektorDependency = new EnjektorDependency(anInterface);

            for (final Class<?> concreteImplementation : allClassesThatInterfaceHas) {
                final Dependency dependency = concreteImplementation.getDeclaredAnnotation(Dependency.class);
                if (dependency != null) {
                    if (dependency.name().isEmpty()) enjektorDependency.register(concreteImplementation);
                    else enjektorDependency.register(concreteImplementation, dependency.name());
                }
            }
            enjektorDependencies.add(enjektorDependency);
        }

        return enjektorDependencies;
    }

    private Stream<Class<?>> filterNonInterfaceClasses(final Set<Class<?>> classes) {
        return classes.stream().filter(bean -> !bean.isInterface());
    }

    private Stream<EnjektorDependency> mapNonInterfaceClasses(final Set<Class<?>> classes) {
        return filterNonInterfaceClasses(classes).map(EnjektorDependency::new);
    }

    private List<Class<?>> filterInterfaces(final Set<Class<?>> classes) {
        return classes.stream().filter(Class::isInterface).collect(Collectors.toList());
    }
}
