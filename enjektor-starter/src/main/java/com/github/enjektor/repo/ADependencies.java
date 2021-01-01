package com.github.enjektor.repo;

import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;

@Dependencies
public class ADependencies {

    @Dependency
    public A3 a3() {
        return new A3();
    }

    @Dependency
    public A4 a4() {
        return new A4();
    }
}
