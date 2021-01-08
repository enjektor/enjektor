package com.github.enjektor.repo;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.annotations.Qualifier;

@Dependency
public class A2 implements A {

    @Inject
    @Qualifier("a3")
    private A a3;

    @Override
    public String a() {
        return "usta";
    }
}
