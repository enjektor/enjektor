package com.github.enjektor.context.dependency.traverser;

import com.github.enjektor.core.scanner.AnnotationScanner;
import com.github.enjektor.core.scanner.Scanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface DependencyTraverser {
    Scanner<Annotation> scanner = AnnotationScanner.getInstance();

    Set<Class<?>> traverse(Class<?> mainClass);
}
