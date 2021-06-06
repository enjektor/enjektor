package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.epel.ConcreteEpelExporter;
import com.github.enjektor.epel.EpelExporter;
import com.github.enjektor.jdbc.EnjektorJdbc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class EnjektorJdbcApplication {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        final DependencyInitializer dependencyInitializer = new ConcreteDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        final EpelExporter concreteEpelExporter = new ConcreteEpelExporter();

        final Map<Class<?>, Bean> beans = new WeakHashMap<>();
        beans.putAll(concreteEpelExporter.export());


        final ApplicationContext applicationContext = new PrimitiveApplicationContext(EnjektorJdbcApplication.class, dependencyInitializers, beans);

        final EnjektorJdbc bean = applicationContext.getBean(EnjektorJdbc.class);
        bean.print();


    }
}
