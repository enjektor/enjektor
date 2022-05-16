package com.github.enjektor.context;

import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.bean.pair.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enjektor {

    private ApplicationContext applicationContext;

    public Enjektor(Class<?> mainClass) {
        this(mainClass, null);
    }

    public Enjektor(Class<?> mainClass, List<Pair> pairs) {
        this(mainClass, Collections.singletonList(new ConcreteDependencyInitializer()), pairs);
    }

    public Enjektor(Class<?> mainClass,
                    List<DependencyInitializer> dependencyInitializers,
                    List<Pair> pairs) {
        applicationContext = new PrimitiveApplicationContext(mainClass, dependencyInitializers, pairs);
    }

    public <T> T getDependency(final Class<T> classType) throws IllegalAccessException, InstantiationException {
        return applicationContext.getBean(classType);
    }

    public <T> T getDependency(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException {
        return applicationContext.getBean(classType, fieldName);
    }

    public void destroy() {
        applicationContext.destroy();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<DependencyInitializer> dependencyInitializers = new ArrayList<>();
        private List<Pair> pairs = new ArrayList<>(3);
        private Class<?> mainClass;

        public Builder builder() {
            return this;
        }

        public Builder withMainClass(Class<?> mainClass) {
            this.mainClass = mainClass;
            return this;
        }

        public Builder addDependencyInitializer(DependencyInitializer dependencyInitializer) {
            dependencyInitializers.add(dependencyInitializer);
            return this;
        }

        public Builder addPair(Pair pair) {
            pairs.add(pair);
            return this;
        }

        public Builder addPairs(List<Pair> pairs) {
            pairs.addAll(pairs);
            return this;
        }

        public Enjektor build() {
            return new Enjektor(mainClass, dependencyInitializers, pairs);
        }
    }
}

