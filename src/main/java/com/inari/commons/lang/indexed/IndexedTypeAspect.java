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

import com.inari.commons.lang.aspect.IndexedAspect;

public final class IndexedTypeAspect extends IndexedAspect {
    
    final Class<? extends IndexedBaseType> indexedType;

    IndexedTypeAspect( Class<? extends IndexedBaseType> indexedType, int length ) {
        super( length );
        this.indexedType = indexedType;
    }
    
    IndexedTypeAspect( IndexedTypeAspect source ) {
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
    public IndexedTypeAspect getCopy() {
        return new IndexedTypeAspect( this );
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