package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;

import java.util.Collections;
import java.util.List;

public class EnjektorApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        final DependencyInitializer dependencyInitializer = new ConcreteDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(EnjektorApplication.class, dependencyInitializers, null);
        final IntE bean = applicationContext.getBean(IntE.class);
        bean.invoke();

        applicationContext.destroy();
        System.gc();
        System.runFinalization();
    }
}
