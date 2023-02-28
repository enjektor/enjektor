package com.github.enjektor.context.dependency;

import com.github.enjektor.context.accumulator.AnnotationAccumulator;
import com.github.enjektor.context.accumulator.DependencyAnnotationAccumulator;
import com.github.enjektor.context.accumulator.DependenciesAnnotationAccumulator;
import com.github.enjektor.core.bean.Bean;

import java.util.List;
import java.util.Map;

public final class DefaultDependencyInitializer implements DependencyInitializer {

    private final AnnotationAccumulator dependencyAnnotationAccumulator = new DependencyAnnotationAccumulator();
    private final AnnotationAccumulator multipleDependencyAnnotationAccumulator = new DependenciesAnnotationAccumulator();

    @Override
    public Map<Class<?>, Bean> initialize(final Class<?> mainClass, final Map<Class<?>, Bean> beans) {
        final List<Bean> dependenciesAsBean = dependencyAnnotationAccumulator.accumulate(mainClass, null);
        for (final Bean bean : dependenciesAsBean) beans.put(bean.getClassType(), bean);

        multipleDependencyAnnotationAccumulator.accumulate(mainClass, beans);
        return beans;
    }
}
