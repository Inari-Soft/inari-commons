package com.inari.commons.lang.functional;

public interface Effect<T> {
    void apply( T t );
}
