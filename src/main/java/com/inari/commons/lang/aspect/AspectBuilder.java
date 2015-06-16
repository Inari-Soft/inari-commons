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
package com.inari.commons.lang.aspect;

public final class AspectBuilder {
    
    private Aspect toBuild;
    
    public final AspectBuilder newAspect( int length ) {
        toBuild = new Aspect( length );
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
    
    public static final Aspect create( int length ) {
        return new Aspect( length );
    }
    
    public static final Aspect create( int length, int... toSet ) {
        return set( create( length ), toSet );
    }
    
    public static final Aspect set( Aspect aspect, int index ) {
        aspect.bitset.set( index );
        return aspect;
    }
    
    public static final Aspect reset( Aspect aspect, int index ) {
        aspect.bitset.set( index, false );
        return aspect;
    }
    
    public static final Aspect set( Aspect aspect, int... toSet ) {
        if ( toSet != null && toSet.length > 0 ) {
            for ( int i = 0; i < toSet.length; i++ ) {
                aspect.bitset.set( toSet[ i ] );
            }
        }
        return aspect;
    }
    
    public static final Aspect reset( Aspect aspect, int... toReset ) {
        if ( toReset != null && toReset.length > 0 ) {
            for ( int i = 0; i < toReset.length; i++ ) {
                aspect.bitset.set( toReset[ i ], false );
            }
        }
        return aspect;
    }
    
    public static final Aspect modify( Aspect aspect, int[] toSet, int[] toReset ) {
        set( aspect, toSet );
        if ( toReset != null && toReset.length > 0 ) {
            for ( int i = 0; i < toReset.length; i++ ) {
                aspect.bitset.set( toReset[ i ], false );
            }
        }
        return aspect;
    }
}
