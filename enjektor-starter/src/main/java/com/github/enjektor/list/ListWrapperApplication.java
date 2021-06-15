package com.github.enjektor.list;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.auto.configuration.BeanAutoConfiguration;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.epel.EpelBeanAutoConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ListWrapperApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        final DependencyInitializer dependencyInitializer = new ConcreteDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Collections.singletonList(dependencyInitializer);

        BeanAutoConfiguration epel = new EpelBeanAutoConfiguration();
        final Pair ex = epel.export();

        final Map<Class<?>, Bean> beans = new WeakHashMap<>();
        beans.put(ex.getType(), ex.getBean());

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(ListWrapperApplication.class, dependencyInitializers, beans);

        final ListWrapper bean = applicationContext.getBean(ListWrapper.class);
        bean.invoke();
    }
}
