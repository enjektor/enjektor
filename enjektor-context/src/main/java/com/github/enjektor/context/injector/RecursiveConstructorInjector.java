package com.github.enjektor.context.injector;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.core.annotations.Qualifier;
import com.github.enjektor.core.scanner.field.DefaultFieldScanner;
import com.github.enjektor.core.scanner.field.FieldScanner;
import com.github.enjektor.utils.NamingUtils;
import gnu.trove.set.hash.THashSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RecursiveConstructorInjector implements Injector {

    private static final FieldScanner fieldScanner = new DefaultFieldScanner();
    private final Map<Class<?>, Bean> beanHolderMap;

    public RecursiveConstructorInjector(Map<Class<?>, Bean> beanHolderMap) {
        this.beanHolderMap = beanHolderMap;
    }

    @Override
    public void inject(Object object) throws IllegalAccessException {
        final Class<?> klass = object.getClass();
        final THashSet<Field> fields = fieldScanner.scan(klass);
        if (fields.isEmpty()) return;

        final Class<?>[] constructorArgs = constructorArgs(fields);

        try {
            final Constructor<?> constructor = declaredConstructor(klass, constructorArgs);
            final Object[] injectedInstancesOnContext = new Object[fields.size()];

            byte counter = (byte) 0;
            for (Field field : fields) {
                final Class<?> fieldClassType = field.getType();
                final Bean bean = beanHolderMap.get(fieldClassType);

                field.setAccessible(true);
                if (fieldClassType.isInterface()) {
                    if (field.isAnnotationPresent(Qualifier.class)) {
                        final Qualifier qualifier = field.getAnnotation(Qualifier.class);
                        final Object beanInstance = bean.getDependency(qualifier.value());
                        injectedInstancesOnContext[counter] = beanInstance;
                    } else {
                        final Object beanInstance = bean.getDependency("");
                        injectedInstancesOnContext[counter] = beanInstance;
                    }
                } else {
                    final String beanName = NamingUtils.beanCase(fieldClassType.getSimpleName());
                    final Object beanInstance = bean.getDependency(beanName);
                    injectedInstancesOnContext[counter] = beanInstance;
                }
                counter++;
            }

            final Object createdInstanceWithConstructor = constructor.newInstance(injectedInstancesOnContext);
            final Bean bean = beanHolderMap.get(klass);
            final Map<String, Object> instancesOnRuntime = bean.getInstancesOnRuntime();
            instancesOnRuntime.replace(NamingUtils.beanCase(klass.getSimpleName()), createdInstanceWithConstructor);

            inject(createdInstanceWithConstructor);

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }

    }

    private Constructor<?> declaredConstructor(final Class<?> klass,
                                               final Class<?>[] args) throws NoSuchMethodException {
        return klass.getDeclaredConstructor(args);
    }

    private Class<?>[] constructorArgs(final THashSet<Field> fields) {
        final Class<?>[] fieldTypes = new Class[fields.size()];
        byte count = (byte) 0;
        for (Field field : fields) fieldTypes[count++] = field.getType();
        return fieldTypes;
    }
}
