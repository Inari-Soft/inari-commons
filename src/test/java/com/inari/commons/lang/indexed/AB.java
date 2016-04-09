package com.inari.commons.lang.indexed;

public class AB extends A {
    protected AB() {
        super( Indexer.createIndexedTypeKey( AIndexedTypeKey.class, AB.class ) );
    }
}
