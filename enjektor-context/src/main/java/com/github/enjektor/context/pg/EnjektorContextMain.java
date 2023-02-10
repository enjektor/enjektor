package com.github.enjektor.context.pg;

import com.github.enjektor.context.Enjektor;
import com.github.enjektor.context.configuration.EnjektorConfiguration;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;

public class EnjektorContextMain {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        final EnjektorConfiguration configuration = EnjektorConfiguration.builder()
            .withMainClass(EnjektorContextMain.class)
            .build();

        final Enjektor enjektor = Enjektor.builder()
            .configuration(configuration)
            .addDependencyInitializer(new DefaultDependencyInitializer())
            .build();

        SampleDependency dependency = enjektor.getDependency(SampleDependency.class);
        dependency.anyExecutableMethod();
    }

}
