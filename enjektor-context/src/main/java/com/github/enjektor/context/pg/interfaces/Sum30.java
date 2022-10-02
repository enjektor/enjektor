package com.github.enjektor.context.pg.interfaces;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public class Sum30 implements Sum {

    @Override
    public int sum(int a) {
        return a + 30;
    }
}
