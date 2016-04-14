package com.inari.commons.lang.indexed;

public abstract class IndexedTypeKey extends BaseIndexedObject implements IIndexedTypeKey {
    
    public final Class<? extends IndexedType> indexedType;

    protected IndexedTypeKey( Class<? extends IndexedType> indexedType ) {
        super();
        this.indexedType = indexedType;
    }

    @Override
    public final Class<? extends IndexedTypeKey> indexedObjectType() {
        return this.getClass();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public final <T> Class<T> type() {
        return (Class<T>) indexedType;
    }

    @Override
    public final int typeIndex() {
        return index();
    }

    @Override
    public String toString() {
        return indexedObjectType().getSimpleName() + " [indexedType=" + indexedType + ", index=" + index + "]";
    }

}
