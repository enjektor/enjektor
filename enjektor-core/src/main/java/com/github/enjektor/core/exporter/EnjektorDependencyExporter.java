package com.github.enjektor.core.exporter;

import com.github.enjektor.core.bean.Bean;

import java.util.Map;

public interface EnjektorDependencyExporter {
    Map<Class<?>, Bean> export(String profileProperty);
    Map<Class<?>, Bean> export();
}
