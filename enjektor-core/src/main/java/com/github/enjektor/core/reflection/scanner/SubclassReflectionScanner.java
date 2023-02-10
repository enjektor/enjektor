package com.github.enjektor.core.reflection.scanner;

import com.github.enjektor.core.reflection.ReflectionScanner;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

public class SubclassReflectionScanner implements ReflectionScanner<Object> {

    public final Set<Class<?>> scan(final Class<?> mainClass,
                                    final Class<?> interfaceClass) {
        final String packageName = mainClass.getPackage().getName();
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
            .setScanners(new SubTypesScanner(false), new ResourcesScanner())
            .setUrls(ClasspathHelper.forJavaClassPath())
            .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName)));

        final Reflections reflections = new Reflections(configurationBuilder);
        return reflections.getSubTypesOf((Class<Object>) interfaceClass);
    }
}
