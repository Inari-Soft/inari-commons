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
*/
package com.inari.commons.lang.list;

import java.util.ArrayList;
import java.util.Iterator;

/** The advantages of ArrayList and an Array in one.
 *  Use DynArray if a good performance matters and just a simple indexed storage is used to set and get objects from.
 *  
 *  Internally this is implemented within an ArrayList but with fixed positions (indices) of stored objects like 
 *  an Array. An ArrayList has good performance on get and add calls but not an add( index ) and remove( index ) 
 *  calls because of the index shift that is needed and ArrayList has no good performance on iteration because 
 *  there are a couple of concurrent modification checks done while ArrayList iteration.
 *  
 *  DynArray has no add methods but set( index ) method like an array and if there is already an object referenced 
 *  by this index, the referenced is overwritten with the new object reference like in an array. 
 *  The remove( index ) method use first a get of the already referenced object and then set a null value at the 
 *  specified position to avoid index shifting.
 *  
 *  DynArray uses an Iterator implementation using index without concurrent modification checks and is as fast as 
 *  an array iteration. But DynArray is not synchronized and do not check concurrent modifications in any case.
 *  
 *  If the index to set is higher as the current capacity of the internal ArrayList the internal ArrayList is 
 *  been automatically growing like a normal ArrayList and filled with null values to fit the new capacity.
 *  The capacity of the list (and the internal ArrayList) never shrink while just remove objects. Only the clear 
 *  functions removes all objects and also clears the internal ArrayList.
 *
 * @param <T> The type of objects in the DynArray
 */
public final class DynArray<T> implements Iterable<T> {
    
    private ArrayList<T> list;
    private int size = 0;
    
    /** Creates a DynArray with no intial capacity */
    public DynArray() {
        list = new ArrayList<T>();
    }
    
    /** Creates a DynArray with specified initial capacity. The internal ArrayList is initialized with 
     *  initialCapacity + ( initialCapacity / 2 ) and filled up with null values for the indexes to initialCapacity.
     *  @param initialCapacity
     */
    public DynArray( int initialCapacity ) {
        list = new ArrayList<T>( initialCapacity + ( initialCapacity / 2 ) );
        for ( int i = 0; i < initialCapacity; i++ ) {
            list.add( null );
        }
    }
    
    /** Sets the value at the specified position of the DynArray and returns the value that was at the position before 
     *  or null of the position was empty. If the index is out of the range of the current capacity of DynArray, the 
     *  internal ArrayList will be resized to fit the new index. This is the only case where this method will take 
     *  some more performance.
     *  
     * @param index the index to set the value
     * @param value the value to set
     * @return the value that was at the position before or null of the position was empty
     */
    public final T set( int index, T value ) {
        ensureCapacity( index );
        T old = list.get( index );
        list.set( index, value );
        if ( value != null ) {
            size++;
        }
        return old;
    }
    
    public final T get( int index ) {
        return list.get( index );
    }
    
    public final boolean contains( int index ) {
        if ( index < 0 || index >= list.size() ) {
            return false;
        }
        return list.get( index ) != null;
    }
    
    public final int indexOf( T value ) {
        return list.indexOf( value );
    }
    
    public final T remove( int index ) {
        if ( index >= list.size() ) {
            return null;
        }
        
        T result = list.get( index );
        list.set( index, null );
        size--;
        return result;
    }
    
    public final int size() {
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
    
    public final void clear() {
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
