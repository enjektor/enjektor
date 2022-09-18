package com.github.enjektor.context.pg.interfaces;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public interface Sum {
    int sum(int a);
}
