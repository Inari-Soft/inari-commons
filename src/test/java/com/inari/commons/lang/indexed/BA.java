package com.inari.commons.lang.indexed;

public class BA implements B {
    
    private final int index;
    
    public BA() {
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
