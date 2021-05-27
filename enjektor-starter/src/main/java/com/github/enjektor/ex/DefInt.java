package com.github.enjektor.ex;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class DefInt implements Int {
    @Override
    public void any() {
        System.out.println("calisiyor ehhee");
    }
}
