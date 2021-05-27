package com.github.enjektor;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.ex.Int;
import com.github.enjektor.middleware.AuthenticationMiddleware;
import com.github.enjektor.middleware.Middleware;

@Dependency
public class IntE {

    @Inject(qualifier = DefaultStringRandomizer.class)
    private StringRandomizer stringRandomizer;

    @Inject(qualifier = AuthenticationMiddleware.class)
    private Middleware middleware;

    @Inject
    private Int anInt;

    public void invoke() {
        stringRandomizer.any();
        middleware.next();
        anInt.any();
    }
}
