package com.github.enjektor.core.scanner;

import java.util.Set;

public interface InterfaceScanner {
    Set<Class<?>> scanConcreteClasses(final Class<?> mainClass, final Class<?> interfaceClass);
}
