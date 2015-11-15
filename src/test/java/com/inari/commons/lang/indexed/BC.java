package com.inari.commons.lang.indexed;

public class BC implements B {
    
    public final static BIndexedTypeKey TYPE_KEY = Indexer.getIndexedTypeKey( BIndexedTypeKey.class, BC.class );
    
    public BC() {
        typeIndex();
    }

    @Override
    public int typeIndex() {
        return TYPE_KEY.index();
    }

    @Override
    public IndexedTypeKey indexedTypeKey() {
        return TYPE_KEY;
    }

}
