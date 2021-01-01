package com.github.enjektor.core.scanner;

import com.github.enjektor.core.annotations.Inject;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class FieldScannerImpl implements FieldScanner {

    @Override
    public final Set<Field> scan(final Class<?> dependency) {

        final Set<Field> injectedFields = new HashSet<>(3);
        final Field[] fields = dependency.getDeclaredFields();
        for (final Field field : fields)
            if (field.isAnnotationPresent(Inject.class))
                injectedFields.add(field);

        return injectedFields;
    }
}

