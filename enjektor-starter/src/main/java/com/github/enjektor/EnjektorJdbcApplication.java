package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.jdbc.EnjektorJdbc;

import java.util.Collections;
import java.util.List;

public class EnjektorJdbcApplication {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        final DependencyInitializer dependencyInitializer = new ConcreteDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(EnjektorJdbcApplication.class, dependencyInitializers);
        final EnjektorJdbc bean = applicationContext.getBean(EnjektorJdbc.class);
        bean.print();


    }
}
