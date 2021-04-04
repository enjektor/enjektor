package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class OtherDependency {
    public void invoke() {
        System.out.println("invoked!!!");
    }
}
