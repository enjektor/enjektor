package com.github.enjektor.context.pg.interfaces;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class DogImpl implements Dog {

    @Override
    public void bark() {
        System.out.println("bark");
    }
}
