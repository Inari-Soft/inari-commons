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

import com.inari.commons.lang.aspect.AspectsBuilder;

public final class IndexedTypeAspectBuilder {
    
    private IndexedTypeAspects toBuild;
    
    
    public IndexedTypeAspectBuilder( Class<? extends IndexedTypeKey> indexedBaseType ) {
        int indexedTypeSize = Indexer.getIndexedObjectSize( indexedBaseType );
        toBuild = new IndexedTypeAspects( indexedBaseType, indexedTypeSize );
    }
    
    public IndexedTypeAspectBuilder( Class<? extends IndexedTypeKey> indexedBaseType, int length ) {
        toBuild = new IndexedTypeAspects( indexedBaseType, length );
    }
    
    public final IndexedTypeAspectBuilder set( IndexedTypeKey indexedTypeKey ) {
        if ( indexedTypeKey == null ) {
            return this;
        }
        
        AspectsBuilder.set( toBuild, indexedTypeKey.index() );
        return this;
    }

    public final IndexedTypeAspectBuilder reset( IndexedTypeKey indexedTypeKey ) {
        if ( indexedTypeKey == null ) {
            return this;
        }
        
        AspectsBuilder.reset( toBuild, indexedTypeKey.index() );
        return this;
    }
    
    public final IndexedTypeAspects build() {
        IndexedTypeAspects result = toBuild;
        toBuild = null;
        return result;
    }
    
    public static final IndexedTypeAspects build( IndexedTypeKey indexedTypeKey ) {
        Class<? extends IndexedTypeKey> indexedBaseType = indexedTypeKey.indexedObjectType();
        return build( indexedBaseType, Indexer.getIndexedObjectSize( indexedBaseType ), indexedTypeKey.indexedType );
    }
    
    public static final IndexedTypeAspects build( Class<? extends IndexedTypeKey> indexedBaseType ) {
        return build( indexedBaseType, Indexer.getIndexedObjectSize( indexedBaseType ), null );
    }
    
    public static final IndexedTypeAspects build( Class<? extends IndexedTypeKey> indexedBaseType, Class<? extends IndexedType> indexType ) {
        return build( indexedBaseType, Indexer.getIndexedObjectSize( indexedBaseType ), indexType );
    }
    
    public static final IndexedTypeAspects build( Class<? extends IndexedTypeKey> indexedBaseType, int length, Class<? extends IndexedType> indexType ) {
        IndexedTypeAspects result = new IndexedTypeAspects( indexedBaseType, length );
        if ( indexType == null ) {
            return result;
        }
        
        return set( result, indexType );
    }
    
    public static void clear( IndexedTypeAspects aspect ) {
        if ( aspect == null ) {
            return;
        }
        
        aspect.clear();
    }

    public static final IndexedTypeAspects set( IndexedTypeAspects aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return aspect;
        }
        AspectsBuilder.set( aspect, Indexer.createIndexedTypeKey( aspect.indexedBaseType, indexType ).index() );
        return aspect;
    }
    
    public static final IndexedTypeAspects set( IndexedTypeAspects aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return aspect;
        }
        AspectsBuilder.set( aspect, indexedType.indexedTypeKey().typeIndex() );
        return aspect;
    }
    
    public static final IndexedTypeAspects reset( IndexedTypeAspects aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return aspect;
        }
        AspectsBuilder.reset( aspect, Indexer.createIndexedTypeKey( aspect.indexedBaseType, indexType ).index() );
        return aspect;
    }
    
    public static final IndexedTypeAspects reset( IndexedTypeAspects aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return aspect;
        }
        AspectsBuilder.reset( aspect, indexedType.indexedTypeKey().typeIndex() );
        return aspect;
    }

}
