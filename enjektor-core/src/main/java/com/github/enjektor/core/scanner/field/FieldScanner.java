package com.github.enjektor.core.scanner.field;

import gnu.trove.set.hash.THashSet;

import java.lang.reflect.Field;

public interface FieldScanner {
    THashSet<Field> scan(final Class<?> dependency);
}
