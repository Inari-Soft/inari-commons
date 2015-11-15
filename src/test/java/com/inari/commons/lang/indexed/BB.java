package com.inari.commons.lang.indexed;

public class BB implements B {
    
    public final static BIndexedTypeKey TYPE_KEY = Indexer.getIndexedTypeKey( BIndexedTypeKey.class, BB.class );
    
    public BB() {
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
