package com.inari.commons.lang.list;

public interface ReadOnlyDynArray<T> extends Iterable<T> {

    /** Indicates the grow number that defines the number of additional capacity that is added when a object is set with an
     *  index higher then the actual capacity.
     *
     *  @return the grow number
     */
    int getGrow();
    
    /** Returns <tt>true</tt> if this DynArray contains no elements.
     *
     * @return <tt>true</tt> if this DynArray contains no elements
     */
    boolean isEmpty();
    
    /** Get the size of the DynArray. The size is defined by the number of objects that
     *  the DynArray contains.
     *  NOTE: this is not the same like length of an array which also counts the null/empty values
     */
    int size();
    
    /** Get the capacity of the DynArray. This is the size of the internal ArrayList and is
     *  according to the length of an array.
     */
    int capacity();
    
    /** Get the object at specified index or null if there is no object referenced by specified index.
     *  If index is out of range ( 0 - capacity ) then an IndexOutOfBoundsException is thrown.
     *
     *  @param index the index
     *  @throws IndexOutOfBoundsException if index is out of bounds ( 0 - capacity )
     */
    T get( int index );
    
    /** Indicates if there is an object referenced by the specified id. If there is no object referenced
     *  by the index, false is returned also in the case, the index is out of bounds.
     *
     *  @param index the index
     *  @return true if there is an object referenced by the specified id
     */
    boolean contains( int index );
    
    /** Use this to get the index of a specified object in the DynArray. 
     *  This checks only gives back the index of the same object instance (no equals check)
     *
     *  @param value The object value to get the associated index
     */
    int indexOf( T value );
    
}
