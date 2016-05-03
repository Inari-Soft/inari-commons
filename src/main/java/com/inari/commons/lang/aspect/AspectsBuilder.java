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
package com.inari.commons.lang.aspect;

public final class AspectsBuilder {
    
    private Aspects toBuild;
    
    public final AspectsBuilder newAspect( int length ) {
        toBuild = new Aspects( length );
        return this;
    }
    
    public final AspectsBuilder set( int index ) {
        toBuild.bitset.set( index );
        return this;
    }
    
    public final AspectsBuilder reset( int index ) {
        toBuild.bitset.set( index, false );
        return this;
    }
    
    public final AspectsBuilder clear() {
        toBuild.clear();
        return this;
    }
    
    public static final Aspects create() {
        return new Aspects( 10 );
    }
    
    public static final Aspects createWithLength( int length ) {
        return new Aspects( length );
    }
    
    public static final Aspects create( int... toSet ) {
        return set( createWithLength( toSet.length ), toSet );
    }

    public static final Aspects create( Aspect... toSet ) {
        return set( createWithLength( toSet.length ), toSet );
    }

    
    public static final Aspects set( Aspects aspects, int index ) {
        aspects.bitset.set( index );
        return aspects;
    }
    
    public static final Aspects reset( Aspects aspects, int index ) {
        aspects.bitset.set( index, false );
        return aspects;
    }
    
    public static final Aspects set( Aspects aspects, Aspect... toSet ) {
        if ( toSet != null && toSet.length > 0 ) {
            for ( int i = 0; i < toSet.length; i++ ) {
                aspects.bitset.set( toSet[ i ].aspectId() );
            }
        }
        return aspects;
    }
    
    public static final Aspects set( Aspects aspects, int... toSet ) {
        if ( toSet != null && toSet.length > 0 ) {
            for ( int i = 0; i < toSet.length; i++ ) {
                aspects.bitset.set( toSet[ i ] );
            }
        }
        return aspects;
    }
    
    public static final Aspects reset( Aspects aspects, int... toReset ) {
        if ( toReset != null && toReset.length > 0 ) {
            for ( int i = 0; i < toReset.length; i++ ) {
                aspects.bitset.set( toReset[ i ], false );
            }
        }
        return aspects;
    }
    
    public static final Aspects modify( Aspects aspects, int[] toSet, int[] toReset ) {
        set( aspects, toSet );
        if ( toReset != null && toReset.length > 0 ) {
            for ( int i = 0; i < toReset.length; i++ ) {
                aspects.bitset.set( toReset[ i ], false );
            }
        }
        return aspects;
    }
}
