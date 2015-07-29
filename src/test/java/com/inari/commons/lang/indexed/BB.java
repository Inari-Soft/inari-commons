package com.inari.commons.lang.indexed;

public class BB implements B {
    
    private final int index;
    
    public BB() {
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
