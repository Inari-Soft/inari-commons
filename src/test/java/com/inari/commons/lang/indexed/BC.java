package com.inari.commons.lang.indexed;

public class BC implements B, IndexedType {
    
    private final int index;
    
    public BC() {
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
