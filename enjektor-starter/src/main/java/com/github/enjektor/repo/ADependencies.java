package com.github.enjektor.repo;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;

@Dependencies
public class ADependencies {

    @Dependency
    public A a3() {
        return new A3();
    }

    @Dependency
    public A a4() {
        return new A4();
    }
}
