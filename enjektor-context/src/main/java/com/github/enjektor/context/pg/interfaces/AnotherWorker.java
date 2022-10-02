package com.github.enjektor.context.pg.interfaces;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class AnotherWorker implements Worker {
    @Override
    public void work() {
        System.out.println("another worker");
    }
}
