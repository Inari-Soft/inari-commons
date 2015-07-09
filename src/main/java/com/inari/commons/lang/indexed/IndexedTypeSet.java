/**
* Copyright (c) 2015, Andreas Hefti, anhefti@yahoo.de 
* All rights reserved.
*
* This software is licensed to you under the Apache License, Version 2.0
* (the "License"); You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* . Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* . Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* . Neither the name "InariUtils" nor the names of its contributors may be
* used to endorse or promote products derived from this software without
* specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package com.inari.commons.lang.indexed;

import java.util.Iterator;

import com.inari.commons.lang.Clearable;
import com.inari.commons.lang.aspect.AspectBuilder;

public final class IndexedTypeSet {
    
    private IndexedType[] indexedType;
    protected final Class<? extends IndexedBaseType> indexedBaseType;
    protected final IndexedAspect aspect;
    protected int size = 0;
    
    
    public IndexedTypeSet( Class<? extends IndexedBaseType> indexedBaseType ) {
        this.indexedBaseType = Indexer.findIndexedType( indexedBaseType );
        int size = Indexer.getIndexedTypeSize( this.indexedBaseType );
        aspect = new IndexedAspect( indexedBaseType, size );
        this.indexedType = new IndexedType[ size ];
    }
    
    public IndexedTypeSet( Class<? extends IndexedBaseType> indexedBaseType, int length ) {
        this.indexedBaseType = Indexer.findIndexedType( indexedBaseType );
        aspect = new IndexedAspect( indexedBaseType, length );
        this.indexedType = new IndexedType[ length ];
    }
    
    public final int size() {
        return size;
    }
    
    public final IndexedAspect getAspect() {
        return aspect;
    }
    
    public final Class<? extends IndexedBaseType> getIndexedBaseType() {
        return indexedBaseType;
    }
    
    public final boolean include( IndexedAspect aspect ) {
        return this.aspect.include( aspect );
    }
    
    public final int length() {
        return indexedType.length;
    }

    public final IndexedType set( IndexedType indexedType ) {
        if ( indexedType == null ) {
            return null;
        }
        Indexer.checkIndexedType( indexedType, indexedBaseType );
        ensureCapacity( indexedType.index() );
        
        IndexedType old = this.indexedType[ indexedType.index() ];
        this.indexedType[ indexedType.index() ] = indexedType;
        IndexedAspectBuilder.set( aspect, indexedType );
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
        Indexer.checkIndexedType( indexedType, indexedBaseType );
        return this.remove( indexedType.index() );
    }
    
    public final <I extends IndexedType> I remove( Class<I> indexType ) {
        Indexer.checkIndexedType( indexType, indexedBaseType );
        return indexType.cast( remove( Indexer.getIndexForType( indexType, indexedBaseType ) ) );
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
            IndexedAspectBuilder.clear( aspect );
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
