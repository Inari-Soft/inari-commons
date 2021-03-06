/*******************************************************************************
 * Copyright (c) 2015 - 2016 - 2016, Andreas Hefti, inarisoft@yahoo.de 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.inari.commons.lang.indexed;

import java.util.Iterator;

import com.inari.commons.lang.Clearable;
import com.inari.commons.lang.aspect.Aspect;
import com.inari.commons.lang.aspect.Aspects;

public final class IndexedTypeSet {
    
    protected final Class<? extends IndexedTypeKey> indexedBaseType;
    protected Aspects aspects = null;
    
    private IndexedType[] indexedType;
    protected int size = 0;
    
    public IndexedTypeSet( Class<? extends IndexedTypeKey> indexedBaseType  ) {
        this.indexedBaseType = indexedBaseType;
        int size = Indexer.getIndexedObjectSize( indexedBaseType );
        this.indexedType = new IndexedType[ size ];
    }
    
    public IndexedTypeSet( Class<? extends IndexedTypeKey> indexedBaseType, int length ) {
        this.indexedBaseType = indexedBaseType;
        this.indexedType = new IndexedType[ length ];
    }
    
    public final int size() {
        return size;
    }
    
    public final Aspects getAspect() {
        return aspects;
    }
    
    public final Class<? extends IndexedTypeKey> getIndexedBaseType() {
        return indexedBaseType;
    }
    
    public final boolean include( Aspects aspects ) {
        if ( this.aspects == null ) {
            return false;
        }
        
        return this.aspects.include( aspects );
    }
    
    public final int length() {
        return indexedType.length;
    }

    public final IndexedType set( IndexedType indexedType ) {
        if ( indexedType == null ) {
            return null;
        }
        
        if ( aspects == null ) {
            aspects = indexedType
                .indexedTypeKey()
                .aspectGroup()
                .createAspects();
        }
        
        int typeIndex = indexedType.indexedTypeKey().index();
        ensureCapacity( typeIndex );
        
        IndexedType old = this.indexedType[ typeIndex ];
        this.indexedType[ typeIndex ] = indexedType;
        aspects.set( indexedType.indexedTypeKey() );
        if ( old == null ) {
            size++;
        }
        
        return old;
    }
    
    public final void setAll( IndexedTypeSet other ) {
        if ( other == null ) {
            return;
        }
        
        ensureCapacity( other.length() );
        for ( int i = 0; i < other.length(); i++ ) {
            if ( other.indexedType[ i ] == null ) {
                continue;
            }
            set( other.indexedType[ i ] );
        }
    }
    
    public final boolean contains( int index ) {
        if ( index < 0 || index >= indexedType.length ) {
            return false;
        }
        return indexedType[ index ] != null;
    }
    
    public final IndexedType remove( IndexedType indexedType ) {
        return this.remove( indexedType.indexedTypeKey().index() );
    }
    
    public final IndexedType remove( IndexedTypeKey indexedTypeKey ) {
        return remove( indexedTypeKey.index() );
    }
    
    public final IndexedType remove( int index ) {
        IndexedType result = this.indexedType[ index ];
        this.indexedType[ index ] = null;
        if ( result != null ) {
            size--;
            aspects.reset( result.indexedTypeKey() );
        }
        return result;
    }
    
    @SuppressWarnings( "unchecked" )
    public final <I extends IndexedType> I get( IndexedTypeKey indexedTypeKey ) {
        int typeIndex = indexedTypeKey.index();
        IndexedType indexedType = this.indexedType[ typeIndex ];
        return (I) indexedType;
    }
    
    public final <I extends IndexedType> I get( Class<I> type ) {
        int typeIndex = Indexer.createIndexedTypeKey( indexedBaseType, type ).index();
        IndexedType indexedType = this.indexedType[ typeIndex ];
        return type.cast( indexedType );
    }
    
    @SuppressWarnings( "unchecked" )
    public final <I extends IndexedType> I get( int typeIndex ) {
        return (I) this.indexedType[ typeIndex ];
    }
    
    public <T extends IndexedType> Iterable<T> getIterable() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new IndexedIterator<T>();
            }
        };
    }

    private void ensureCapacity( int index ) {
        if ( index < indexedType.length ) {
            return;
        }
        
        IndexedType[] newArray = new IndexedType[ index + 1 ];
        System.arraycopy( indexedType, 0, newArray, 0, indexedType.length );
        indexedType = newArray;
    }
    
    public final void clear() {
        if ( aspects != null ) {
            aspects.clear();
        }
        size = 0;
        if ( indexedType != null ) {
            for ( int i = 0; i < indexedType.length; i++ ) {
                if ( indexedType[ i ] != null && indexedType[ i ] instanceof Clearable ) {
                    ( (Clearable) indexedType[ i ] ).clear();
                }
                indexedType[ i ] = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "IndexedTypeSet [ indexedBaseType=" ).append( indexedBaseType.getSimpleName() );
        sb.append( " length=" ).append( indexedType.length ).append( " size:" ).append( size );
        sb.append( ", indexed={" );
        boolean deleteLastSep = false;
        for ( int i = 0; i < indexedType.length; i++ ) {
            deleteLastSep = true;
            sb.append( ( indexedType[ i ] != null )? indexedType[ i ].getClass().getSimpleName() : "null" ).append( "," );
        }
        if ( deleteLastSep ) {
            sb.deleteCharAt( sb.length() - 1 );
        }
        sb.append( "} ]" );
        return  sb.toString();
    }
    
    private final class IndexedIterator<T extends IndexedType> implements Iterator<T> {
        
        private final Iterator<Aspect> delegate = ( aspects!= null )? aspects.iterator() : null;

        @Override
        public boolean hasNext() {
            return delegate != null && delegate.hasNext();
        }

        @SuppressWarnings( "unchecked" )
        @Override
        public T next() {
            return (T) indexedType[ delegate.next().index() ];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
