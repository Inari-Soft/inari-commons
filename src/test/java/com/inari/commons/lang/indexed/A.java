package com.inari.commons.lang.indexed;

import com.inari.commons.lang.aspect.AspectGroup;

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
        
        private static final AspectGroup ASPECT_GROUP = new AspectGroup( "AIndexedTypeKey" );
        
        protected AIndexedTypeKey( Class<? extends A> indexedType ) {
            super( indexedType );
        }

        @Override
        public final Class<A> baseType() {
            return A.class;
        }

        @Override
        public AspectGroup aspectGroup() {
            return ASPECT_GROUP;
        }
    }

}
