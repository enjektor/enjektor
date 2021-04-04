package com.github.enjektor.context;

public interface ApplicationContext {
    void init();
    void destroy();

    <T> T getBean(final Class<T> classType) throws IllegalAccessException, InstantiationException;

    <T> T getBean(final Class<T> classType, final String fieldName) throws IllegalAccessException, InstantiationException;
}
