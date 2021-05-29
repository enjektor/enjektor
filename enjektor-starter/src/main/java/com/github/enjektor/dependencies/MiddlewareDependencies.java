package com.github.enjektor.dependencies;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.middleware.LoggingMiddleware;
import com.github.enjektor.middleware.Middleware;
import com.github.enjektor.single.SingleComponent;

@Dependencies
public class MiddlewareDependencies {

    @Dependency
    public Middleware loggingMiddleware() {
        return new LoggingMiddleware();
    }

    @Dependency
    public SingleComponent singleComponent() {
        return new SingleComponent();
    }
}
