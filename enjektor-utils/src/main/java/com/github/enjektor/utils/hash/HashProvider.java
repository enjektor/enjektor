package com.github.enjektor.utils.hash;

public interface HashProvider {
    byte provideByteHash(String source);
    short provideShortHash(String source);
}
