package com.inari.commons.lang.indexed;

public class BB implements B, IndexedType {
    
    private final int index;
    
    public BB() {
        index = Indexer.getIndexForType( this.getClass() );
    }

    @Override
    public Class<B> indexedBaseType() {
        return B.class;
    }

    @Override
    public int index() {
        return index;
    }

}
