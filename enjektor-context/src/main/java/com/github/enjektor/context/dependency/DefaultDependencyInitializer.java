package com.github.enjektor.context.dependency;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.consumer.BeanConsumer;
import com.github.enjektor.context.dependency.traverser.AnnotationConfigDependencyTraverser;
import com.github.enjektor.context.dependency.traverser.DefaultDependencyTraverser;
import com.github.enjektor.context.dependency.traverser.DependencyTraverser;
import com.github.enjektor.core.scanner.ConcreteClassScanner;
import com.github.enjektor.core.scanner.ClassScanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class DefaultDependencyInitializer implements DependencyInitializer {

    @Override
    public Map<Class<?>, Bean> initialize(final Class<?> mainClass) {
        final Map<Class<?>, Bean> applicationContext = new WeakHashMap<>();
        final ClassScanner<Object> concreteClassClassScanner = ConcreteClassScanner.getInstance();
        final DependencyTraverser defaultTraverser = new DefaultDependencyTraverser();
        final DependencyTraverser annotationBasedTraverser = new AnnotationConfigDependencyTraverser();

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final CompletableFuture<Set<Class<?>>> defaultDependenciesCf = CompletableFuture.supplyAsync(() -> defaultTraverser.traverse(mainClass), executorService);
        final CompletableFuture<Set<Class<?>>> annotationBasedDependenciesCf = CompletableFuture.supplyAsync(() -> annotationBasedTraverser.traverse(mainClass), executorService);
        final CompletableFuture<Void> traverseTask = CompletableFuture.allOf(defaultDependenciesCf, annotationBasedDependenciesCf);

        try {
            traverseTask.get();
            final Set<Class<?>> defaultDependencies = defaultDependenciesCf.get();
            final Set<Class<?>> annotationBasedDependencies = annotationBasedDependenciesCf.get();
            defaultDependencies.addAll(annotationBasedDependencies);

            final List<Bean> beans = new ArrayList<>();
            final Set<Class<?>> scannedClasses = new HashSet<>();

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

            for (Bean bean : beans) applicationContext.put(bean.getClassType(), bean);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        return applicationContext;
    }
}
