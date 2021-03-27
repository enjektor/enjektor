package com.github.enjektor.core.scanner.field;

import gnu.trove.set.hash.THashSet;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DefaultFieldScanner implements FieldScanner {

    @Override
    public THashSet<Field> scan(Class<?> dependency) {
        final THashSet<Field> classFields = new THashSet<>(3);
        final Field[] declaredFields = dependency.getDeclaredFields();
        classFields.addAll(Arrays.asList(declaredFields));
        return classFields;
    }
}
