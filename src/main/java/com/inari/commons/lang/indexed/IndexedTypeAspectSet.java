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

import com.inari.commons.lang.aspect.AspectBitSet;

public final class IndexedTypeAspectSet extends AspectBitSet {
    
    final Class<? extends IndexedTypeKey> indexedBaseType;

    IndexedTypeAspectSet( Class<? extends IndexedTypeKey> indexedBaseType, int length ) {
        super( length );
        this.indexedBaseType = indexedBaseType;
    }
    
    IndexedTypeAspectSet( IndexedTypeAspectSet source ) {
        super( source );
        indexedBaseType = source.indexedBaseType;
    }

    public final Class<? extends IndexedTypeKey> getIndexedBaseType() {
        return indexedBaseType;
    }

    public boolean contains( IndexedTypeKey indexType ) {
        return contains( indexType.index() );
    }
    
    public boolean contains( IndexedType indexedType ) {
        return contains( indexedType.indexedTypeKey().typeIndex() );
    }

    
    @Override
    protected void clear() {
        super.clear();
    }
    
    @Override
    public IndexedTypeAspectSet getCopy() {
        return new IndexedTypeAspectSet( this );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "IndexedAspect [ indexedBaseType=" ).append( indexedBaseType.getSimpleName() );
        sb.append( ", bitset=" ).append( bitset );
        sb.append( ", types={" );
        boolean typeAdded = false;
        for ( int i = 0; i < bitset.size(); i++ ) {
            if ( bitset.get( i ) ) {
                typeAdded = true;
                sb.append( Indexer.getIndexedTypeKeyForIndex( indexedBaseType, i ).indexedType.getSimpleName() ).append( "," );
            }
        }
        if ( typeAdded ) {
            sb.deleteCharAt( sb.length() - 1 );
        }
        sb.append( "} ]" );
        return sb.toString();
    }

    

}
