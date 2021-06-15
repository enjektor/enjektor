package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.single.IntE;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class EnjektorApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        final DependencyInitializer dependencyInitializer = new ConcreteDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        final Map<Class<?>, Bean> beans = new WeakHashMap<>();

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(EnjektorApplication.class, dependencyInitializers, beans);
        final IntE bean = applicationContext.getBean(IntE.class);
        bean.invoke();

        applicationContext.destroy();
        System.gc();
        System.runFinalization();
    }
}
