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

public abstract class AbstractIndexedBag implements IndexedBag {
    
    protected final Class<? extends IndexedType> indexedType;
    protected int size = 0;
    protected IndexedAspect aspect;
    
    protected AbstractIndexedBag( Class<? extends IndexedType> indexedType ) {
        this.indexedType = Indexer.findIndexedType( indexedType );
        aspect = new IndexedAspect( indexedType, Indexer.getIndexedTypeSize( this.indexedType ) );
    }
    
    protected AbstractIndexedBag( Class<? extends IndexedType> indexedType, int length ) {
        this.indexedType = Indexer.findIndexedType( indexedType );
        aspect = new IndexedAspect( indexedType, length );
    }
    
    @Override
    public final int size() {
        return size;
    }
    
    @Override
    public int[] getMappedIndexes() {
        int[] result = new int[ size ];
        int index = 0;
        for ( int i = aspect.nextSetBit( 0 ); i >= 0; i = aspect.nextSetBit( i + 1 ) ) {
            result[ index ] = i;
            index++;
        }
        return result;
    }
    
    @Override
    public final IndexedAspect getAspect() {
        return aspect;
    }
    
    @Override
    public final Class<? extends IndexedType> getIndexedType() {
        return indexedType;
    }
    
    public final boolean include( IndexedAspect aspect ) {
        return this.aspect.include( aspect );
    }
    
    @Override
    public void clear() {
        if ( aspect != null ) {
            IndexedAspectBuilder.clear( aspect );
        }
        size = 0;
    }

}
