package com.inari.commons.lang.aspect;

import java.util.BitSet;
import java.util.Iterator;

import com.inari.commons.lang.list.IntBag;

public final class Aspects implements IAspects {
    
    final AspectGroup group;
    final BitSet bitset;
    final BitSet tempBitset;
    
    Aspects( AspectGroup group, int initSize ) {
        this.group = group;
        bitset = new BitSet( initSize );
        tempBitset = new BitSet( initSize );
    }
    
    private Aspects( Aspects source ) {
        this( source.group, source.size() );
        bitset.or( source.bitset );
    }
    
    public final AspectGroup getAspectGroup() {
        return group;
    }

    public final boolean valid() {
        return ( bitset != null && !bitset.isEmpty() );
    }
    
    public final int size() {
        return bitset.size();
    }
    
    public final boolean isEmpty() {
        return bitset.cardinality() <= 0;
    }
    
    public final Aspects set( Aspect aspect ) {
        checkType( aspect );
        bitset.set( aspect.index() );
        return this;
    }
    
    public final Aspects reset( Aspect aspect ) {
        checkType( aspect );
        bitset.set( aspect.index(), false );
        return this;
    }

    public final Aspects set( Aspects aspects ) {
        checkType( aspects );
        clear();
        bitset.or( aspects.bitset );
        return this;
    }

    public final Aspects reset( Aspects aspects ) {
        checkType( aspects );
        bitset.andNot( aspects.bitset );
        return this;
    }

    public final boolean include( Aspects aspects ) {
        checkType( aspects );
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
    
    public final boolean exclude( Aspects aspects ) {
        checkType( aspects );
        if ( bitset.isEmpty() || aspects.bitset.isEmpty() ) {
            return true;
        }
        
        if ( this == aspects ) {
            return false;
        }
        
        tempBitset.clear();
        tempBitset.or( bitset );
        tempBitset.and( aspects.bitset );
        return tempBitset.isEmpty();
    }
    
    public final boolean intersects( Aspects aspects ) {
        checkType( aspects );
        return !exclude( aspects );
    }
    
    public final boolean contains( Aspect aspect ) {
        checkType( aspect );
        return bitset.get( aspect.index() );
    }
    
    @Override
    public final Iterator<Aspect> iterator() {
        return new AspectIterator();
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

    private final void checkType( Aspects aspects ) {
        if ( aspects.group != group ) {
            throw new IllegalArgumentException( "Aspect group missmatch: " + aspects.group + " " + group );
        }
    }
    
    private final void checkType( Aspect aspect ) {
        if ( aspect.aspectGroup() != group ) {
            throw new IllegalArgumentException( "Aspect type missmatch: " + aspect.aspectGroup() + " " + group );
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "Aspects [group=" );
        builder.append( group );
        builder.append( " {" );
        AspectIterator iterator = new AspectIterator();
        while( iterator.hasNext() ) {
            builder.append( iterator.next().name() );
            if ( iterator.hasNext() ) {
                builder.append( ", " );
            }
        }
        builder.append( "}" );
        builder.append( "]" );
        return builder.toString();
    }
    
    private final class AspectIterator implements Iterator<Aspect> {
        
        private int nextSetBit = bitset.nextSetBit( 0 );

        @Override
        public boolean hasNext() {
            return nextSetBit >= 0;
        }

        @Override
        public Aspect next() {
            Aspect aspect = group.getAspect( nextSetBit );
            nextSetBit = bitset.nextSetBit( nextSetBit + 1 );
            return aspect;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
