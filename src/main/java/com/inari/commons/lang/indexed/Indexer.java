/*******************************************************************************
 * Copyright (c) 2015 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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
package com.inari.commons.lang.indexed;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Indexer is used to indexing either IndexedObject or IndexedType.
 *
 *  The concept of IndexedObject is the possibility to indexing object instanced of a certain type.
 *  For example there is a class A and for every instance of the class A there should be an index,
 *  the class A can either extends the BaseIndexedObject to get all its benefits to get the indexed
 *  automatically or it can implements IndexedObject and implements the index creation by it self using
 *  the Indexer like BaseIndexedObject does.
 *
 *  The concept of IndexedType is to index all different sub-types of a certain IndexedBaseType so that
 *  the index of a IndexedType instance identifies the sub-type of IndexedBaseType by an index.
 *  For example if there is a class (type) A for that all sub-types has a specified index to identify,
 *  A has to implements IndexedBaseType and returns its owen type (A.class) for indexedBaseType.
 *  Normally A is an interface or abstract class.
 *  Then for every sub-type of A, for example B extends A and C extends A, the class has to implement
 *  IndexedType and returns its own type for indexedType (B.class and C.class). Within the Indexer
 *  one is able to generate and use an index for the specified type B and C and is able to identify the
 *  type by index.
 *
 *  For more example see also the Test cases of Indexer.
 */
public abstract class Indexer {
    
    private final static List<Class<? extends IndexedBaseType>> indexedBaseTypes = new ArrayList<Class<? extends IndexedBaseType>>();
    private final static List<List<Class<? extends IndexedType>>> indexedTypeMap = Collections.synchronizedList( new ArrayList<List<Class<? extends IndexedType>>>() );
    
    private final static Map<Class<? extends IndexedObject>, BitSet> indexedObjectTypes = new HashMap<Class<? extends IndexedObject>, BitSet>();
    
    
    // **** OBJECT INDEX *********************************************************

    /** Within nextObjectIndex one can get the next index for a specified type of IndexedObject.
     * @param indexedObjectType The specified type of IndexedObject
     * @return the next index for a specified type of IndexedObject.
     */
    public final static int nextObjectIndex( Class<? extends IndexedObject> indexedObjectType ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObjectType );
        if ( indexSet == null ) {
            indexSet = new BitSet();
            indexedObjectTypes.put( indexedObjectType, indexSet );
        }
        return findFreeObjectIndex( indexSet );
    }

    /** The the number of indices that are used for a specified IndexedObject type.
     * @param indexedObjectType The specific type of IndexedObject
     * @return the number of indices that are used for a specified IndexedObject type.
     */
    public final static int getIndexedObjectSize( Class<? extends IndexedObject> indexedObjectType ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObjectType );
        if ( indexSet == null ) {
            return 0;
        }
        int lastIndex = 0;
        for ( int i = indexSet.nextSetBit( 0 ); i >= 0; i = indexSet.nextSetBit( i+1 ) ) {
            lastIndex = i;
        }
        return lastIndex + 1;
    }

    /** This is used by BaseIndexedObject to release a specified index when the BaseIndexedObject instance is
     *  disposed and not used anymore.
     *  @param indexedObject the IndexedObject instance to dispose
     */
    final static void disposeIndexedObject( IndexedObject indexedObject ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObject.indexedObjectType() );
        if ( indexSet != null ) {
            indexSet.clear( indexedObject.index() );
        }
    }

    /** This is used by BaseIndexedObject on new instance that has already a index assigned to
     *  register this index to the Indexer.
     *  @param indexedObject the IndexedObject instance form that the index is get to register
     *  @throws IllegalArgumentException if the index from specified indexedObject is already used by another living IndexedObject instance
     */
    final static void setIndexedObjectIndex( IndexedObject indexedObject ) {
        Class<? extends IndexedObject> indexedObjectType = indexedObject.indexedObjectType();
        int index = indexedObject.index();
        registerIndex( indexedObjectType, index );
    }

    public final static void registerIndex( Class<? extends IndexedObject> indexedObjectType, int index ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObjectType );
        if ( indexSet == null ) {
            indexSet = new BitSet();
            indexedObjectTypes.put( indexedObjectType, indexSet );
        }
        
        if ( indexSet.get( index ) ) {
            throw new IllegalArgumentException( "The Object index: " + index + " is already used by another Object!" );
        }
        
        indexSet.set( index );
    }

    /** Finds the next free index on the specified BitSet.
     *  This is used to reuse once used but already disposed indexes
     * @param indexSet the BitSet of a specified indexedObject type
     * @return the next free index.
     */
    private static int findFreeObjectIndex( BitSet indexSet ) {
        int nextClearBit = indexSet.nextClearBit( 0 );
        indexSet.set( nextClearBit );
        return nextClearBit;
    }
    
    
    // **** INDEXED TYPE *********************************************************

    /** Gets the index for a certain IndexedType instance. If there is no index yet for this IndexedType
     *  automatically creates one.
     * @param indexedType the IndexedType instance to get the index for
     * @return the index for a certain IndexedType instance
     */
    public final static int getIndexForType( IndexedType indexedType ) {
        return getIndexForType( indexedType.indexedType(), indexedType.indexedBaseType() );
    }

    /** Gets the index for a certain IndexedType class belonging to a certain IndexedBaseType class.
     *  If there is no index yet automatically creates one.
     *
     * @param indexedType The IndexedType class
     * @param indexedBaseType The IndexedBaseType class
     * @return the index for specified IndexedType class and IndexedBaseType class
     * @throws IllegalArgumentException if the specified indexedType do not belong to the specified indexedBaseType
     */
    public final static int getIndexForType( Class<? extends IndexedType> indexedType, Class<? extends IndexedBaseType> indexedBaseType ) {
        checkIndexedType( indexedType, indexedBaseType );
        List<Class<? extends IndexedType>> indexedOfType = getCreateIndexedOfType( indexedBaseType, indexedType );
        return indexedOfType.indexOf( indexedType );
    }

    /** Gets the index for a certain IndexedType by search for the belonging IndexedBaseType class using reflection.
     *  If there is no index yet automatically creates one.
     *
     *  NOTE: because this is using reflection to search for the belonging IndexedBaseType it is not that fast
     *        as getIndexForType( indexedType, indexedBaseType ).
     *
     * @param indexedType The IndexedType class
     * @return the index for specified IndexedType class
     */
    public final static int getIndexForType( Class<? extends IndexedType> indexedType ) {
        Class<? extends IndexedBaseType> indexedBaseType = findIndexedType( indexedType );
        return getIndexForType( indexedType, indexedBaseType );
    }

    /** Gets the class type of an registered IndexedType for specified index and IndexedBaseType
     *  or null if there is no type registered for that index.
     * @param indexedBaseType the IndexedBaseType class
     * @param index the index
     * @return the class type of an registered IndexedType for specified index and IndexedBaseType.
     */
    @SuppressWarnings( "unchecked" )
    public final static <T extends IndexedType> Class<T> getTypeForIndex( Class<? extends IndexedBaseType> indexedBaseType, int index ) {
        List<Class<? extends IndexedType>> indexedBaseTypeList = indexedTypeList( indexedBaseType );
        if ( indexedBaseTypeList != null ) {
            if ( index < 0 || index >= indexedBaseTypeList.size() ) {
                return null;
            }
            return (Class<T>) indexedBaseTypeList.get( index );
        }
        return null;
    }

    /** Gets the class type of an registered IndexedType for specified index and IndexedBaseType
     *  or null if there is no type registered for that index.
     * @param indexedBaseType the IndexedBaseType class
     * @param index the index
     * @param <T> the expected type of IndexedType
     * @return the class type of an registered IndexedType for specified index and IndexedBaseType.
     */
    @SuppressWarnings( "unchecked" )
    public final static <T extends IndexedType> Class<T> getCastedTypeForIndex( Class<? extends IndexedBaseType> indexedBaseType, int index ) {
        return (Class<T>) getTypeForIndex( indexedBaseType, index );
    }

    /** Get the number of indices registered for a specified IndexedBaseType.
     * @param indexedBaseType IndexedBaseType class to get the number of registered indices for
     * @return the number of indices registered for a specified IndexedBaseType.
     */
    public final static int getIndexedTypeSize( Class<? extends IndexedBaseType> indexedBaseType ) {
        indexedBaseType = findIndexedType( indexedBaseType );
        List<Class<? extends IndexedType>> indexedOfType = getCreateIndexedTypeList( indexedBaseType );
        return indexedOfType.size();
    }

    /** Get the number of all registered IndexedBaseType.
     * @return the number of all registered IndexedBaseType.
     */
    public int getNumberOfRegisteredBaseTypes() {
        return indexedBaseTypes.size();
    }

    /** Clears all registered indexes.
     *  NOTE: use this with care. normally it is useful in tests to reset the Indexer.
     */
    public final static void clear() {
        indexedBaseTypes.clear();
        indexedTypeMap.clear();
        indexedObjectTypes.clear();
    }


    /** Checks first if the specified indexedType is an instantiable type because only instantiable types make
     *  sense to be a indexedType.
     *  Second checks if the specified indexedType belongs to the specified indexedBaseType
     * @param indexedType the IndexedType class
     * @param indexedBaseType the IndexedBaseType class
     */
    public static final void checkIndexedType( Class<? extends IndexedType> indexedType, Class<? extends IndexedBaseType> indexedBaseType ) {
        if ( indexedType.isInterface() || Modifier.isAbstract( indexedType.getModifiers() ) ) {
            throw new IllegalArgumentException( "indexedType: " + indexedType + " is not an instantiable class" );
        }
        if ( !indexedBaseType.isAssignableFrom( indexedType ) ) {
            throw new IllegalArgumentException( String.format( "IndexedType missmatch: indexedType:%s is not a valid substitute of indexedBaseType:%s", indexedType, indexedBaseType ) );
        }
    }
    
    @SuppressWarnings( "unchecked" )
    final static Class<? extends IndexedBaseType> findIndexedType( Class<? extends IndexedBaseType> indexedBaseType ) {
        while ( ( indexedBaseType.getSuperclass() != null ) && IndexedBaseType.class.isAssignableFrom( indexedBaseType.getSuperclass() ) ) {
            indexedBaseType = (Class<? extends IndexedBaseType>) indexedBaseType.getSuperclass();
        }
        if ( indexedBaseType.isInterface() ) {
            return indexedBaseType;
        }
        Class<?>[] interfaces = indexedBaseType.getInterfaces();
        if ( interfaces != null ) {
            for ( Class<?> _interface : interfaces ) {
                if ( _interface != IndexedType.class && _interface != IndexedBaseType.class && IndexedBaseType.class.isAssignableFrom( _interface ) ) {
                    indexedBaseType = (Class<? extends IndexedBaseType>) _interface;
                }
            }
        }
        return indexedBaseType;
    }
    
    private final static List<Class<? extends IndexedType>> indexedTypeList( Class<? extends IndexedBaseType> indexedBaseType ) {
        if ( !indexedBaseTypes.contains( indexedBaseType ) ) {
            return null;
        }
        return indexedTypeMap.get( indexedBaseTypes.indexOf( indexedBaseType ) );
    }
    
    private final static List<Class<? extends IndexedType>> getCreateIndexedTypeList( Class<? extends IndexedBaseType> indexedBaseType ) {
        if ( !indexedBaseTypes.contains( indexedBaseType ) ) {
            indexedBaseTypes.add( indexedBaseType );
            List<Class<? extends IndexedType>> indexedOfType = Collections.synchronizedList( new ArrayList<Class<? extends IndexedType>>() );
            indexedTypeMap.add( indexedOfType );
        }
        
        return indexedTypeList( indexedBaseType );
    }

    private final static List<Class<? extends IndexedType>> getCreateIndexedOfType( Class<? extends IndexedBaseType> indexedBaseType, Class<? extends IndexedType> indexedType ) {
        List<Class<? extends IndexedType>> indexedOfType = getCreateIndexedTypeList( indexedBaseType );
        if ( !indexedOfType.contains( indexedType ) ) {
            indexedOfType.add( indexedType );
        }
        return indexedOfType;
    }

    /** Creates a dump String of Indexed within all registrations
     * @return a dump String of Indexed within all registrations
     */
    public final static String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append( "IndexProvider : {\n" );
        int indexedIndex = 0;
        builder.append( " * Indexed Objects :" );
        for ( Map.Entry<Class<? extends IndexedObject>, BitSet> indexedObjectType : indexedObjectTypes.entrySet() ) {
            builder.append( "\n  " ).append( indexedObjectType.getKey() ).append( " : " ).append( indexedObjectType.getValue() );
        }
        builder.append( "\n * Indexed Types :\n" );
        for ( Class<? extends IndexedBaseType> indexedBaseType : indexedBaseTypes ) {
            List<Class<? extends IndexedType>> value = indexedTypeMap.get( indexedIndex );
            builder.append( "  " ).append( indexedBaseType ).append( " : {\n" );
            int index = 0;
            for ( Class<? extends IndexedType> indexed : value ) {
                builder.append( "    " ).append( index ).append( ":" ).append( indexed.getName() ).append( "\n" );
                index++;
            }
            builder.append( "  }\n" );
            indexedIndex++;
        }
        builder.append( "}" );
        return builder.toString();
    }

}
