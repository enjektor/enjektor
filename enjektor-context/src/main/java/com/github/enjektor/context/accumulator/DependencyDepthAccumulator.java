package com.github.enjektor.context.accumulator;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.TIntObjectMap;

import java.util.List;
import java.util.Set;

public interface DependencyDepthAccumulator {
    TIntObjectMap<List<Class<?>>> findDepth(Set<Class<?>> classesAnnotatedWithDependencyAnnotation);
}
