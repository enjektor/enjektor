package com.github.enjektor.context.initializer;

import com.github.enjektor.core.EnjektorDependency;
import vlsi.utils.CompactHashMap;

import java.util.Set;

public final class SingleImplementationDependencyInstantiator implements DependencyInstantiator {

    @Override
    public CompactHashMap<Class<?>, EnjektorDependency> merge(final CompactHashMap<Class<?>, EnjektorDependency> previousPipe,
                                                              final Set<Class<?>> classes) {



        return null;
    }
}
