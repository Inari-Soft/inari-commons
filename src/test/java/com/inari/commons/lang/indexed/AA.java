package com.inari.commons.lang.indexed;

public class AA extends A {

    protected AA() {
        super( Indexer.createIndexedTypeKey( AIndexedTypeKey.class, AA.class ) );
    }

}
