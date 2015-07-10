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
