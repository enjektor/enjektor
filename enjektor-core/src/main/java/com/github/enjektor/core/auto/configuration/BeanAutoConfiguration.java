package com.github.enjektor.core.auto.configuration;

import com.github.enjektor.core.bean.pair.Pair;

public interface BeanAutoConfiguration {
    Pair export();
    Pair export(String profileProperty);
}
