package com.github.enjektor.core.util.hash;

public class ConcreteHashProvider implements HashProvider {

    public static final byte HASH_KEY = (byte) 0x7F;
    public static final byte BYTE_MASKING_VALUE = (byte) 0xFF;
    public static final short SHORT_MASKING_VALUE = (byte) 0xFFFF;

    private static HashProvider instance;

    private ConcreteHashProvider() {
    }

    @Override
    public byte provideByteHash(String source) {
        return (byte) ((source.hashCode() % HASH_KEY) & BYTE_MASKING_VALUE);
    }

    @Override
    public short provideShortHash(String source) {
        return (short) ((source.hashCode() % HASH_KEY) & SHORT_MASKING_VALUE);
    }

    public static HashProvider getInstance() {
        if (instance == null) instance = new ConcreteHashProvider();
        return instance;
    }
}
