package com.inari.commons.lang.indexed;

/** Defines the key for an IndexedType with the index value for specified subType or indexed type.
 * 
 *  // TODO describe or refer to an example
 * 
 * @author andreashefti
 *
 */
public interface IIndexedTypeKey {
    
    /** The specified subType or indexed type */
    <T> Class<T> type();
    
    Class<?> baseType();
    
    /** The index value for the specified subType or indexed type of this key */
    int typeIndex();

}
