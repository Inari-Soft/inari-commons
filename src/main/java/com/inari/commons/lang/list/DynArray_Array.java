package com.inari.commons.lang.list;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class DynArray_Array<T> implements Iterable<T> {

    private final Class<T> typeClass;
    private T[] array;
    private final int grow;
    private int size = 0;

    /** Creates a DynArray with no initial capacity */
    public DynArray_Array( Class<T> typeClass ) {
        this.typeClass = typeClass;
        createList( 50 );
        grow = 1;
    }

    /** Creates a DynArray with specified initial capacity. The internal ArrayList is initialized with
     *  initialCapacity + ( initialCapacity / 2 ) and filled up with null values for the indexes to initialCapacity.
     *  @param initialCapacity
     */
    public DynArray_Array( Class<T> typeClass, int initialCapacity ) {
        this.typeClass = typeClass;
        createList( initialCapacity );
        grow = 20;
    }

    /** Creates a DynArray with specified initial capacity. The internal ArrayList is initialized with
     *  initialCapacity + ( initialCapacity / 2 ) and filled up with null values for the indexes to initialCapacity.
     *  @param initialCapacity
     */
    public DynArray_Array(Class<T> typeClass, int initialCapacity, int grow ) {
        this.typeClass = typeClass;
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
    
    public final T[] getArray() {
        return array;
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
    private final void ensureCapacity( int index ) {
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

        @Override
        public final boolean hasNext() {
            return index < array.length;
        }

        @Override
        public final T next() {
            return array[ index++ ];
        }

        @Override
        public final void remove() {
            array[ index ] = null;
        }
    }
}
