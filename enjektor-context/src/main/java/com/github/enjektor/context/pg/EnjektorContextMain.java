package com.github.enjektor.context.pg;

import com.github.enjektor.context.Enjektor;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;

public class EnjektorContextMain {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Enjektor enjektor = Enjektor.builder()
            .addDependencyInitializer(new ConcreteDependencyInitializer())
            .withMainClass(EnjektorContextMain.class)
            .build();

        SampleDependency dependency = enjektor.getDependency(SampleDependency.class);
        dependency.anyExecutableMethod();

    }

}
