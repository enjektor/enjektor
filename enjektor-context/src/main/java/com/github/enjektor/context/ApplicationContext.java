package com.github.enjektor.context;

import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;

import java.util.List;

public interface ApplicationContext {
    void init(final List<Pair> pairs);

    void destroy();

    <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException;

    <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException;

    <T> Bean getNativeBean(final Class<T> classType) throws IllegalAccessException, InstantiationException;
}
