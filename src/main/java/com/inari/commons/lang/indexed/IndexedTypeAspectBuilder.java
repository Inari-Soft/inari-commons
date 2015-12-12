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

import com.inari.commons.lang.aspect.AspectSetBuilder;

public final class IndexedTypeAspectBuilder {
    
    private IndexedTypeAspectSet toBuild;
    
    
    public IndexedTypeAspectBuilder( Class<? extends IndexedTypeKey> indexedBaseType ) {
        int indexedTypeSize = Indexer.getIndexedObjectSize( indexedBaseType );
        toBuild = new IndexedTypeAspectSet( indexedBaseType, indexedTypeSize );
    }
    
    public IndexedTypeAspectBuilder( Class<? extends IndexedTypeKey> indexedBaseType, int length ) {
        toBuild = new IndexedTypeAspectSet( indexedBaseType, length );
    }
    
    public final IndexedTypeAspectBuilder set( IndexedTypeKey indexedTypeKey ) {
        if ( indexedTypeKey == null ) {
            return this;
        }
        
        AspectSetBuilder.set( toBuild, indexedTypeKey.index() );
        return this;
    }

    public final IndexedTypeAspectBuilder reset( IndexedTypeKey indexedTypeKey ) {
        if ( indexedTypeKey == null ) {
            return this;
        }
        
        AspectSetBuilder.reset( toBuild, indexedTypeKey.index() );
        return this;
    }
    
    public final IndexedTypeAspectSet build() {
        IndexedTypeAspectSet result = toBuild;
        toBuild = null;
        return result;
    }
    
    public static final IndexedTypeAspectSet build( IndexedTypeKey indexedTypeKey ) {
        Class<? extends IndexedTypeKey> indexedBaseType = indexedTypeKey.indexedObjectType();
        return build( indexedBaseType, Indexer.getIndexedObjectSize( indexedBaseType ), indexedTypeKey.indexedType );
    }
    
    public static final IndexedTypeAspectSet build( Class<? extends IndexedTypeKey> indexedBaseType ) {
        return build( indexedBaseType, Indexer.getIndexedObjectSize( indexedBaseType ), null );
    }
    
    public static final IndexedTypeAspectSet build( Class<? extends IndexedTypeKey> indexedBaseType, Class<? extends IndexedType> indexType ) {
        return build( indexedBaseType, Indexer.getIndexedObjectSize( indexedBaseType ), indexType );
    }
    
    public static final IndexedTypeAspectSet build( Class<? extends IndexedTypeKey> indexedBaseType, int length, Class<? extends IndexedType> indexType ) {
        IndexedTypeAspectSet result = new IndexedTypeAspectSet( indexedBaseType, length );
        if ( indexType == null ) {
            return result;
        }
        
        return set( result, indexType );
    }
    
    public static void clear( IndexedTypeAspectSet aspect ) {
        if ( aspect == null ) {
            return;
        }
        
        aspect.clear();
    }

    public static final IndexedTypeAspectSet set( IndexedTypeAspectSet aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return aspect;
        }
        AspectSetBuilder.set( aspect, Indexer.getIndexedTypeKey( aspect.indexedBaseType, indexType ).index() );
        return aspect;
    }
    
    public static final IndexedTypeAspectSet set( IndexedTypeAspectSet aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return aspect;
        }
        AspectSetBuilder.set( aspect, indexedType.indexedTypeKey().index );
        return aspect;
    }
    
    public static final IndexedTypeAspectSet reset( IndexedTypeAspectSet aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return aspect;
        }
        AspectSetBuilder.reset( aspect, Indexer.getIndexedTypeKey( aspect.indexedBaseType, indexType ).index() );
        return aspect;
    }
    
    public static final IndexedTypeAspectSet reset( IndexedTypeAspectSet aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return aspect;
        }
        AspectSetBuilder.reset( aspect, indexedType.indexedTypeKey().index );
        return aspect;
    }

}
