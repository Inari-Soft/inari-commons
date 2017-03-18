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
package com.inari.commons.lang.list;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;

import com.inari.commons.lang.Method;

/** An Array that dynamically grows if more space is needed.
 * 
 *  Since the creation of a typed Array need the class of the type to properly create the array and since this implementation wants to 
 *  avoid a cast on every get, a DynArray has do be instantiated within defined static create methods.
 *  <P>
 *  DynArray<String> dynArray = DynArray.create( String.class )
 *  <P
 *  For example will create a DynArray<String> with a initial capacity of 50 and a grow factor of 20
 *  <P>
 *  If a DynArray has to be created for a type that itself is typed, use the createType method..
 *  <P>
 *  DynArray<List<String> dynArray = DynArray.createTyped( List.class )
 *
 * @param <T> The type of objects in the DynArray
 */
public final class DynArray<T> implements Iterable<T> {
    
    private final Class<T> typeClass;
    private T[] array;
    private final int grow;
    private int size = 0;

    /** Creates a DynArray with specified initial capacity. The internal ArrayList is initialized with
     *  initialCapacity + ( initialCapacity / 2 ) and filled up with null values for the indexes to initialCapacity.
     *  @param initialCapacity
     */
    private DynArray( Class<T> typeClass, int initialCapacity, int grow ) {
        this.typeClass = typeClass;
        createList( initialCapacity );
        this.grow = grow;
    }
   
    /** Returns the type Class of T for this DynArray */
    public final Class<T> getTypeClass() {
        return typeClass;
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
        T old = array[ index ];
        array[ index ] = value;
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
        while( index < array.length && array[ index ] != null ) {
            index++;
        }
        set( index, value );
        return index;
    }
    
    /** Use this to add all values of a DynArray to the instance of DynArray.
     * 
     * @param values the other DynArray to get the values form
     */
    public final void addAll( DynArray<T> values ) {
        ensureCapacity( this.size + values.size );
        for ( T value : values ) {
            add( value );
        }
    }

    /** Get the object at specified index or null if there is no object referenced by specified index.
     *  If index is out of range ( 0 - capacity ) then an IndexOutOfBoundsException is thrown.
     *
     *  @param index the index
     *  @throws IndexOutOfBoundsException if index is out of bounds ( 0 - capacity )
     */
    public final T get( int index ) {
        return array[ index ];
    }

    /** Indicates if there is an object referenced by the specified id. If there is no object referenced
     *  by the index, false is returned also in the case, the index is out of bounds.
     *
     *  @param index the index
     *  @return true if there is an object referenced by the specified id
     */
    public final boolean contains( int index ) {
        if ( index < 0 || index >= array.length ) {
            return false;
        }
        return array[ index ] != null;
    }

    /** Use this to get the index of a specified object in the DynArray. This inernally uses
     *  ArrayList.indexOf and has the same performance.
     *
     *  @param value The object value to get the associated index
     */
    public final int indexOf( T value ) {
        if ( value == null ) {
            return -1;
        }
        for ( int i = 0; i < array.length; i++ ) {
            if ( value.equals( array[ i ] ) ) {
                return i;
            }
        }
        return -1;
    }

    /** Removes the object on specified index of DynArray and returns the objects that was set before.
     *
     *  @param index the index
     *  @return The objects that was set before or null of there was none
     *  @throws IndexOutOfBoundsException if index is out of bounds ( 0 - capacity )
     */
    public final T remove( int index ) {
        if ( index < 0 || index >= array.length ) {
            return null;
        }
        T result = array[ index ];
        array[ index ] = null;
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
        int indexOf = indexOf( value );
        if ( indexOf >= 0 ) {
            remove( indexOf );
        }
        return indexOf;
    }

    /** Sorts the list within the given comparator.
     *  @param comparator
     */
    public final void sort( Comparator<T> comparator ) {
        Arrays.sort( array, comparator );
    }
    
    /** Returns <tt>true</tt> if this DynArray contains no elements.
     *
     * @return <tt>true</tt> if this DynArray contains no elements
     */
    public final boolean isEmpty() {
        return size <= 0;
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
        return array.length;
    }

    /** Clears the whole list, removes all objects and sets the capacity to 0.
     */
    public final void clear() {
        for ( int i = 0; i < array.length; i++ ) {
            array[ i ] = null;
        }
        size = 0;
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
    
    @SuppressWarnings( "unchecked" )
    public final void trim() {
        if ( size == capacity() ) {
            return;
        }
        
        T[] oldArray = array;
        array = (T[]) Array.newInstance( typeClass, size );
        int index = 0;
        for ( int i = 0; i < oldArray.length; i++ ) {
            if ( oldArray[ i ] != null ) {
                array[ index ] = oldArray[ i ];
                index++;
            }
        }
    }
    
    public final void forEach( Method<T> m ) {
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] != null ) {
                m.call( array[ i ] );
            }
        }
    }
    
    public final void forEach( Function<T, Void> f ) {
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] != null ) {
                f.apply( array[ i ] );
            }
        }
    }

    public final T[] getArray() {
        @SuppressWarnings( "unchecked" )
        T[] result = (T[]) Array.newInstance( typeClass, size );
        int index = 0;
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] != null ) {
                result[ index ] = array[ i ];
                index++;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "DynArray [list=" );
        builder.append( Arrays.toString( array ) );
        builder.append( ", size()=" );
        builder.append( size() );
        builder.append( ", capacity()=" );
        builder.append( capacity() );
        builder.append( "]" );
        return builder.toString();
    }

    @SuppressWarnings( "unchecked" )
    public final void ensureCapacity( int index ) {
        if ( index < array.length ) {
            return;
        }
        int size = array.length;
        int newSize = size;
        while ( index >= newSize ) {
            newSize += grow;
        }
        T[] oldArray = array;
        array = (T[]) Array.newInstance( typeClass, newSize );
        System.arraycopy( oldArray, 0, array, 0, oldArray.length );
    }

    @SuppressWarnings( "unchecked" )
    private final void createList( int initialCapacity ) {
        array = (T[]) Array.newInstance( typeClass, initialCapacity );
    }
    
    private final class DynArrayIterator implements Iterator<T> {
        
        private int index = 0;
        
        private DynArrayIterator() {
            findNext();
        }

        @Override
        public final boolean hasNext() {
            return index < array.length;
        }

        @Override
        public final T next() {
            T result = array[ index ];
            index++;
            findNext();
            return result;
        }

        @Override
        public final void remove() {
            array[ index ] = null;
        }
 
        private final void findNext() {
            while( index < array.length && array[ index ] == null ) {
                index++;
            }
        }
    }

    
    public final static <T> DynArray<T> create( Class<T> type ) {
        return new DynArray<T>( type, 50, 20 );
    }
    
    public final static <T> DynArray<T> create( Class<T> type, int initialCapacity ) {
        return new DynArray<T>( type, initialCapacity, 20 );
    }
    
    public final static <T> DynArray<T> create( Class<T> type, int initialCapacity, int grow ) {
        return new DynArray<T>( type, initialCapacity, grow );
    }
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public final static <T> T createTyped( Class<?> type ) {
        return (T) new DynArray( type, 50, 20 );
    }
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public final static <T> T createTyped( Class<?> type, int initialCapacity ) {
        return (T) new DynArray( type, initialCapacity, 20 );
    }
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public final static <T> T createTyped( Class<?> type, int initialCapacity, int grow ) {
        return (T) new DynArray( type, initialCapacity, grow );
    }

}
