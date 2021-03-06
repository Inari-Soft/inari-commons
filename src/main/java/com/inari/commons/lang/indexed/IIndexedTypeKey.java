package com.inari.commons.lang.indexed;

import com.inari.commons.lang.aspect.Aspect;

/** Defines the key for an IndexedType with the index value for specified subType or indexed type.
 * 
 *  // TODO describe or refer to an example
 * 
 * @author andreashefti
 *
 */
public interface IIndexedTypeKey extends Aspect {
    
    /** The specified subType or indexed type */
    <T> Class<T> type();
    
    Class<?> baseType();

}
