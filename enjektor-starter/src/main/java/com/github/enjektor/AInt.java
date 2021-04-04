package com.github.enjektor;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class AInt implements Int {
    @Override
    public int x() {
        return -1;
    }
}
