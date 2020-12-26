package com.github.enjektor.context;

public class ApplicationContextImpl implements ApplicationContext {

    @Override
    public <T> T getBean(Class<T> classType) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> classType, String fieldName) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
