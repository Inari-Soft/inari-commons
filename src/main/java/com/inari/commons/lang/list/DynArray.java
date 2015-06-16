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
package com.inari.commons.lang.list;

import java.util.ArrayList;
import java.util.Iterator;

public class DynArray<T> implements Iterable<T> {
    
    private ArrayList<T> list;

    public DynArray() {
        list = new ArrayList<T>();
    }
    
    public DynArray( int initialSize ) {
        list = new ArrayList<T>( initialSize + ( initialSize/ 2 ) );
        for ( int i = 0; i < initialSize; i++ ) {
            list.add( null );
        }
    }
    
    public final T set( int index, T value ) {
        ensureCapacity( index );
        T old = list.get( index );
        list.set( index, value );
        return old;
    }
    
    public final T get( int index ) {
        if ( index >= list.size() ) {
            return null;
        }
        
        return list.get( index );
    }
    
    public boolean contains( int index ) {
        return list.get( index ) != null;
    }
    
    public int indexOf( T value ) {
        return list.indexOf( value );
    }
    
    public final T remove( int index ) {
        if ( index >= list.size() ) {
            return null;
        }
        
        T result = list.get( index );
        list.set( index, null );
        return result;
    }
    
    public final int size() {
        int size = 0;
        for ( T t : list ) {
            if ( t != null ) {
                size++;
            }
        }
        return size;
    }
    
    public final int capacity() {
        return list.size();
    }

    private final void ensureCapacity( int index ) {
        int newSize = index + 1;
        while( list.size() < newSize ) {
            list.add( null );
        }
    }
    
    public void clear() {
        list.clear();
    }

    @Override
    public final Iterator<T> iterator() {
        return new DynArrayIterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "DynArray [list=" );
        builder.append( list );
        builder.append( ", size()=" );
        builder.append( size() );
        builder.append( ", capacity()=" );
        builder.append( capacity() );
        builder.append( "]" );
        return builder.toString();
    }
    

    private final class DynArrayIterator implements Iterator<T> {
        
        private int index = 0;
        
        private DynArrayIterator() {
            findNext();
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public T next() {
            T result = list.get( index );
            findNext();
            return result;
        }

        @Override
        public void remove() {
            list.set( index, null );
        }
        
        public void findNext() {
            while( index < list.size() && list.get( index ) == null ) {
                index++;
            }
        }
        
    }

}
