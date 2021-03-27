package com.github.enjektor.context.dependency;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.consumer.BeanConsumer;
import com.github.enjektor.context.dependency.traverser.DefaultDependencyTraverser;
import com.github.enjektor.context.dependency.traverser.DependencyTraverser;
import com.github.enjektor.core.scanner.ClassScanner;
import com.github.enjektor.core.scanner.ConcreteClassScanner;
import gnu.trove.set.hash.THashSet;

import java.util.*;
import java.util.function.Consumer;

public class ConstructorStrategyDependencyInitializer implements DependencyInitializer {

    @Override
    public Map<Class<?>, Bean> initialize(final Class<?> mainClass) {
        final Map<Class<?>, Bean> applicationContext = new WeakHashMap<>();
        final ClassScanner<Object> concreteClassClassScanner = ConcreteClassScanner.getInstance();
        final DependencyTraverser defaultTraverser = new DefaultDependencyTraverser();
        final Set<Class<?>> defaultDependencies = defaultTraverser.traverse(mainClass);

        final Map<String, Object> instancesOnRuntime = new WeakHashMap<>();

        final List<Bean> beans = new ArrayList<>();
        final THashSet<Class<?>> scannedClasses = new THashSet<>();

        List<Class<?>> scannedDefaultDependencies = new ArrayList<>(defaultDependencies);

        for (final Class<?> dependency : defaultDependencies) {
            if (dependency.isInterface()) {
                scannedClasses.add(dependency);
                final Set<Class<?>> allClassesThatInterfaceHas = concreteClassClassScanner.scan(mainClass, dependency);
                final Bean bean = new Bean(dependency);

                final Consumer<Class<?>> beanConsumer = new BeanConsumer(bean, scannedClasses);
                allClassesThatInterfaceHas.forEach(beanConsumer);

                beans.add(bean);
            }
        }

        for (final Class<?> singleImplementation : defaultDependencies) {
            if (!scannedClasses.contains(singleImplementation)) {
                Bean bean = new Bean(singleImplementation);
                bean.register(singleImplementation);
                beans.add(bean);
            }
        }


        return applicationContext;
    }
}
