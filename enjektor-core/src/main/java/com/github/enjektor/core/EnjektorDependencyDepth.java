package com.github.enjektor.core;

import java.util.ArrayList;
import java.util.List;

public class EnjektorDependencyDepth {
    private int level;
    private List<Class<?>> metadata = new ArrayList<>();

    public EnjektorDependencyDepth(int level) {
        this.level = level;
    }

    public void add(Class<?> klass) {
        metadata.add(klass);
    }

    public int getLevel() {
        return level;
    }

    public List<Class<?>> getMetadata() {
        return metadata;
    }
}

