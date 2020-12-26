package com.github.enjektor.core.scanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface BeanScanner {
    Set<Class<?>> scan(Class<?> mainClass, Class<? extends Annotation> annotation);
}
