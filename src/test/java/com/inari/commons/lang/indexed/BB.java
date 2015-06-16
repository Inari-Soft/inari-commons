package com.inari.commons.lang.indexed;

public class BB implements B, Indexed {
    
    private final int index;
    
    public BB() {
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
