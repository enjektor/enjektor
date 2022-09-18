package com.github.enjektor.core.scanner.field;

import com.github.enjektor.core.annotations.Inject;
import gnu.trove.set.hash.THashSet;

import java.lang.reflect.Field;

public class InjectAnnotationFieldScanner implements FieldScanner {

    @Override
    public final THashSet<Field> scan(final Class<?> dependency) {
        final THashSet<Field> injectedFields = new THashSet<>(3);
        final Field[] fields = dependency.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Inject.class))
                injectedFields.add(field);
        }

        return injectedFields;
    }
}

