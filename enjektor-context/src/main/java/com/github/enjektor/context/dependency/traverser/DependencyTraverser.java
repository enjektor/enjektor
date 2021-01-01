package com.github.enjektor.context.dependency.traverser;

import com.github.enjektor.core.scanner.AnnotationScanner;
import com.github.enjektor.core.scanner.ClassScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface DependencyTraverser {
    ClassScanner<Annotation> CLASS_SCANNER = AnnotationScanner.getInstance();

    Set<Class<?>> traverse(Class<?> mainClass);
}
