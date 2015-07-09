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

import com.inari.commons.lang.aspect.Aspect;

public final class IndexedAspect extends Aspect {
    
    final Class<? extends IndexedBaseType> indexedType;

    IndexedAspect( Class<? extends IndexedBaseType> indexedType, int length ) {
        super( length );
        this.indexedType = indexedType;
    }
    
    IndexedAspect( IndexedAspect source ) {
        super( source );
        indexedType = source.indexedType;
    }

    public final Class<? extends IndexedBaseType> getIndexedType() {
        return indexedType;
    }

    public boolean contains( Class<? extends IndexedType> indexType ) {
        Indexer.checkIndexedType( indexType, indexedType );
        return contains( Indexer.getIndexForType( indexType, indexedType ) );
    }
    
    public boolean contains( IndexedType indexedType ) {
        Indexer.checkIndexedType( indexedType.getClass(), this.indexedType );
        return contains( indexedType.index() );
    }

    
    @Override
    protected void clear() {
        super.clear();
    }
    
    @Override
    public IndexedAspect getCopy() {
        return new IndexedAspect( this );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "IndexedAspect [ indexedBaseType=" ).append( indexedType.getSimpleName() );
        sb.append( ", bitset=" ).append( bitset );
        sb.append( ", types={" );
        boolean typeAdded = false;
        for ( int i = 0; i < bitset.size(); i++ ) {
            if ( bitset.get( i ) ) {
                typeAdded = true;
                sb.append( Indexer.getTypeForIndex( indexedType, i ).getSimpleName() ).append( "," );
            }
        }
        if ( typeAdded ) {
            sb.deleteCharAt( sb.length() - 1 );
        }
        sb.append( "} ]" );
        return sb.toString();
    }

    

}
