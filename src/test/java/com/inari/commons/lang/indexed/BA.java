package com.inari.commons.lang.indexed;

public class BA implements B {
    
    private final int index;
    
    public BA() {
        index = IndexProvider.getIndexForType( this.getClass() );
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
