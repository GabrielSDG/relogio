package com.gabrieldemery.relogio.util;

public class DummyCacheProvider {

    public static <K, V> DummyCache<K, V> getCache() {
        return new SillyDummyCacheImpl<>();
    }

}