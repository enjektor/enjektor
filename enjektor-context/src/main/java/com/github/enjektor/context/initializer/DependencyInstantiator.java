package com.github.enjektor.context.initializer;

import com.github.enjektor.core.EnjektorDependency;
import vlsi.utils.CompactHashMap;

import java.util.Set;

public interface DependencyInstantiator {
    CompactHashMap<Class<?>, EnjektorDependency> merge(CompactHashMap<Class<?>, EnjektorDependency> previousPipe,
                                                       Set<Class<?>> classes);
}
