package com.inari.commons.lang.indexed;

public interface B extends IndexedType {
    class BIndexedTypeKey extends IndexedTypeKey {
        protected BIndexedTypeKey( Class<? extends B> indexedType ) {
            super( indexedType );
        }
        @Override
        public final Class<B> baseType() {
            return B.class;
        }
    }

}
