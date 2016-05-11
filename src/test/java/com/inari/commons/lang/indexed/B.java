package com.inari.commons.lang.indexed;

import com.inari.commons.lang.aspect.AspectGroup;

public interface B extends IndexedType {
    class BIndexedTypeKey extends IndexedTypeKey {
        
        private static final AspectGroup ASPECT_GROUP = new AspectGroup( "BIndexedTypeKey" );
        
        protected BIndexedTypeKey( Class<? extends B> indexedType ) {
            super( indexedType );
        }
        @Override
        public final Class<B> baseType() {
            return B.class;
        }
        
        @Override
        public AspectGroup aspectGroup() {
            return ASPECT_GROUP;
        }
    }

}
