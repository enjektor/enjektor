package com.github.enjektor;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class BInt implements Int {
    @Override
    public int x() {
        return 2;
    }
}
