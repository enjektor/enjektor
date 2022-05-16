package com.github.enjektor.context;

import com.github.enjektor.core.bean.pair.Pair;

public interface ApplicationContext {
    void init();

    void destroy();

    void putDependency(Pair pair);

    <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException;

    <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException;
}
