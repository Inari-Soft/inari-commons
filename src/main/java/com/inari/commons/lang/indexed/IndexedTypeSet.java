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
    
    private Indexed[] indexed;
    protected final Class<? extends IndexedType> indexedType;
    protected final IndexedAspect aspect;
    protected int size = 0;
    
    
    public IndexedTypeSet( Class<? extends IndexedType> indexedType ) {
        this.indexedType = Indexer.findIndexedType( indexedType );
        aspect = new IndexedAspect( indexedType, Indexer.getIndexedTypeSize( this.indexedType ) );
        indexed = new Indexed[ Indexer.getIndexedTypeSize( indexedType ) ];
    }
    
    public IndexedTypeSet( Class<? extends IndexedType> indexedType, int length ) {
        this.indexedType = Indexer.findIndexedType( indexedType );
        aspect = new IndexedAspect( indexedType, length );
        indexed = new Indexed[ length ];
    }
    
    public final int size() {
        return size;
    }
    
    public final IndexedAspect getAspect() {
        return aspect;
    }
    
    public final Class<? extends IndexedType> getIndexedType() {
        return indexedType;
    }
    
    public final boolean include( IndexedAspect aspect ) {
        return this.aspect.include( aspect );
    }
    
    public final int length() {
        return indexed.length;
    }

    public final Indexed set( Indexed indexed ) {
        if ( indexed == null ) {
            return null;
        }
        Indexer.checkIndexedType( indexed, indexedType );
        ensureCapacity( indexed.index() );
        
        Indexed old = this.indexed[ indexed.index() ];
        this.indexed[ indexed.index() ] = indexed;
        IndexedAspectBuilder.set( aspect, indexed );
        if ( old == null ) {
            size++;
        }
        
        return old;
    }
    
    public final boolean contains( int index ) {
        if ( index < 0 || index >= indexed.length ) {
            return false;
        }
        return indexed[ index ] != null;
    }
    
    public final Indexed remove( Indexed indexed ) {
        Indexer.checkIndexedType( indexed, indexedType );
        return this.remove( indexed.index() );
    }
    
    public final <I extends Indexed> I remove( Class<I> indexType ) {
        Indexer.checkIndexedType( indexType, indexedType );
        return indexType.cast( remove( Indexer.getIndexForType( indexType, indexedType ) ) );
    }
    
    public final Indexed remove( int index ) {
        Indexed result = this.indexed[ index ];
        this.indexed[ index ] = null;
        AspectBuilder.reset( aspect, index );
        if ( result != null ) {
            size--;
        }
        return result;
    }
    
    public final <I extends Indexed> I get( Class<I> type ) {
        int typeIndex = Indexer.getIndexForType( type, indexedType );
        Indexed indexed = this.indexed[ typeIndex ];
        return type.cast( indexed );
    }
    
    @SuppressWarnings( "unchecked" )
    public final <I extends Indexed> I get( int typeIndex ) {
        return (I) this.indexed[ typeIndex ];
    }
    
    public <T extends Indexed> Iterable<T> getIterable() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new IndexedIterator<T>();
            }
        };
    }

    private void ensureCapacity( int index ) {
        if ( index <indexed.length ) {
            return;
        }
        
        Indexed[] newArray = new Indexed[ index + 1 ];
        System.arraycopy( indexed, 0, newArray, 0, indexed.length );
        indexed = newArray;
    }
    
    public final void clear() {
        if ( aspect != null ) {
            IndexedAspectBuilder.clear( aspect );
        }
        size = 0;
        if ( indexed != null ) {
            for ( int i = 0; i < indexed.length; i++ ) {
                if ( indexed[ i ] != null && indexed[ i ] instanceof Clearable ) {
                    ( (Clearable) indexed[ i ] ).clear();
                }
                indexed[ i ] = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "IndexedTypeSet [ indexedType=" ).append( indexedType.getSimpleName() );
        sb.append( " length=" ).append( indexed.length ).append( " size:" ).append( size );
        sb.append( ", indexed={" );
        boolean deleteLastSep = false;
        for ( int i = 0; i < indexed.length; i++ ) {
            deleteLastSep = true;
            sb.append( ( indexed[ i ] != null )? indexed[ i ].getClass().getSimpleName() : "null" ).append( "," );
        }
        if ( deleteLastSep ) {
            sb.deleteCharAt( sb.length() - 1 );
        }
        sb.append( "} ]" );
        return  sb.toString();
    }
    
    
    private final class IndexedIterator<T extends Indexed> implements Iterator<T> {
        
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
            return (T) indexed[ nextIndex ];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
