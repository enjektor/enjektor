package com.github.enjektor.context.dependency.collector;

import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.annotations.Dependency;

import java.util.Map;
import java.util.Set;

public class DependencyAnnotationCollector implements Collector {

    @Override
    public final Set<Class<?>> collect(final Class<?> mainClass) {
        return CLASS_SCANNER.scan(mainClass, Dependency.class);
    }

    @Override
    public void collect(Class<?> mainClass, Map<Class<?>, Bean> beanMap) {
        throw new UnsupportedOperationException();
    }
}
