package com.github.enjektor.core.scanner;

import java.util.Set;

public interface Scanner<T> {
    Set<Class<?>> scan(Class<?> mainClass, Class<? extends T> annotation);
}
