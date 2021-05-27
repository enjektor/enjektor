package com.github.enjektor;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class DefaultStringRandomizer implements StringRandomizer {

    @Override
    public void any() {
        System.out.println("works");
    }
}
