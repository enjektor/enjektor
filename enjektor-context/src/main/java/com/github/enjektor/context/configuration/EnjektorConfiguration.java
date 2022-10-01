package com.github.enjektor.context.configuration;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class EnjektorConfiguration {
    private final Reflections reflections;
    private final Class<?> mainClass;

    private EnjektorConfiguration(final Reflections reflections,
                                  final Class<?> mainClass) {
        this.reflections = reflections;
        this.mainClass = mainClass;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {
        private Class<?> mainClass;

        public Builder builder() {
            return this;
        }

        public Builder withMainClass(final Class<?> mainClass) {
            this.mainClass = mainClass;
            return this;
        }

        public EnjektorConfiguration build() {
            final ConfigurationBuilder configurationBuilder = createConfigurationBuilderWithMainClass(mainClass);
            final Reflections reflections = new Reflections(configurationBuilder);
            return new EnjektorConfiguration(reflections, mainClass);
        }

        private ConfigurationBuilder createConfigurationBuilderWithMainClass(final Class<?> mainClass) {
            final String packageName = mainClass.getPackage().getName();
            final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forJavaClassPath())
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName)));
            return configurationBuilder;
        }
    }

    public Class<?> getMainClass() {
        return mainClass;
    }

    public Reflections getReflections() {
        return reflections;
    }
}
