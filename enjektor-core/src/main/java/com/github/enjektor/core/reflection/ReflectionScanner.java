package com.github.enjektor.core.reflection;

import java.util.Set;

public interface ReflectionScanner<T> {
    Set<Class<?>> scan(final Class<?> mainClass, final Class<? extends T> klass);
}
