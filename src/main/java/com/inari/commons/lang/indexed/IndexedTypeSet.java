/*******************************************************************************
 * Copyright (c) 2015, Andreas Hefti, inarisoft@yahoo.de 
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

import com.inari.commons.lang.aspect.AspectBuilder;
import com.inari.commons.lang.functional.Clearable;

public final class IndexedTypeSet {
    
    private IndexedType[] indexedType;
    protected final Class<? extends IndexedBaseType> indexedBaseType;
    protected final IndexedTypeAspect aspect;
    protected int size = 0;
    
    
    public IndexedTypeSet( Class<? extends IndexedBaseType> indexedBaseType ) {
        this.indexedBaseType = Indexer.findIndexedType( indexedBaseType );
        int size = Indexer.getIndexedTypeSize( this.indexedBaseType );
        aspect = new IndexedTypeAspect( indexedBaseType, size );
        this.indexedType = new IndexedType[ size ];
    }
    
    public IndexedTypeSet( Class<? extends IndexedBaseType> indexedBaseType, int length ) {
        this.indexedBaseType = Indexer.findIndexedType( indexedBaseType );
        aspect = new IndexedTypeAspect( indexedBaseType, length );
        this.indexedType = new IndexedType[ length ];
    }
    
    public final int size() {
        return size;
    }
    
    public final IndexedTypeAspect getAspect() {
        return aspect;
    }
    
    public final Class<? extends IndexedBaseType> getIndexedBaseType() {
        return indexedBaseType;
    }
    
    public final boolean include( IndexedTypeAspect aspect ) {
        return this.aspect.include( aspect );
    }
    
    public final int length() {
        return indexedType.length;
    }

    public final IndexedType set( IndexedType indexedType ) {
        if ( indexedType == null ) {
            return null;
        }
        
        checkIndexedType( indexedType, indexedBaseType );
        ensureCapacity( indexedType.index() );
        
        IndexedType old = this.indexedType[ indexedType.index() ];
        this.indexedType[ indexedType.index() ] = indexedType;
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
        checkIndexedType( indexedType, indexedBaseType );
        return this.remove( indexedType.index() );
    }
    
    public final <I extends IndexedType> I remove( Class<I> indexedType ) {
        Indexer.checkIndexedType( indexedType, indexedBaseType );
        return indexedType.cast( remove( Indexer.getIndexForType( indexedType, indexedBaseType ) ) );
    }
    
    public final IndexedType remove( int index ) {
        IndexedType result = this.indexedType[ index ];
        this.indexedType[ index ] = null;
        AspectBuilder.reset( aspect, index );
        if ( result != null ) {
            size--;
        }
        return result;
    }
    
    public final <I extends IndexedType> I get( Class<I> type ) {
        int typeIndex = Indexer.getIndexForType( type, indexedBaseType );
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
    
    private final void checkIndexedType( IndexedType indexedType, Class<? extends IndexedBaseType> indexedBaseType ) {
        if ( indexedType.indexedBaseType() != indexedBaseType ) {
            throw new IllegalArgumentException( "IndexedType missmatch: indexedBaseType=" + indexedBaseType + " indexType=" + indexedType.indexedBaseType() );
        }
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
