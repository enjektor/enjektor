package com.github.enjektor.core.scanner;

import java.lang.reflect.Field;
import java.util.Set;

public interface FieldScanner {
    Set<Field> scan(final Class<?> dependency);
}
