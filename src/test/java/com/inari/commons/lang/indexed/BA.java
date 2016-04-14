package com.inari.commons.lang.indexed;

public class BA implements B {
    
    public final static BIndexedTypeKey TYPE_KEY = Indexer.createIndexedTypeKey( BIndexedTypeKey.class, BA.class );
    
    public BA() {
        Indexer.getIndexedTypeKey( BIndexedTypeKey.class, BA.class );
    }

    @Override
    public IndexedTypeKey indexedTypeKey() {
        return TYPE_KEY;
    }

}
