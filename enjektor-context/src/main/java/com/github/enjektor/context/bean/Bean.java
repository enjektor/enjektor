package com.github.enjektor.context.bean;

import com.github.enjektor.utils.NamingUtils;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Bean {

    private final Map<String, Object> instancesOnRuntime = new HashMap<>(3);
    private final Class<?> classType;

    public Bean(final Class<?> classType) {
        this.classType = classType;
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
            final ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
            final Constructor<?> constructor = reflectionFactory.newConstructorForSerialization(classType, Object.class.getDeclaredConstructor(new Class[0]));
            final Object implementationInstance = constructor.newInstance(new Object[0]);
            final String simpleClassName = NamingUtils.beanCase(classType.getSimpleName());
            final String className = dependencyNameOpt.orElse(simpleClassName);
            instancesOnRuntime.put(className, implementationInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO: add sl4fj
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public final Object getDependency(final String dependencyUniqueName) {
        if (instancesOnRuntime.size() == 1) {
            String classNameHolder = null;
            final Set<Map.Entry<String, Object>> entries = instancesOnRuntime.entrySet();
            for (Map.Entry<String, Object> entry : entries) classNameHolder = entry.getKey();
            return instancesOnRuntime.get(classNameHolder);
        }
        return instancesOnRuntime.get(dependencyUniqueName);
    }

    public Class<?> getClassType() {
        return classType;
    }

    public Map<String, Object> getInstancesOnRuntime() {
        return instancesOnRuntime;
    }

    @Override
    public String toString() {
        return "Dependency{" +
            "instancesOnRuntime=" + instancesOnRuntime +
            '}';
    }
}
