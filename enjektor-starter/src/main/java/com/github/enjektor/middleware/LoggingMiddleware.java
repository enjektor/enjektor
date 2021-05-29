package com.github.enjektor.middleware;

public class LoggingMiddleware implements Middleware {

    @Override
    public void next() {
        System.out.println("logged!");
    }
}
