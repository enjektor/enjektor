package com.github.enjektor.repo;

import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.annotations.Qualifier;

public class A3 implements A {

    @Inject
    @Qualifier("a4")
    private A a4;

    @Override
    public String a() {
        return "feyza";
    }
}
