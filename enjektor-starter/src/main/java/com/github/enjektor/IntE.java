package com.github.enjektor;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.ex.Int;
import com.github.enjektor.middleware.AuthenticationMiddleware;
import com.github.enjektor.middleware.LoggingMiddleware;
import com.github.enjektor.middleware.Middleware;
import com.github.enjektor.single.SingleComponent;

@Dependency
public class IntE {

    @Inject(qualifier = DefaultStringRandomizer.class)
    private StringRandomizer stringRandomizer;

    @Inject(qualifier = AuthenticationMiddleware.class)
    private Middleware middleware;

    @Inject(qualifier = LoggingMiddleware.class)
    private Middleware loggingMiddleware;

    @Inject
    private SingleComponent singleComponent;

    @Inject
    private Int anInt;

    public void invoke() {
        stringRandomizer.any();
        middleware.next();
        loggingMiddleware.next();
        anInt.any();
        singleComponent.invoke();
    }
}
