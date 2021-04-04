package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.tutorial.Util;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;

public class EnjektorApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InterruptedException {
        final DependencyInitializer dependencyInitializer = new DefaultDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(EnjektorApplication.class, dependencyInitializers);
        final Util bean = applicationContext.getBean(Util.class);

        final Class<AInt> aIntClass = AInt.class;
        final Constructor<AInt> declaredConstructor = AInt.class.getConstructor();
        System.out.println("declaredConstructor = " + declaredConstructor);

        bean.invoke();

        applicationContext.destroy();
        System.gc();
        System.runFinalization();
    }
}
