package com.github.enjektor.epel;

import com.github.enjektor.core.bean.Bean;

import java.util.Map;

public interface EpelExporter {
    Map<Class<?>, Bean> export();
}
