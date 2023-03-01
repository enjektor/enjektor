package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.EnjektorDependencyDepth;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

public final class DependencyDepthAccumulatorImpl implements DependencyDepthAccumulator {

    private final static int CAPACITY = 6;

    @Override
    public TIntObjectMap<List<Class<?>>> findDepth(final Set<Class<?>> classesAnnotatedWithDependencyAnnotation) {
        final TIntObjectMap<List<Class<?>>> depths = new TIntObjectHashMap<>(CAPACITY + 1);
        final EnjektorDependencyDepth levels[] = new EnjektorDependencyDepth[CAPACITY];

        for (int i = 0; i < CAPACITY; i++) levels[i] = new EnjektorDependencyDepth(i);

        for (final Class<?> klass : classesAnnotatedWithDependencyAnnotation) {
            final Constructor<?>[] constructors = klass.getDeclaredConstructors();
            final int level = constructors.length;
            levels[level].add(klass);
        }

        for (final EnjektorDependencyDepth enjektorDependencyDepth : levels) {
            final int level = enjektorDependencyDepth.getLevel();
            final List<Class<?>> metadata = enjektorDependencyDepth.getMetadata();
            depths.put(level, metadata);
        }

        return depths;
    }
}
