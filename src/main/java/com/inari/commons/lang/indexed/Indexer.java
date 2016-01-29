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
package com.inari.commons.lang.indexed;

import java.lang.reflect.Constructor;
import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
    
    private final static Set<IndexedTypeKey> indexedTypeKeys = new LinkedHashSet<IndexedTypeKey>();
    private final static Map<Class<? extends IndexedObject>, BitSet> indexedObjectTypes = new LinkedHashMap<Class<? extends IndexedObject>, BitSet>();
    
    
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
    
    public final static void disposeObjectIndex( Class<? extends IndexedObject> indexedObjectType, int index ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObjectType );
        if ( indexSet != null ) {
            indexSet.clear( index );
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
            throw new IllegalArgumentException( "The Object: " + indexedObjectType.getName() + " index: " + index + " is already used by another Object!" );
        }
        
        indexSet.set( index );
    }
    
    public final static <K extends IndexedTypeKey> K getIndexedTypeKey( Class<K> baseType, Class<? extends IndexedType> indexedType ) {
        for ( IndexedTypeKey indexedTypeKey : indexedTypeKeys ) {
            if ( indexedType == indexedTypeKey.indexedType && baseType == indexedTypeKey.indexedObjectType() ) {
                if ( indexedTypeKey.index < 0 ) {
                    indexedTypeKey.index();
                }
                return baseType.cast( indexedTypeKey );
            }
        }
        
        K key = createIndexedTypeKeyInstance( baseType, indexedType );
        Class<?> baseIndexedType = key.baseIndexedType();
        if ( baseIndexedType != null ) {
            if ( !baseIndexedType.isAssignableFrom( indexedType ) ) {
                throw new IllegalArgumentException( "IndexedType missmatch: indexedType: " + indexedType + " is not a valid substitute of indexedBaseType: " + baseIndexedType );
            }
        }

        indexedTypeKeys.add( key );
        return key;
        
    }
    
    final static void removeIndexedTypeKey( IndexedTypeKey key ) {
        indexedTypeKeys.remove( key );
    }

    public static final <K extends IndexedTypeKey> K getIndexedTypeKeyForIndex( Class<K> baseType, int index ) {
        for ( IndexedTypeKey indexedTypeKey : indexedTypeKeys ) {
            if ( baseType == indexedTypeKey.indexedObjectType() && index == indexedTypeKey.index ) {
                return baseType.cast( indexedTypeKey );
            }
        }
        
        return null;
    }
    
    public static final void clear() {
        indexedObjectTypes.clear();
        for ( IndexedTypeKey indexedTypeKey : indexedTypeKeys ) {
            indexedTypeKey.index = -1;
        }
    }
    
    private static <K extends IndexedTypeKey> K createIndexedTypeKeyInstance( Class<K> baseType, Class<? extends IndexedType> indexedType ) {
        try {
            Constructor<K> declaredConstructor = baseType.getDeclaredConstructor( Class.class );
            boolean accessible = declaredConstructor.isAccessible();
            declaredConstructor.setAccessible( true );
            K result = declaredConstructor.newInstance( indexedType );
            declaredConstructor.setAccessible( accessible );
            return result;
        } catch ( Exception e ) {
            throw new IllegalArgumentException( "Faild to create instance form " + baseType.getName() + ". Root cause: ", e );
        }
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
    
    /** Creates a dump String of Indexed within all registrations
     * @return a dump String of Indexed within all registrations
     */
    public final static String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append( "IndexProvider : {\n" );
        builder.append( " * Indexed Objects :" );
        for ( Map.Entry<Class<? extends IndexedObject>, BitSet> indexedObjectType : indexedObjectTypes.entrySet() ) {
            builder.append( "\n  " ).append( indexedObjectType.getKey() ).append( " : " ).append( indexedObjectType.getValue() );
        }
        builder.append( "\n * Indexed Type Keys :" );
        for ( IndexedTypeKey indexedTypeKey : indexedTypeKeys ) {
            if ( indexedTypeKey.index >= 0 ) {
                builder.append( "\n  " ).append( indexedTypeKey );
            }
        }
        builder.append( "\n}" );
        return builder.toString();
    }

}
