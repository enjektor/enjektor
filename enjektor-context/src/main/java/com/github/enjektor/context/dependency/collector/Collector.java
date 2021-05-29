package com.github.enjektor.context.dependency.collector;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.core.scanner.AnnotationScanner;
import com.github.enjektor.core.scanner.ClassScanner;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public interface Collector {
    ClassScanner<Annotation> CLASS_SCANNER = AnnotationScanner.getInstance();

    Set<Class<?>> collect(Class<?> mainClass);
    void collect(Class<?> mainClass, Map<Class<?>, Bean> beanMap);
}
