package com.github.enjektor.context.injector;

public interface Injector {
    void inject(Object object) throws IllegalAccessException;
}
