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
package com.inari.commons.lang.aspect;

public final class AspectBuilder {
    
    private IndexedAspect toBuild;
    
    public final AspectBuilder newAspect( int length ) {
        toBuild = new IndexedAspect( length );
        return this;
    }
    
    public final AspectBuilder set( int index ) {
        toBuild.bitset.set( index );
        return this;
    }
    
    public final AspectBuilder reset( int index ) {
        toBuild.bitset.set( index, false );
        return this;
    }
    
    public final AspectBuilder clear() {
        toBuild.clear();
        return this;
    }
    
    public static final IndexedAspect create( int length ) {
        return new IndexedAspect( length );
    }
    
    public static final IndexedAspect create( int length, int... toSet ) {
        return set( create( length ), toSet );
    }
    
    public static final IndexedAspect set( IndexedAspect aspect, int index ) {
        aspect.bitset.set( index );
        return aspect;
    }
    
    public static final IndexedAspect reset( IndexedAspect aspect, int index ) {
        aspect.bitset.set( index, false );
        return aspect;
    }
    
    public static final IndexedAspect set( IndexedAspect aspect, int... toSet ) {
        if ( toSet != null && toSet.length > 0 ) {
            for ( int i = 0; i < toSet.length; i++ ) {
                aspect.bitset.set( toSet[ i ] );
            }
        }
        return aspect;
    }
    
    public static final IndexedAspect reset( IndexedAspect aspect, int... toReset ) {
        if ( toReset != null && toReset.length > 0 ) {
            for ( int i = 0; i < toReset.length; i++ ) {
                aspect.bitset.set( toReset[ i ], false );
            }
        }
        return aspect;
    }
    
    public static final IndexedAspect modify( IndexedAspect aspect, int[] toSet, int[] toReset ) {
        set( aspect, toSet );
        if ( toReset != null && toReset.length > 0 ) {
            for ( int i = 0; i < toReset.length; i++ ) {
                aspect.bitset.set( toReset[ i ], false );
            }
        }
        return aspect;
    }
}
