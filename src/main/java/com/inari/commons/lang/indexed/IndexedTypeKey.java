package com.inari.commons.lang.indexed;

public abstract class IndexedTypeKey extends BaseIndexedObject implements IIndexedTypeKey {
    
    public final Class<? extends IndexedType> indexedType;

    protected IndexedTypeKey( Class<? extends IndexedType> indexedType ) {
        super();
        this.indexedType = indexedType;
        aspectGroup().createAspect( name(), index() );
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
    public final String name() {
        return indexedType.getName().replace( ".", "_" );
    }

    @Override
    public String toString() {
        return indexedObjectType().getSimpleName() + " [indexedType=" + indexedType + ", index=" + index + "]";
    }

    final void disable() {
        aspectGroup().disposeAspect( index() );
        index = -1;
    }

    final void enable( int index ) {
        this.index = index;
        aspectGroup().createAspect( name(), index() );
    }

}
