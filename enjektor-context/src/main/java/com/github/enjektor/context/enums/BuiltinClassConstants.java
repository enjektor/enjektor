package com.github.enjektor.context.enums;

import com.github.enjektor.utils.hash.ConcreteHashProvider;
import com.github.enjektor.utils.hash.HashProvider;

public enum BuiltinClassConstants {
    STRING(String.class),
    INTEGER(Integer.class);

    private HashProvider hashProvider = ConcreteHashProvider.getInstance();
    private final byte hash;
    private final Class<?> type;

    BuiltinClassConstants(Class<?> type) {
        this.type = type;
        this.hash = this.hashProvider.provideByteHash(type.getSimpleName());
    }

    public byte getHash() {
        return hash;
    }
}
