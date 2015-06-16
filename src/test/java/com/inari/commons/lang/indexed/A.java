package com.inari.commons.lang.indexed;

public abstract class A implements Indexed {
    
    private final int index;
    
    protected A() {
        index = IndexProvider.getIndexForType( this.getClass(), A.class );
    }

    @Override
    public final Class<A> indexedType() {
        return A.class;
    }

    @Override
    public final int index() {
        return index;
    }

}
