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
package com.inari.commons.lang.indexed;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Indexer {
    
    private final static List<Class<?>> unknownIndexedTypes = new ArrayList<Class<?>>();
    
    private final static List<Class<? extends IndexedBaseType>> indexedBaseTypes = new ArrayList<Class<? extends IndexedBaseType>>();
    private final static List<List<Class<? extends IndexedType>>> indexedTypeMap = Collections.synchronizedList( new ArrayList<List<Class<? extends IndexedType>>>() );
    
    private final static Map<Class<? extends IndexedObject>, BitSet> indexedObjectTypes = new HashMap<Class<? extends IndexedObject>, BitSet>();
    
    
    // **** OBJECT INDEX *********************************************************
    
    final static int nextObjectIndex( Class<? extends IndexedObject> indexedObjectType ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObjectType );
        if ( indexSet == null ) {
            indexSet = new BitSet();
            indexedObjectTypes.put( indexedObjectType, indexSet );
        }
        return findFreeObjectIndex( indexSet );
    }

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
    
    final static void disposeIndexedObject( IndexedObject indexedObject ) {
        BitSet indexSet = indexedObjectTypes.get( indexedObject.indexedObjectType() );
        if ( indexSet != null ) {
            indexSet.clear( indexedObject.index() );
        }
    }
    
    final static void setIndexedObjectIndex( IndexedObject indexedObject ) {
        Class<? extends IndexedObject> indexedObjectType = indexedObject.indexedObjectType();
        int index = indexedObject.index();
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
    
    private static int findFreeObjectIndex( BitSet indexSet ) {
        int nextClearBit = indexSet.nextClearBit( 0 );
        indexSet.set( nextClearBit );
        return nextClearBit;
    }
    
    
    // **** INDEXED TYPE *********************************************************
    
    public final static int getIndexForType( IndexedType indexedType ) {
        return getIndexForType( indexedType.indexedType(), indexedType.indexedBaseType() );
    }
    
    public final static int getIndexForType( Class<? extends IndexedType> indexedType, Class<? extends IndexedBaseType> indexedBaseType ) {
        checkIndexedType( indexedType, indexedBaseType );
        List<Class<? extends IndexedType>> indexedOfType = getCreateIndexedOfType( indexedBaseType, indexedType );
        return indexedOfType.indexOf( indexedType );
    }
    
    @SuppressWarnings( "unchecked" )
    public final static int getIndexForType( Class<?> indexedType ) {
        if ( IndexedType.class.isAssignableFrom( indexedType ) ) {
            Class<? extends IndexedType> _type = (Class<? extends IndexedType>) indexedType;
            Class<? extends IndexedBaseType> indexedBaseType = findIndexedType( _type );
            return getIndexForType( _type, indexedBaseType );
        } 
        
        if ( !unknownIndexedTypes.contains( indexedType ) ) {
            unknownIndexedTypes.add( indexedType );
        }
        return unknownIndexedTypes.indexOf( indexedType );
    }
    
    public final static Class<? extends IndexedType> getTypeForIndex( Class<? extends IndexedBaseType> indexedBaseType, int index ) {
        List<Class<? extends IndexedType>> indexedBaseTypeList = indexedTypeList( indexedBaseType );
        if ( indexedBaseTypeList != null ) {
            if ( index < 0 || index >= indexedBaseTypeList.size() ) {
                return null;
            }
            return indexedBaseTypeList.get( index );
        }
        return null;
    }
    
    public final static Class<?> getTypeForIndex( int index ) {
        if ( index < 0 || index >= unknownIndexedTypes.size() ) {
            return null;
        }
        return unknownIndexedTypes.get( index );
    }
    
    @SuppressWarnings( "unchecked" )
    public final static <T extends IndexedType> Class<T> getCastedTypeForIndex( Class<? extends IndexedBaseType> indexedBaseType, int index ) {
        return (Class<T>) getTypeForIndex( indexedBaseType, index );
    }
    
    public final static int getIndexedTypeSize( Class<? extends IndexedBaseType> indexedBaseType ) {
        indexedBaseType = findIndexedType( indexedBaseType );
        List<Class<? extends IndexedType>> indexedOfType = getCreateIndexedTypeList( indexedBaseType );
        return indexedOfType.size();
    }
    
    public int getNumberOfRegisteredBaseTypes() {
        return indexedBaseTypes.size();
    }
    
    public int getNumberOfRegisteredIndexedTypes() {
        return unknownIndexedTypes.size();
    }

    public final static void clear() {
        unknownIndexedTypes.clear();
        indexedBaseTypes.clear();
        indexedTypeMap.clear();
        indexedObjectTypes.clear();
    }

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
        if ( !unknownIndexedTypes.isEmpty() ) {
            builder.append( " * Unknown Indexed Types : {\n" );
            int index = 0;
            for ( Class<?> indexed : unknownIndexedTypes ) {
                builder.append( "    " ).append( index ).append( ":" ).append( indexed.getName() ).append( "\n" );
                index++;
            }
        }
        builder.append( "}" );
        return builder.toString();
    }
    
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
    
    public final static IndexedTypeList getIndexedTypeList( Class<? extends IndexedBaseType> indexedBaseType ) {
        indexedBaseType = findIndexedType( indexedBaseType );
        return new IndexedTypeList( indexedBaseType );
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

    
    public static final class IndexedTypeList {
        
        private final Class<? extends IndexedBaseType> indexedBaseType;
        private final List<Class<? extends IndexedType>> indexedTypes;
        
        IndexedTypeList( Class<? extends IndexedBaseType> indexedBaseType ) {
            this.indexedBaseType = indexedBaseType;
            this.indexedTypes = Indexer.indexedTypeList( indexedBaseType );
        }

        public Class<? extends IndexedBaseType> getIndexedBaseType() {
            return indexedBaseType;
        }

        public int getTypeIndex( Class<? extends IndexedType> indexType ) {
            return indexedTypes.indexOf( indexType );
        }
    }

}
