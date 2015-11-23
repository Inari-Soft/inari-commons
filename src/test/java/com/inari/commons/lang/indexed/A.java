package com.inari.commons.lang.indexed;

public abstract class A implements IndexedType {
    
    public final AIndexedTypeKey indexedTypeKey;
    
    protected A( AIndexedTypeKey indexedTypeKey ) {
        this.indexedTypeKey = indexedTypeKey;
    }

    @Override
    public IndexedTypeKey indexedTypeKey() {
        return indexedTypeKey;
    }
    
    public static final class AIndexedTypeKey extends IndexedTypeKey {
        protected AIndexedTypeKey( Class<? extends A> indexedType ) {
            super( indexedType );
        }

        @Override
        protected final Class<A> baseIndexedType() {
            return A.class;
        }
    }

}
