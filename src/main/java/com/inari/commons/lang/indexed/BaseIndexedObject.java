package com.inari.commons.lang.indexed;

import com.inari.commons.lang.Disposable;

public abstract class BaseIndexedObject implements IndexedObject, Disposable {
    
    protected int indexedId;
    
    protected BaseIndexedObject() {
        indexedId = IndexProvider.nextObjectIndex( getIndexedObjectType() );
    }
    
    protected BaseIndexedObject( int indexedId ) {
        if ( indexedId < 0 ) {
            this.indexedId = IndexProvider.nextObjectIndex( getIndexedObjectType() );
        } else {
            this.indexedId = indexedId;
            IndexProvider.setIndexedObjectIndex( this );
        }
    }

    @Override
    public final int indexedId() {
        return indexedId;
    }

    @Override
    public final void dispose() {
        IndexProvider.disposeIndexedObject( this );
        indexedId = -1;
    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + indexedId;
        result = prime * result + getIndexedObjectType().hashCode();
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        BaseIndexedObject other = (BaseIndexedObject) obj;
        if ( indexedId != other.indexedId )
            return false;
        if ( getIndexedObjectType() == null ) {
            if ( other.getIndexedObjectType() != null )
                return false;
        } else if ( !getIndexedObjectType().equals( other.getIndexedObjectType() ) )
            return false;
        return true;
    }
}
