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

import java.util.BitSet;

public class Aspect {

    protected final BitSet bitset;
    private final BitSet tempBitset;
    
    protected Aspect( int size ) {
        bitset = new BitSet( size );
        tempBitset = new BitSet( size );
    }
    
    protected Aspect( Aspect source ) {
        this( source.bitset.size() );
        bitset.or( source.bitset );
    }
    
    public boolean isEmpty() {
        return ( bitset == null || bitset.isEmpty() );
    }
    
    public final Aspect add( Aspect aspect ) {
        Aspect result = getCopy();
        result.bitset.or( aspect.bitset );
        return result;
    }
    
    public Aspect remove( Aspect aspect ) {
        Aspect result = getCopy();
        result.bitset.andNot( aspect.bitset );
        return result;
    }

    public final boolean include( Aspect aspect ) {
        if ( bitset.isEmpty() || aspect.bitset.isEmpty() ) {
            return false;
        }
        
        if ( this == aspect ) {
            return true;
        }
        
        tempBitset.clear();
        tempBitset.or( bitset );
        tempBitset.and( aspect.bitset );
        return tempBitset.equals( aspect.bitset );
    }
    
    public final boolean exclude( Aspect aspect ) {
        if ( bitset.isEmpty() || aspect.bitset.isEmpty() ) {
            return false;
        }
        
        if ( this == aspect ) {
            return false;
        }
        
        tempBitset.clear();
        tempBitset.or( bitset );
        tempBitset.and( aspect.bitset );
        return tempBitset.isEmpty();
    }
    
    public final boolean intersects( Aspect aspect ) {
        return !exclude( aspect );
    }
    
    public final boolean contains( int index ) {
        if ( index < 0 || index >= bitset.length() ) {
            return false;
        }  
        return bitset.get( index );
    }
    
    public final int nextSetBit( int fromIndex ) {
        return bitset.nextSetBit( fromIndex );
    }
    
    protected void clear() {
        bitset.clear();
        if ( tempBitset != null ) {
            tempBitset.clear();
        }
    }
    
    public Aspect getCopy() {
        return new Aspect( this );
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "Aspect [ size=" + bitset.size() + " bitset=" ).append( bitset ).append( " ]" );
        return sb.toString();
    }

}
