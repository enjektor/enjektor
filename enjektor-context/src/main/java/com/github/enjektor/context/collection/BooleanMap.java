package com.github.enjektor.context.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class BooleanMap<K extends Boolean, V> implements Map<K, V> {
    private static final byte SIZE = (byte) 0x2;
    private V[] elements;

    public BooleanMap(Class<?> type) {
        this.elements = (V[]) Array.newInstance(type, SIZE);
    }


    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        boolean k = (boolean) key;
        int location = 1 & Boolean.hashCode(k) >> 1;
        return elements[location];
    }

    @Override
    public V put(K key, V value) {
        int location = 1 & Boolean.hashCode(key) >> 1;
        elements[location] = value;
        return value;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
