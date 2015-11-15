package com.inari.commons.lang.indexed;

public class AA extends A {

    protected AA() {
        super( Indexer.getIndexedTypeKey( AIndexedTypeKey.class, AA.class ) );
    }

}
