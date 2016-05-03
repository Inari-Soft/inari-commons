package com.inari.commons.lang.aspect;

import java.util.BitSet;

import com.inari.commons.lang.indexed.Indexed;

public class Aspects {
    
    protected final BitSet bitset;
    private final BitSet tempBitset;
    
    protected Aspects( int size ) {
        bitset = new BitSet( size );
        tempBitset = new BitSet( size );
    }
    
    protected Aspects( Aspects source ) {
        this( source.bitset.size() );
        bitset.or( source.bitset );
    }
    
    public final boolean valid() {
        return ( bitset != null && !bitset.isEmpty() );
    }
    
    public final Aspects set( Aspect aspect ) {
        bitset.set( aspect.aspectId() );
        return this;
    }
    
    public final Aspects reset( Aspect aspect ) {
        bitset.set( aspect.aspectId(), false );
        return this;
    }

    public final Aspects add( Aspects aspects ) {
        Aspects result = getCopy();
        result.bitset.or( aspects.bitset );
        return result;
    }
    
    public final Aspects remove( Aspects aspects ) {
        Aspects result = getCopy();
        result.bitset.andNot( aspects.bitset );
        return result;
    }

    public final boolean include( Aspects aspects ) {
        if ( bitset.isEmpty() || aspects.bitset.isEmpty() ) {
            return false;
        }
        
        if ( this == aspects ) {
            return true;
        }
        
        tempBitset.clear();
        tempBitset.or( bitset );
        tempBitset.and( aspects.bitset );
        return tempBitset.equals( aspects.bitset );
    }
    
    public final boolean exclude( Aspects aspect ) {
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
    
    public final boolean intersects( Aspects aspect ) {
        return !exclude( aspect );
    }
    
    public final boolean contains( int index ) {
        if ( index < 0 || index >= bitset.length() ) {
            return false;
        }  
        return bitset.get( index );
    }
    
    public final boolean contains( Indexed indexed ) {
        return contains( indexed.index() );
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
    
    public Aspects getCopy() {
        return new Aspects( this );
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "Aspects [ size=" + bitset.size() + " bitset=" ).append( bitset ).append( " ]" );
        return sb.toString();
    }

}
