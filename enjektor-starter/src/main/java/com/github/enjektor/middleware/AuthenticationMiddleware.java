package com.github.enjektor.middleware;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class AuthenticationMiddleware implements Middleware {

    @Override
    public void next() {
        System.out.println("authentication middleware!!");
    }
}
