package com.inari.commons.lang.indexed;

public class AC extends A {
    protected AC() {
        super( Indexer.createIndexedTypeKey( AIndexedTypeKey.class, AC.class ) );
    }
}
