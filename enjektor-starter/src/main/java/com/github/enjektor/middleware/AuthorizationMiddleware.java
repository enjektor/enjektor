package com.github.enjektor.middleware;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class AuthorizationMiddleware implements Middleware {

    @Override
    public void next() {
        System.out.println("authorized!");
    }
}
