package com.github.enjektor.context.dependency;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.collector.DependenciesAnnotationCollector;
import com.github.enjektor.context.dependency.collector.DependencyAnnotationCollector;
import com.github.enjektor.context.dependency.collector.Collector;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.scanner.ClassScanner;
import com.github.enjektor.core.scanner.ConcreteClassScanner;

import java.util.*;
import java.util.stream.Collectors;

public class ConcreteDependencyInitializer implements DependencyInitializer {

    private static final ClassScanner<Object> concreteClassClassScanner = ConcreteClassScanner.getInstance();
    private final Collector dependencyAnnotationCollector = new DependencyAnnotationCollector();
    private final Collector dependenciesAnnotationCollector = new DependenciesAnnotationCollector();

    @Override
    public Map<Class<?>, Bean> initialize(final Class<?> mainClass) {
        final Map<Class<?>, Bean> applicationContext = new WeakHashMap<>();

        final Set<Class<?>> collect = dependencyAnnotationCollector.collect(mainClass);
        final List<Bean> interfaces = registerInterfaces(collect, mainClass);
        final List<Bean> concretes = registerConcretes(collect);
        interfaces.addAll(concretes);

        for (Bean bean : interfaces) applicationContext.put(bean.getClassType(), bean);

       dependenciesAnnotationCollector.collect(mainClass, applicationContext);

        return applicationContext;
    }

    private List<Class<?>> interfaces(Set<Class<?>> set) {
        return set.stream().filter(Class::isInterface).collect(Collectors.toList());
    }

    private List<Class<?>> concretes(Set<Class<?>> set) {
        return set
            .stream()
            .filter(bean -> !bean.isInterface())
            .collect(Collectors.toList());
    }

    private List<Bean> registerConcretes(Set<Class<?>> set) {
        final List<Bean> beans = new ArrayList<>(set.size());

        for (final Class<?> concrete : concretes(set)) {
            Bean bean = new Bean(concrete);
            bean.register(concrete);
            beans.add(bean);
        }

        return beans;
    }

    private List<Bean> registerInterfaces(Set<Class<?>> set, Class<?> mainClass) {
        final List<Bean> beans = new ArrayList<>(set.size());

        for (final Class<?> anInterface : interfaces(set)) {
            final Set<Class<?>> allClassesThatInterfaceHas = concreteClassClassScanner.scan(mainClass, anInterface);
            final Bean bean = new Bean(anInterface);

            for (Class<?> concreteImplementation : allClassesThatInterfaceHas) {
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
}
