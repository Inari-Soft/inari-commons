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

import com.inari.commons.lang.aspect.AspectBuilder;

public final class IndexedAspectBuilder {
    
    private IndexedAspect toBuild;
    
    
    public IndexedAspectBuilder( Class<? extends IndexedBaseType> indexedType ) {
        indexedType = Indexer.findIndexedType( indexedType );
        int indexedTypeSize = Indexer.getIndexedTypeSize( indexedType );
        toBuild = new IndexedAspect( indexedType, indexedTypeSize );
    }
    
    public IndexedAspectBuilder( Class<? extends IndexedBaseType> indexedType, int length ) {
        indexedType = Indexer.findIndexedType( indexedType );
        toBuild = new IndexedAspect( indexedType, length );
    }
    
    public final IndexedAspectBuilder set( Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return this;
        }
        
        Indexer.checkIndexedType( indexType, toBuild.indexedType );
        AspectBuilder.set( toBuild, Indexer.getIndexForType( indexType, toBuild.indexedType ) );
        return this;
    }

    public final IndexedAspectBuilder reset( Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return this;
        }
        
        AspectBuilder.reset( toBuild, Indexer.getIndexForType( indexType, toBuild.indexedType ) );
        return this;
    }
    
    public final IndexedAspect build() {
        IndexedAspect result = toBuild;
        toBuild = null;
        return result;
    }
    
    public static final IndexedAspect build( Class<? extends IndexedBaseType> indexedType ) {
        return build( indexedType, Indexer.getIndexedTypeSize( indexedType ), (Class<? extends IndexedType>) null );
    }
    
    public static final IndexedAspect build( Class<? extends IndexedBaseType> indexedType, Class<? extends IndexedType> indexType ) {
        return build( indexedType, Indexer.getIndexedTypeSize( indexedType ), indexType );
    }
    
    public static final IndexedAspect build( Class<? extends IndexedBaseType> indexedType, int length, Class<? extends IndexedType> indexType ) {
        indexedType = Indexer.findIndexedType( indexedType );
        IndexedAspect result = new IndexedAspect( indexedType, length );
        if ( indexType == null ) {
            return result;
        }
        
        set( result, indexType );
        return result;
    }
    
    public static void clear( IndexedAspect aspect ) {
        if ( aspect == null ) {
            return;
        }
        
        aspect.clear();
    }

    public static final void set( IndexedAspect aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexType, aspect.indexedType );
        AspectBuilder.set( aspect, Indexer.getIndexForType( indexType, aspect.indexedType ) );
    }
    
    public static final void set( IndexedAspect aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexedType.getClass(), aspect.indexedType );
        AspectBuilder.set( aspect, indexedType.index() );
    }
    
    public static final void reset( IndexedAspect aspect, Class<? extends IndexedType> indexType ) {
        if ( indexType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexType, aspect.indexedType );
        AspectBuilder.reset( aspect, Indexer.getIndexForType( indexType, aspect.indexedType ) );
    }
    
    public static final void reset( IndexedAspect aspect, IndexedType indexedType ) {
        if ( indexedType == null ) {
            return;
        }
        Indexer.checkIndexedType( indexedType.getClass(), aspect.indexedType );
        AspectBuilder.reset( aspect, indexedType.index() );
    }

}
