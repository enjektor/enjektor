package com.github.enjektor.context;

import com.github.enjektor.core.bean.pair.Pair;

import java.util.List;

public interface ApplicationContext {
    void init(List<Pair> pairs);

    void destroy();

    <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException;

    <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException;
}
