/*******************************************************************************
 * Copyright (c) 2015 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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

import com.inari.commons.lang.aspect.AspectSetBuilder;
import com.inari.commons.lang.functional.Clearable;

public final class IndexedTypeSet {
    
    protected final Class<? extends IndexedTypeKey> indexedBaseType;
    protected final IndexedTypeAspectSet aspect;
    
    private IndexedType[] indexedType;
    protected int size = 0;
    
    
    public IndexedTypeSet( Class<? extends IndexedTypeKey> indexedBaseType ) {
        this.indexedBaseType = indexedBaseType;
        int size = Indexer.getIndexedObjectSize( indexedBaseType );
        aspect = new IndexedTypeAspectSet( indexedBaseType, size );
        this.indexedType = new IndexedType[ size ];
    }
    
    public IndexedTypeSet( Class<? extends IndexedTypeKey> indexedBaseType, int length ) {
        this.indexedBaseType = indexedBaseType;
        aspect = new IndexedTypeAspectSet( indexedBaseType, length );
        this.indexedType = new IndexedType[ length ];
    }
    
    public final int size() {
        return size;
    }
    
    public final IndexedTypeAspectSet getAspect() {
        return aspect;
    }
    
    public final Class<? extends IndexedTypeKey> getIndexedBaseType() {
        return indexedBaseType;
    }
    
    public final boolean include( IndexedTypeAspectSet aspect ) {
        return this.aspect.include( aspect );
    }
    
    public final int length() {
        return indexedType.length;
    }

    public final IndexedType set( IndexedType indexedType ) {
        if ( indexedType == null ) {
            return null;
        }
        
        int typeIndex = indexedType.typeIndex();
        ensureCapacity( typeIndex );
        
        IndexedType old = this.indexedType[ typeIndex ];
        this.indexedType[ typeIndex ] = indexedType;
        IndexedTypeAspectBuilder.set( aspect, indexedType );
        if ( old == null ) {
            size++;
        }
        
        return old;
    }
    
    public final boolean contains( int index ) {
        if ( index < 0 || index >= indexedType.length ) {
            return false;
        }
        return indexedType[ index ] != null;
    }
    
    public final IndexedType remove( IndexedType indexedType ) {
        return this.remove( indexedType.typeIndex() );
    }
    
    public final IndexedType remove( IndexedTypeKey indexedTypeKey ) {
        return remove( indexedTypeKey.index() );
    }
    
    public final IndexedType remove( int index ) {
        IndexedType result = this.indexedType[ index ];
        this.indexedType[ index ] = null;
        AspectSetBuilder.reset( aspect, index );
        if ( result != null ) {
            size--;
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
        int typeIndex = Indexer.getIndexedTypeKey( indexedBaseType, type ).index();
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
        if ( aspect != null ) {
            IndexedTypeAspectBuilder.clear( aspect );
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
        
        private int i = aspect.nextSetBit( 0 );

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @SuppressWarnings( "unchecked" )
        @Override
        public T next() {
            int nextIndex = i;
            i = aspect.nextSetBit( i + 1 );
            return (T) indexedType[ nextIndex ];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
