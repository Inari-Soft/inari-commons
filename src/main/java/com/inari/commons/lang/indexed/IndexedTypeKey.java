package com.inari.commons.lang.indexed;

public abstract class IndexedTypeKey extends BaseIndexedObject {
    
    public final Class<? extends IndexedType> indexedType;

    protected IndexedTypeKey( Class<? extends IndexedType> indexedType ) {
        super();
        this.indexedType = indexedType;
    }

    @Override
    public final Class<? extends IndexedTypeKey> indexedObjectType() {
        return this.getClass();
    }
    
    protected abstract Class<?> baseIndexedType();

    @Override
    public final int index() {
        if ( index < 0 ) {
            index = Indexer.nextObjectIndex( indexedObjectType() );
        }
        return index;
    }

    @Override
    public final String toString() {
        return indexedObjectType().getSimpleName() + " [indexedType=" + indexedType + ", index=" + index + "]";
    }

}
