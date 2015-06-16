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
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package com.inari.commons.lang.indexed;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IndexProvider {
    
    private final static List<Class<?>> unknownIndexedTypes = new ArrayList<Class<?>>();
    
    private final static List<Class<? extends IndexedType>> indexedTypes = new ArrayList<Class<? extends IndexedType>>();
    private final static List<List<Class<? extends Indexed>>> indexMap = Collections.synchronizedList( new ArrayList<List<Class<? extends Indexed>>>() );
    
    private final static Map<Class<? extends IndexedObject>, BitSet> indexedObjectTypes = new HashMap<Class<? extends IndexedObject>, BitSet>();
    
    
    // **** OBJECT INDEX *********************************************************
    
    static int nextObjectIndex( IndexedObject indexedObject ) {
        return nextObjectIndex( indexedObject.getClass() );
    }
    
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
        BitSet indexSet = indexedObjectTypes.get( indexedObject.getIndexedObjectType() );
        if ( indexSet != null ) {
            indexSet.clear( indexedObject.indexedId() );
        }
    }
    
    final static void setIndexedObjectIndex( IndexedObject indexedObject ) {
        Class<? extends IndexedObject> indexedObjectType = indexedObject.getIndexedObjectType();
        int index = indexedObject.indexedId();
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
    
    public final static int getIndexForType( Class<? extends Indexed> type, Class<? extends IndexedType> indexedType ) {
        checkIndexedType( type, indexedType );
        List<Class<? extends Indexed>> indexedOfType = getCreateIndexedOfType( indexedType, type );
        return indexedOfType.indexOf( type );
    }
    
    @SuppressWarnings( "unchecked" )
    public final static int getIndexForType( Class<?> type ) {
        if ( Indexed.class.isAssignableFrom( type ) ) {
            Class<? extends Indexed> _type = (Class<? extends Indexed>) type;
            Class<? extends IndexedType> indexedType = findIndexedType( _type );
            return getIndexForType( _type, indexedType );
        } 
        
        if ( !unknownIndexedTypes.contains( type ) ) {
            unknownIndexedTypes.add( type );
        }
        return unknownIndexedTypes.indexOf( type );
    }
    
    public final static Class<? extends Indexed> getTypeForIndex( Class<? extends IndexedType> indexedType, int index ) {
        List<Class<? extends Indexed>> indexedTypeList = indexedTypeList( indexedType );
        if ( indexedTypeList != null ) {
            if ( index < 0 || index >= indexedTypeList.size() ) {
                return null;
            }
            return indexedTypeList.get( index );
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
    public final static <T extends Indexed> Class<T> getCastedTypeForIndex( Class<? extends IndexedType> indexedType, int index ) {
        return (Class<T>) getTypeForIndex( indexedType, index );
    }
    
    public final static int getIndexedTypeSize( Class<? extends IndexedType> indexedType ) {
        indexedType = findIndexedType( indexedType );
        List<Class<? extends Indexed>> indexedOfType = getCreateIndexedTypeList( indexedType );
        return indexedOfType.size();
    }
    
    public int getNumberOfTypes( Class<? extends IndexedType> indexedType ) {
        return indexedTypes.size();
    }
    
    public int getNumberOfKnownIndexedTypes() {
        return unknownIndexedTypes.size();
    }

    public final static void clear() {
        unknownIndexedTypes.clear();
        indexedTypes.clear();
        indexMap.clear();
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
        for ( Class<? extends IndexedType> indexedType : indexedTypes ) {
            List<Class<? extends Indexed>> value = indexMap.get( indexedIndex );
            builder.append( "  " ).append( indexedType ).append( " : {\n" );
            int index = 0;
            for ( Class<? extends Indexed> indexed : value ) {
                builder.append( "    " ).append( index ).append( ":" ).append( indexed.getName() ).append( "\n" );
                index++;
            }
            builder.append( "  }\n" );
            indexedIndex++;
        }
        if ( !unknownIndexedTypes.isEmpty() ) {
            builder.append( "  " ).append( " * Unknown Indexed Types : {\n" );
            int index = 0;
            for ( Class<?> indexed : unknownIndexedTypes ) {
                builder.append( "    " ).append( index ).append( ":" ).append( indexed.getName() ).append( "\n" );
                index++;
            }
        }
        builder.append( "}" );
        return builder.toString();
    }
    
    public static final void checkIndexedType( Class<? extends Indexed> indexType, Class<? extends IndexedType> indexedType ) {
        if ( indexType.isInterface() || Modifier.isAbstract( indexType.getModifiers() ) ) {
            throw new IllegalArgumentException( "indexType: " + indexType + " is not an instantiable class" );
        }
        if ( !indexedType.isAssignableFrom( indexType ) ) {
            throw new IllegalArgumentException( String.format( "IndexedType missmatch: indexType:%s is not a valid substitute of indexedType:%s", indexType, indexedType ) );
        }
    }

    public static final void checkIndexedType( Indexed indexed, Class<? extends IndexedType> indexedType ) {
        if ( indexed.indexedType() != indexedType ) {
            throw new IllegalArgumentException( "IndexedType missmatch: indexedType=" + indexedType + " indexType=" + indexed.indexedType() );
        }
    }
    
    @SuppressWarnings( "unchecked" )
    final static Class<? extends IndexedType> findIndexedType( Class<? extends IndexedType> indexedType ) {
        while ( ( indexedType.getSuperclass() != null ) && IndexedType.class.isAssignableFrom( indexedType.getSuperclass() ) ) {
            indexedType = (Class<? extends IndexedType>) indexedType.getSuperclass();
        }
        if ( indexedType.isInterface() ) {
            return indexedType;
        }
        Class<?>[] interfaces = indexedType.getInterfaces();
        if ( interfaces != null ) {
            for ( Class<?> _interface : interfaces ) {
                if ( _interface != Indexed.class && _interface != IndexedType.class && IndexedType.class.isAssignableFrom( _interface ) ) {
                    indexedType = (Class<? extends IndexedType>) _interface;
                }
            }
        }
        return indexedType;
    }
    
    public final static IndexedTypeList getIndexedTypeList( Class<? extends IndexedType> indexedType ) {
        indexedType = findIndexedType( indexedType );
        return new IndexedTypeList( indexedType );
    }
    
    private final static List<Class<? extends Indexed>> indexedTypeList( Class<? extends IndexedType> indexedType ) {
        if ( !indexedTypes.contains( indexedType ) ) {
            return null;
        }
        return indexMap.get( indexedTypes.indexOf( indexedType ) );
    }
    
    private final static List<Class<? extends Indexed>> getCreateIndexedTypeList( Class<? extends IndexedType> indexedType ) {
        if ( !indexedTypes.contains( indexedType ) ) {
            indexedTypes.add( indexedType );
            List<Class<? extends Indexed>> indexedOfType = Collections.synchronizedList( new ArrayList<Class<? extends Indexed>>() );
            indexMap.add( indexedOfType );
        }
        
        return indexedTypeList( indexedType );
    }

    private final static List<Class<? extends Indexed>> getCreateIndexedOfType( Class<? extends IndexedType> indexedType, Class<? extends Indexed> type ) {
        List<Class<? extends Indexed>> indexedOfType = getCreateIndexedTypeList( indexedType );
        if ( !indexedOfType.contains( type ) ) {
            indexedOfType.add( type );
        }
        return indexedOfType;
    }

    
    public static final class IndexedTypeList {
        
        private final Class<? extends IndexedType> indexedType;
        private final List<Class<? extends Indexed>> indexedTypes;
        
        IndexedTypeList( Class<? extends IndexedType> indexedType ) {
            this.indexedType = indexedType;
            this.indexedTypes = IndexProvider.indexedTypeList( indexedType );
        }

        public Class<? extends IndexedType> getIndexedType() {
            return indexedType;
        }

        public int getTypeIndex( Class<? extends Indexed> indexType ) {
            return indexedTypes.indexOf( indexType );
        }
    }

}
