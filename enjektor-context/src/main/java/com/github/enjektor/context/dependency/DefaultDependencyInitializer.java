package com.github.enjektor.context.dependency;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.traverser.DefaultDependencyTraverser;
import com.github.enjektor.context.dependency.traverser.DependencyTraverser;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.scanner.ClassScanner;
import com.github.enjektor.core.scanner.ConcreteClassScanner;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultDependencyInitializer implements DependencyInitializer {

    @Override
    public Map<Class<?>, Bean> initialize(final Class<?> mainClass) {
        final Map<Class<?>, Bean> applicationContext = new WeakHashMap<>();
        final ClassScanner<Object> concreteClassClassScanner = ConcreteClassScanner.getInstance();
        final DependencyTraverser defaultTraverser = new DefaultDependencyTraverser();

        final List<Bean> beans = new ArrayList<>(5);

        final Set<Class<?>> traverse = defaultTraverser.traverse(mainClass);

        final List<Class<?>> interfaces = traverse.stream().filter(Class::isInterface).collect(Collectors.toList());
        final List<Class<?>> concretes = traverse
            .stream()
            .filter(bean -> !bean.isInterface())
            .collect(Collectors.toList());

        for (Class<?> anInterface : interfaces) {
            final Set<Class<?>> allClassesThatInterfaceHas = concreteClassClassScanner.scan(mainClass, anInterface);
            final Bean bean = new Bean(anInterface);

            for (Class<?> concreteImplementation : allClassesThatInterfaceHas) {
                final Dependency dependency = concreteImplementation.getDeclaredAnnotation(Dependency.class);
                if (dependency.name().isEmpty()) bean.register(concreteImplementation);
                else bean.register(concreteImplementation, dependency.name());
            }

            beans.add(bean);
        }

        for (Class<?> concrete : concretes) {
            Bean bean = new Bean(concrete);
            bean.register(concrete);
            beans.add(bean);
        }

        for (Bean bean : beans) applicationContext.put(bean.getClassType(), bean);

        return applicationContext;
    }
}
