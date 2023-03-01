package com.github.enjektor.core;

import com.github.enjektor.core.util.NamingUtils;
import com.github.enjektor.core.util.hash.ConcreteHashProvider;
import com.github.enjektor.core.util.hash.HashProvider;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class EnjektorDependency {

    private static final byte INITIAL_CAPACITY = (byte) 0x03;
    private static final float LOAD_FACTOR = 0.75f;
    private static final HashProvider HASH_PROVIDER = ConcreteHashProvider.getInstance();
    private final TByteObjectMap<Object> instancesOnRuntime = new TByteObjectHashMap<>(INITIAL_CAPACITY, LOAD_FACTOR);
    private final Class<?> classType;

    public EnjektorDependency(final Class<?> classType) {
        this.classType = classType;
        register(classType);
    }

    public final void register(String beanName, Object object) {
        final byte hash = HASH_PROVIDER.provideByteHash(beanName);
        instancesOnRuntime.put(hash, object);
    }

    public final void register(final Class<?> classType) {
        register(classType, Optional.empty());
    }

    public final void register(final Class<?> classType,
                               final String dependencyName) {
        register(classType, Optional.ofNullable(dependencyName));
    }

    public final void register(final Class<?> classType,
                               final Optional<String> dependencyNameOpt) {
        try {
            final Constructor<?> constructor = classType.getConstructor();
            final Object implementationInstance = constructor.newInstance();
            final String simpleClassName = NamingUtils.beanCase(classType.getSimpleName());
            final String className = dependencyNameOpt.orElse(simpleClassName);
            final byte hash = HASH_PROVIDER.provideByteHash(className);
            instancesOnRuntime.put(hash, implementationInstance);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            // TODO: add sl4fj
            e.printStackTrace();
        }
    }

    public final Object getDependency(final String dependencyUniqueName) {
        final byte hash = HASH_PROVIDER.provideByteHash(dependencyUniqueName);
        return instancesOnRuntime.size() != 1 ? instancesOnRuntime.get(hash) : getInstancesOnRuntime();
    }

    public final Object getSingularDependency() {
        return instancesOnRuntime.values()[0];
    }

    public Class<?> getClassType() {
        return classType;
    }

    public TByteObjectMap<Object> getInstancesOnRuntime() {
        return instancesOnRuntime;
    }

    @Override
    public String toString() {
        return "Dependency{" +
            "instancesOnRuntime=" + instancesOnRuntime +
            '}';
    }
}
