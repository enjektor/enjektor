package com.github.enjektor.context.initializer;

import com.github.enjektor.context.accumulator.AnnotationAccumulator;
import com.github.enjektor.context.accumulator.DefaultConstructorOnlyDependencyAnnotationAccumulator;
import com.github.enjektor.context.accumulator.DependenciesAnnotationAccumulator;
import com.github.enjektor.context.accumulator.DependencyAnnotationAccumulator;
import com.github.enjektor.core.bean.Bean;

import java.util.Map;

public final class DefaultDependencyInitializer implements DependencyInitializer {

    private final AnnotationAccumulator dependencyAnnotationAccumulator = new DependencyAnnotationAccumulator();
    private final AnnotationAccumulator multipleDependencyAnnotationAccumulator = new DependenciesAnnotationAccumulator();
    private final AnnotationAccumulator singleConstructorDependencyAnnotationAccumulator = new DefaultConstructorOnlyDependencyAnnotationAccumulator();

    @Override
    public Map<Class<?>, Bean> initialize(Class<?> mainClass, Map<Class<?>, Bean> beans) {
        return null;
    }

//    @Override
//    public Map<Class<?>, Bean> initialize(final Class<?> mainClass, final Map<Class<?>, Bean> beans) {
//        List<Bean> accumulate = singleConstructorDependencyAnnotationAccumulator.accumulate(mainClass, beans);
//
//        final List<Bean> dependenciesAsBean = dependencyAnnotationAccumulator.accumulate(mainClass, null);
//        for (final Bean bean : dependenciesAsBean) beans.put(bean.getClassType(), bean);
//
//        multipleDependencyAnnotationAccumulator.accumulate(mainClass, beans);
//        return beans;
//    }
}
