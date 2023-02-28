package com.github.enjektor.context;

import com.github.enjektor.context.configuration.EnjektorConfiguration;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.bean.pair.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enjektor {

    private final static byte REQUIRED_COMPONENTS_ZERO_INDEX_REUSABLE_REFLECTIONS_OBJECT = (byte) 0x0;

    private final ApplicationContext applicationContext;

    private Enjektor(final Class<?> mainClass,
                     final Object[] requiredComponents) {
        this(mainClass, null, requiredComponents);
    }

    private Enjektor(final Class<?> mainClass,
                     final List<Pair> pairs,
                     final Object[] requiredComponents) {
        this(mainClass, Collections.singletonList(new DefaultDependencyInitializer()), pairs, requiredComponents);
    }

    private Enjektor(final Class<?> mainClass,
                     final List<DependencyInitializer> dependencyInitializers,
                     final List<Pair> pairs,
                     final Object[] requiredComponents) {
        this.applicationContext = new PrimitiveApplicationContext(mainClass, dependencyInitializers, pairs, requiredComponents);
    }

    public <T> T getDependency(final Class<T> classType) throws IllegalAccessException, InstantiationException {
        return applicationContext.getBean(classType);
    }

    public <T> T getDependency(final Class<T> classType,
                               final String fieldName) throws IllegalAccessException, InstantiationException {
        return applicationContext.getBean(classType, fieldName);
    }

    public void destroy() {
        applicationContext.destroy();
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {
        private final static byte INITIAL_CAPACITY = (byte) 0x3;
        private final List<DependencyInitializer> dependencyInitializers = new ArrayList<>(INITIAL_CAPACITY);
        private final List<Pair> pairs = new ArrayList<>(INITIAL_CAPACITY);
        private EnjektorConfiguration enjektorConfiguration;

        public Builder addDependencyInitializer(final DependencyInitializer dependencyInitializer) {
            dependencyInitializers.add(dependencyInitializer);
            return this;
        }

        public Builder configuration(final EnjektorConfiguration enjektorConfiguration) {
            this.enjektorConfiguration = enjektorConfiguration;
            return this;
        }

        public Builder addPair(final Pair pair) {
            pairs.add(pair);
            return this;
        }

        public Builder addPairs(final List<Pair> pairs) {
            pairs.addAll(pairs);
            return this;
        }

        public Enjektor build() {
            final Class<?> mainClass = enjektorConfiguration.getMainClass();
            final Object[] requiredComponents = new Object[INITIAL_CAPACITY];
            requiredComponents[REQUIRED_COMPONENTS_ZERO_INDEX_REUSABLE_REFLECTIONS_OBJECT] = enjektorConfiguration.getReflections();


            return new Enjektor(mainClass, dependencyInitializers, pairs, requiredComponents);
        }
    }
}

