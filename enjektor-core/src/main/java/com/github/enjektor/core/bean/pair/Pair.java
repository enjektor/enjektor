package com.github.enjektor.core.bean.pair;

import com.github.enjektor.core.bean.Bean;

public class Pair {
    private final Class<?> type;
    private final Bean bean;

    public Pair(Builder builder) {
        this.type = builder.type;
        this.bean = builder.bean;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Class<?> type;
        private Bean bean;

        public Builder type(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder bean(Bean bean) {
            this.bean = bean;
            return this;
        }

        public Pair build() {
            return new Pair(this);
        }
    }

    public Class<?> getType() {
        return type;
    }

    public Bean getBean() {
        return bean;
    }
}
