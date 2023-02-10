package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.bean.Bean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DependencyAnnotationAccumulator extends AbstractAnnotationAccumulator {

    @Override
    public List<Bean> accumulate(final Class<?> mainClass,
                                 final Map<Class<?>, Bean> beans) {
        final Set<Class<?>> classes = annotationReflectionScanner.scan(mainClass, Dependency.class);
        final List<Bean> interfaceBeans = accumulateInterfaces(mainClass, classes);
        final Stream<Bean> implementationBeansStream = mapNonInterfaceClasses(classes);

        return Stream
            .concat(interfaceBeans.stream(), implementationBeansStream)
            .collect(Collectors.toList());
    }

    private List<Bean> accumulateInterfaces(final Class<?> mainClass, final Set<Class<?>> classes) {
        final List<Class<?>> interfaces = filterInterfaces(classes);
        final List<Bean> beans = new ArrayList<>(interfaces.size());

        for (final Class<?> anInterface : interfaces) {
            final Set<Class<?>> allClassesThatInterfaceHas = subclassReflectionScanner.scan(mainClass, anInterface);
            final Bean bean = new Bean(anInterface);

            for (final Class<?> concreteImplementation : allClassesThatInterfaceHas) {
                final Dependency dependency = concreteImplementation.getDeclaredAnnotation(Dependency.class);
                if (dependency != null) {
                    if (dependency.name().isEmpty()) bean.register(concreteImplementation);
                    else bean.register(concreteImplementation, dependency.name());
                }
            }
            beans.add(bean);
        }

        return beans;
    }

    private Stream<Class<?>> filterNonInterfaceClasses(final Set<Class<?>> classes) {
        return classes.stream().filter(bean -> !bean.isInterface());
    }

    private Stream<Bean> mapNonInterfaceClasses(final Set<Class<?>> classes) {
        return filterNonInterfaceClasses(classes).map(Bean::new);
    }

    private List<Class<?>> filterInterfaces(final Set<Class<?>> classes) {
        return classes.stream().filter(Class::isInterface).collect(Collectors.toList());
    }
}
