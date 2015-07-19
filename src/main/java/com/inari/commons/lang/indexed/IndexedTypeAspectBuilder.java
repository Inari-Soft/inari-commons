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

import com.inari.commons.lang.aspect.AspectBuilder;

public final class IndexedTypeAspectBuilder {
    
    private IndexedTypeAspect toBuild;
    
    
    public IndexedTypeAspectBuilder( Class<? extends IndexedBaseType> indexedType ) {
        indexedType = Indexer.findIndexedType( indexedType );
        int indexedTypeSize = Indexer.getIndexedTypeSize( indexedType );
        toBuild = new IndexedTypeAspect( indexedType, indexedTypeSize );
    }
    
    public IndexedTypeAspectBuilder( Class<? extends IndexedBaseType> indexedType, int length ) {
        indexedType = Indexer.findIndexedType( indexedType );
        toBuild = new IndexedTypeAspect( indexedType, length );
    }
    
    public final IndexedTypeAspectBuilder set( Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return this;
        }
        
        Indexer.checkIndexedType( indexType, toBuild.indexedType );
        AspectBuilder.set( toBuild, Indexer.getIndexForType( indexType, toBuild.indexedType ) );
        return this;
    }

    public final IndexedTypeAspectBuilder reset( Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return this;
        }
        
        AspectBuilder.reset( toBuild, Indexer.getIndexForType( indexType, toBuild.indexedType ) );
        return this;
    }
    
    public final IndexedTypeAspect build() {
        IndexedTypeAspect result = toBuild;
        toBuild = null;
        return result;
    }
    
    public static final IndexedTypeAspect build( Class<? extends IndexedBaseType> indexedType ) {
        return build( indexedType, Indexer.getIndexedTypeSize( indexedType ), (Class<? extends IndexedType>) null );
    }
    
    public static final IndexedTypeAspect build( Class<? extends IndexedBaseType> indexedType, Class<? extends IndexedType> indexType ) {
        return build( indexedType, Indexer.getIndexedTypeSize( indexedType ), indexType );
    }
    
    public static final IndexedTypeAspect build( Class<? extends IndexedBaseType> indexedType, int length, Class<? extends IndexedType> indexType ) {
        indexedType = Indexer.findIndexedType( indexedType );
        IndexedTypeAspect result = new IndexedTypeAspect( indexedType, length );
        if ( indexType == null ) {
            return result;
        }
        
        set( result, indexType );
        return result;
    }
    
    public static void clear( IndexedTypeAspect aspect ) {
        if ( aspect == null ) {
            return;
        }
        
        aspect.clear();
    }

    public static final void set( IndexedTypeAspect aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexType, aspect.indexedType );
        AspectBuilder.set( aspect, Indexer.getIndexForType( indexType, aspect.indexedType ) );
    }
    
    public static final void set( IndexedTypeAspect aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexedType.getClass(), aspect.indexedType );
        AspectBuilder.set( aspect, indexedType.index() );
    }
    
    public static final void reset( IndexedTypeAspect aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexType, aspect.indexedType );
        AspectBuilder.reset( aspect, Indexer.getIndexForType( indexType, aspect.indexedType ) );
    }
    
    public static final void reset( IndexedTypeAspect aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexedType.getClass(), aspect.indexedType );
        AspectBuilder.reset( aspect, indexedType.index() );
    }

}
