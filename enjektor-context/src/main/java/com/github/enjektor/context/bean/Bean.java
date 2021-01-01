package com.github.enjektor.context.bean;

import com.github.enjektor.utils.NamingUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Bean {

    private Map<String, Object> instancesOnRuntime = new HashMap<>(3);

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
            final Object implementationInstance = classType.newInstance();
            final String simpleClassName = NamingUtils.beanCase(classType.getSimpleName());
            final String className = dependencyNameOpt.orElse(simpleClassName);
            instancesOnRuntime.put(className, implementationInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO: add sl4fj
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

    @Override
    public String toString() {
        return "Dependency{" +
            "instancesOnRuntime=" + instancesOnRuntime +
            '}';
    }
}
