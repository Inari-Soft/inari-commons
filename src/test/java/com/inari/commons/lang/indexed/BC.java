package com.inari.commons.lang.indexed;

public class BC implements B {
    
    private final int index;
    
    public BC() {
        index = Indexer.getIndexForType( this );
    }

    @Override
    public Class<B> indexedBaseType() {
        return B.class;
    }
    
    @Override
    public final Class<? extends IndexedType> indexedType() {
        return this.getClass();
    }

    @Override
    public int index() {
        return index;
    }

}
