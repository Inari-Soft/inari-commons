package com.inari.commons.lang.indexed;

public class BC implements B, Indexed {
    
    private final int index;
    
    public BC() {
        index = Indexer.getIndexForType( this.getClass() );
    }

    @Override
    public Class<B> indexedType() {
        return B.class;
    }

    @Override
    public int index() {
        return index;
    }

}
