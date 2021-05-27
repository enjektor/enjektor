package com.github.enjektor.middleware;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public interface Middleware {
    void next();
}
