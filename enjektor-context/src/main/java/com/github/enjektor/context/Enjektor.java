package com.github.enjektor.context;

import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enjektor {

    private ApplicationContext applicationContext;

    public Enjektor(Class<?> mainClass) {
        this(mainClass, Collections.singletonList(new ConcreteDependencyInitializer()));
    }

    public Enjektor(Class<?> mainClass, List<DependencyInitializer> dependencyInitializers) {
        applicationContext = new PrimitiveApplicationContext(mainClass, dependencyInitializers);
    }

    public <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException {
        return applicationContext.getBean(classType);
    }

    public <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException {
        return applicationContext.getBean(classType, fieldName);
    }

    public static class Builder {
        private List<DependencyInitializer> dependencyInitializers = new ArrayList<>();
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

        public Enjektor build() {
            return new Enjektor(mainClass, dependencyInitializers);
        }
    }
}

