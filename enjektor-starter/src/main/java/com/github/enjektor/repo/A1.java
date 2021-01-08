package com.github.enjektor.repo;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.annotations.Qualifier;

@Dependency
public class A1 implements A {

    @Inject
    @Qualifier("a2")
    private A a2;

    @Override
    public String a() {
        return "enes";
    }
}
