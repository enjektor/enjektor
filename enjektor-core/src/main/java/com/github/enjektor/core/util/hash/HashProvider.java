package com.github.enjektor.core.util.hash;

public interface HashProvider {
    byte provideByteHash(String source);
    short provideShortHash(String source);
}
