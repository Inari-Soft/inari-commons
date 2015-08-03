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
package com.inari.commons.lang.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/** The advantages of ArrayList and an Array in one.
 *  Use DynArray if a good performance matters and just a simple indexed storage is needed to set and get objects from.
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
 *  DynArray uses an Iterator implementation using index without concurrent modification checks and should be as fast as 
 *  an array iteration. But DynArray is not synchronized and do not check concurrent modifications in any case.
 *  
 *  If the index to set is higher then the current capacity of the internal ArrayList the internal ArrayList is 
 *  been automatically growing like a normal ArrayList and filled with null values to fit the new capacity.
 *  The capacity of the list (and the internal ArrayList) never shrink while just remove objects. Only the clear 
 *  functions removes all objects and also clears the internal ArrayList.
 *
 * @param <T> The type of objects in the DynArray
 */
public final class DynArray<T> implements Iterable<T> {
    
    private ArrayList<T> list;
    private final int grow;
    private int size = 0;
    
    /** Creates a DynArray with no initial capacity */
    public DynArray() {
        list = new ArrayList<T>();
        grow = 1;
    }
    
    /** Creates a DynArray with specified initial capacity. The internal ArrayList is initialized with 
     *  initialCapacity + ( initialCapacity / 2 ) and filled up with null values for the indexes to initialCapacity.
     *  @param initialCapacity
     */
    public DynArray( int initialCapacity ) {
        createList( initialCapacity );
        grow = 1;
    }

    /** Creates a DynArray with specified initial capacity. The internal ArrayList is initialized with 
     *  initialCapacity + ( initialCapacity / 2 ) and filled up with null values for the indexes to initialCapacity.
     *  @param initialCapacity
     */
    public DynArray( int initialCapacity, int grow ) {
        createList( initialCapacity );
        this.grow = grow;
    }
    
    
    /** Indicates the grow number that defines the number of additional capacity that is added when a object is set with an
     *  index higher then the actual capacity.
     *  
     *  @return the grow number
     */
    public final int getGrow() {
        return grow;
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
    
    /** Add the specified value at the first empty (null) position that is found in the DynArray.
     *  If there is no empty position the list is growing by the grow value and the value is added
     *  to at the end of old list.
     *
     *  @param value The value to add to the DynArray
     *  @return the index of the newly added value
     */
    public final int add( T value ) {
        int index = 0;
        while( index < list.size() && list.get( index ) != null ) {
            index++;
        }
        set( index, value );
        return index;
    }
    
    /** Get the object at specified index or null if there is no object referenced by specified index.
     *  If index is out of range ( 0 - capacity ) then an IndexOutOfBoundsException is thrown.
     * 
     *  @param index the index
     *  @throws IndexOutOfBoundsException if index is out of bounds ( 0 - capacity )
     */
    public final T get( int index ) {
        return list.get( index );
    }
    
    /** Indicates if there is an object referenced by the specified id. If there is no object referenced
     *  by the index, false is returned also in the case, the index is out of bounds.
     * 
     *  @param index the index
     *  @return true if there is an object referenced by the specified id
     */
    public final boolean contains( int index ) {
        if ( index < 0 || index >= list.size() ) {
            return false;
        }
        return list.get( index ) != null;
    }
    
    /** Use this to get the index of a specified object in the DynArray. This inernally uses
     *  ArrayList.indexOf and has the same performance.
     * 
     *  @param value The object value to get the associated index 
     */
    public final int indexOf( T value ) {
        return list.indexOf( value );
    }
    
    /** Removes the object on specified index of DynArray and returns the objects that was set before.
     * 
     *  @param index the index
     *  @return The objects that was set before or null of there was none
     *  @throws IndexOutOfBoundsException if index is out of bounds ( 0 - capacity )
     */
    public final T remove( int index ) {
        T result = list.get( index );
        list.set( index, null );
        size--;
        return result;
    }
    
    /** Removes the specified object value from DynArray and returns the index where it as removed.
     *  Also this remove sets a null value on specified index of internal ArrayList instead of removing it
     *  to avoid the index shift of an ArrayList remove.
     *  
     *  @param value The value to remove.
     *  @return the index of the value that was removed or -1 if there was no such value.
     */
    public final int remove( T value ) {
        int indexOf = list.indexOf( value );
        if ( indexOf >= 0 ) {
            remove( indexOf );
        }
        return indexOf;
    }
    
    /** Sorts the list within the given comparator.
     *  @param coparator
     */
    public final void sort( Comparator<T> coparator ) {
        Collections.sort( list, coparator );
    }
    
    /** Get the size of the DynArray. The size is defined by the number of objects that
     *  the DynArray contains. 
     *  NOTE: this is not the same like length of an array which also counts the null/empty values
     */
    public final int size() {
        return size;
    }
    
    /** Get the capacity of the DynArray. This is the size of the internal ArrayList and is
     *  according to the length of an array.
     */
    public final int capacity() {
        return list.size();
    }

    /** Clears the whole list, removes all objects and sets the capacity to 0.
     */
    public final void clear() {
        list.clear();
        size = 0;
    }
    
    /** Gets the iterator from internal list. NOTE: this also gets the null values in the internal
     *  list but is as fast as a normal ArrayList iterator.
     *  
     *  @return the iterator from internal list
     */
    public final Iterator<T> listIterator() {
        return list.iterator();
    }

    /** Gets an Iterator of specified type to iterate over all objects in the DynArray
     *  by skipping the empty/null values. This does not concurrent modification checks at all
     *  but is not that fast like an array iteration or by using the listIterator but skips
     *  the null values.
     *  
     *  @return an Iterator of specified type to iterate over all objects in the DynArray by skipping the empty/null values
     */
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
    
    private final void ensureCapacity( int index ) {
        int size = list.size();
        int newSize = size;
        while ( index >= newSize ) {
            newSize += grow;
        }
        while( size < newSize ) {
            list.add( null );
            size++;
        }
    }
    
    private final void createList( int initialCapacity ) {
        list = new ArrayList<T>( initialCapacity + ( initialCapacity / 2 ) );
        for ( int i = 0; i < initialCapacity; i++ ) {
            list.add( null );
        }
    }

    // TODO optimization: get rid of list.get in next or findNext.
    private final class DynArrayIterator implements Iterator<T> {
        
        private int index = 0;
        private final int size = list.size();
        
        private DynArrayIterator() {
            findNext();
        }

        @Override
        public final boolean hasNext() {
            return index < list.size();
        }

        @Override
        public final T next() {
            T result = list.get( index );
            index++;
            findNext();
            return result;
        }

        @Override
        public final void remove() {
            list.set( index, null );
        }
        
        private final void findNext() {
            while( index < size && list.get( index ) == null ) {
                index++;
            }
        }
    }

}
