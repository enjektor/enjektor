package com.github.enjektor.context.aggregator;

import com.github.enjektor.core.EnjektorDependency;

import java.util.List;
import java.util.Set;

public interface AnnotationAggregator {
    List<EnjektorDependency> aggregate(final Set<Class<?>> classes);
}
