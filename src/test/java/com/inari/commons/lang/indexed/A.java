package com.inari.commons.lang.indexed;

public abstract class A implements IndexedType {
    
    private final int index;
    
    protected A() {
        index = Indexer.getIndexForType( this.getClass(), A.class );
    }

    @Override
    public final Class<A> indexedBaseType() {
        return A.class;
    }

    @Override
    public final Class<? extends IndexedType> indexedType() {
        return this.getClass();
    }

    @Override
    public final int index() {
        return index;
    }

}
