package com.inari.commons.lang.aspect;

import java.util.BitSet;
import java.util.Collection;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;
import com.inari.commons.lang.list.IntBag;

public class Aspects implements StringConfigurable {
    
    protected final BitSet bitset;
    private final BitSet tempBitset;
    
    protected Aspects( int capacity ) {
        bitset = new BitSet( capacity );
        tempBitset = new BitSet( capacity );
    }
    
    protected Aspects( Aspects source ) {
        this( source.bitset.size() );
        bitset.or( source.bitset );
    }
    
    public final boolean valid() {
        return ( bitset != null && !bitset.isEmpty() );
    }
    
    public final int length() {
        return bitset.length();
    }
    
    public final Aspects set( Aspect aspect ) {
        bitset.set( aspect.aspectId() );
        return this;
    }
    
    public final Aspects set( int aspectId ) {
        bitset.set( aspectId );
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
    
    public final boolean contains( Aspect aspect ) {
        return bitset.get( aspect.aspectId() );
    }
    
    public final int nextSetBit( int fromIndex ) {
        return bitset.nextSetBit( fromIndex );
    }
    
    public final void clear() {
        bitset.clear();
        if ( tempBitset != null ) {
            tempBitset.clear();
        }
    }
    
    public Aspects getCopy() {
        return new Aspects( this );
    }
    
    public final IntBag getValues() {
        IntBag result = new IntBag( bitset.cardinality() );
        for ( int i = bitset.nextSetBit( 0 ); i >= 0; i = bitset.nextSetBit( i + 1 ) ) {
            result.add( i );
        }
        return result;
    }
    
    @Override
    public void fromConfigString( String stringValue ) {
        bitset.clear();
        
        if ( stringValue == null ) {
            return;
        }
        
        Collection<String> bits = StringUtils.split( stringValue, StringUtils.LIST_VALUE_SEPARATOR_STRING );
        int index = 0;
        for ( String bit : bits ) {
            bitset.set( index, !"0".equals( bit ) );
            index++;
        }
    }

    @Override
    public String toConfigString() {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < bitset.length(); i++ ) {
            sb.append( ( bitset.get( i ) )? "1" : "0" );
            if ( i < bitset.length() ) {
                sb.append( StringUtils.LIST_VALUE_SEPARATOR );
            }
        }
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "Aspects [ size=" + bitset.size() + " bitset=" ).append( bitset ).append( " ]" );
        return sb.toString();
    }
    

}
