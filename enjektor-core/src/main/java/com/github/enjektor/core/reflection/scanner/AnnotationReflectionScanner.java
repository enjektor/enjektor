package com.github.enjektor.core.reflection.scanner;

import com.github.enjektor.core.reflection.ReflectionScanner;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

public final class AnnotationReflectionScanner implements ReflectionScanner<Annotation> {

    public Set<Class<?>> scan(final Class<?> mainClass,
                              final Class<? extends Annotation> annotation) {
        final String packageName = mainClass.getPackage().getName();
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
            .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner())
            .setUrls(ClasspathHelper.forJavaClassPath())
            .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName)));

        final Reflections reflections = new Reflections(configurationBuilder);
        return reflections.getTypesAnnotatedWith(annotation);
    }

}
