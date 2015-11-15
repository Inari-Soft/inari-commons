package com.inari.commons.lang.indexed;

public class BA implements B {
    
    public final static BIndexedTypeKey TYPE_KEY = Indexer.getIndexedTypeKey( BIndexedTypeKey.class, BA.class );
    
    public BA() {
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
