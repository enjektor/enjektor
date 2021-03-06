package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.repo.A;

import java.util.Collections;
import java.util.List;

public class EnjektorApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        final DependencyInitializer dependencyInitializer = new DefaultDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(EnjektorApplication.class, dependencyInitializers);

        final A a4 = applicationContext.getBean(A.class, "a4");
        System.out.println(a4.a());
    }
}
