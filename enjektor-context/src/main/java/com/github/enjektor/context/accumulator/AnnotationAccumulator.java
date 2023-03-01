package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.EnjektorDependency;
import com.github.enjektor.core.bean.Bean;

import java.util.List;
import java.util.Map;

public interface AnnotationAccumulator {
    List<EnjektorDependency> accumulate(final Class<?> mainClass, final Map<Class<?>, EnjektorDependency> enjektorDependencyHolder);
}
