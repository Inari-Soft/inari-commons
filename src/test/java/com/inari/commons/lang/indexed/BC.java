package com.inari.commons.lang.indexed;

public class BC implements B {
    
    public final static BIndexedTypeKey TYPE_KEY = Indexer.createIndexedTypeKey( BIndexedTypeKey.class, BC.class );
    
    public BC() {
        TYPE_KEY.index();
    }

    @Override
    public IndexedTypeKey indexedTypeKey() {
        return TYPE_KEY;
    }

}
