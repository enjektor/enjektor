package com.github.enjektor.core.scanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public enum BeanScannerImpl implements BeanScanner {
    INSTANCE;

    @Override
    public Set<Class<?>> scan(Class<?> mainClass, Class<? extends Annotation> annotation) {
        return null;
    }

    public static BeanScanner getInstance() {
        return INSTANCE;
    }
}
