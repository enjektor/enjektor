package com.github.enjektor.context;

import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.context.injection.RecursiveFieldInjectionManager;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.core.reflection.scanner.field.FieldScanner;
import com.github.enjektor.core.reflection.scanner.field.InjectAnnotationFieldScanner;
import com.github.enjektor.core.util.NamingUtils;
import org.reflections.Reflections;

import java.util.List;
import java.util.Map;

public class DefaultApplicationContext implements ApplicationContext, DeAllocationHandler {

    private static final byte STRING_TYPE = (byte) 1;
    private static final byte OBJECT_TYPE = (byte) 0;
    private static final byte NULL_CASE = (byte) 0;
    private static final byte NON_NULL_CASE = (byte) 1;

    private final static byte REQUIRED_COMPONENTS_ZERO_INDEX_REUSABLE_REFLECTIONS_OBJECT = (byte) 0x0;

    private final Map<Class<?>, Bean> beans;
    private RecursiveFieldInjectionManager recursiveFieldInjectionManager;


    public DefaultApplicationContext(final Class<?> mainClass,
                                     final Map<Class<?>, Bean> beans,
                                     final List<DependencyInitializer> dependencyInitializers,
                                     final Object[] requiredComponents) {
        final FieldScanner fieldScanner = new InjectAnnotationFieldScanner();
        final Reflections reflections = (Reflections) requiredComponents[REQUIRED_COMPONENTS_ZERO_INDEX_REUSABLE_REFLECTIONS_OBJECT];

        this.beans = beans;
        this.recursiveFieldInjectionManager = new RecursiveFieldInjectionManager(this, fieldScanner, reflections);

        init(mainClass, dependencyInitializers);
    }

    private void init(final Class<?> mainClass,
                      final List<DependencyInitializer> dependencyInitializers) {
        for (final DependencyInitializer dependencyInitializer : dependencyInitializers)
            beans.putAll(dependencyInitializer.initialize(mainClass, beans));
    }

    @Override
    public void init(List<Pair> pairs) {

    }

    @Override
    public void destroy() {
        clean();
    }

    @Override
    public <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException {
        final Bean bean = beans.get(classType);
        final byte stringOrNot = classType.equals(String.class) ? STRING_TYPE : OBJECT_TYPE;
        final byte nullOrNot = bean != null ? NON_NULL_CASE : NULL_CASE;

        switch (nullOrNot) {
            case NULL_CASE:
                switch (stringOrNot) {
                    case STRING_TYPE:
                        throw new IllegalArgumentException("There is no such property ${" + fieldName + "} in your configuration file.");
                    case OBJECT_TYPE:
                        final String s = NamingUtils.reverseBeanCase(classType.getSimpleName());
                        throw new IllegalArgumentException("There is no instance of \" " + s + " \" class in Enjektor IoC container. Make sure that you create any instance from this class with @Dependency or @Dependencies");
                }
                break;
        }

        final Object existObject = bean.getDependency(fieldName);

        recursiveFieldInjectionManager.inject(existObject);
        return (T) existObject;
    }

    @Override
    public <T> Bean getNativeBean(Class<T> classType) throws IllegalAccessException, InstantiationException {
        return beans.get(classType);
    }

    @Override
    public void clean() {
        recursiveFieldInjectionManager = null;
    }
}
