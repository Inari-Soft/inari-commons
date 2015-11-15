package com.inari.commons.lang.indexed;

public class AC extends A {
    protected AC() {
        super( Indexer.getIndexedTypeKey( AIndexedTypeKey.class, AC.class ) );
    }
}
