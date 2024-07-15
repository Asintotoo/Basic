package com.asintoto.basic.structures;

public final class Tuple<K, V> {

    private final K key;

    private final V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public String toLine() {
        return this.key + " - " + this.value;
    }

    @Override
    public String toString() {
        return this.toLine();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
