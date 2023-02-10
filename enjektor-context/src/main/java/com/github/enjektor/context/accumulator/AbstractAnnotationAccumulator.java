package com.github.enjektor.context.accumulator;

import com.github.enjektor.core.reflection.ReflectionScanner;
import com.github.enjektor.core.reflection.scanner.AnnotationReflectionScanner;
import com.github.enjektor.core.reflection.scanner.SubclassReflectionScanner;

import java.lang.annotation.Annotation;

public abstract class AbstractAnnotationAccumulator implements AnnotationAccumulator {

    protected final ReflectionScanner<Annotation> annotationReflectionScanner;
    protected final ReflectionScanner<Object> subclassReflectionScanner;

    protected AbstractAnnotationAccumulator() {
        this.annotationReflectionScanner = new AnnotationReflectionScanner();
        this.subclassReflectionScanner = new SubclassReflectionScanner();
    }
}
