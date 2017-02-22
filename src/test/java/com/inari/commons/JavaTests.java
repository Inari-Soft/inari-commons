package com.inari.commons;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;

import org.junit.Test;

public class JavaTests {
    
    @Test
    public void testTwoCaseIteration() {
        int from = 0;
        int size = 10;
        
        Collection<Integer> result = new ArrayList<Integer>();
        for ( int i = from; ( size < from )? i > size : i < size; i = ( size < from )? --i : ++i ) {
            result.add( i );
        }
        
        assertEquals( "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", result.toString() );
        
        size = -10;
        
        result = new ArrayList<Integer>();
        for ( int i = from; ( size < from )? i > size : i < size; i = ( size < from )? --i : ++i ) {
            result.add( i );
        }
        
        assertEquals( "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9]", result.toString() );
    }
    
    @Test
    public void testMathCeilAndFloor() {
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.1 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.2 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.3 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.4 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.55555 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.65555 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.75555 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.85555 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 0.95555 ) ) );
        assertEquals( "1.0", String.valueOf( Math.ceil( 1.0 ) ) );
        assertEquals( "2.0", String.valueOf( Math.ceil( 1.000001 ) ) );
    }
    
    @Test
    public void indexIncrementTest() {
        String[] array = new String[] { "one", "two", "three" };
        int index = 0;
        assertEquals( "one", array[ index++ ] );
        assertEquals( "two", array[ index++ ] );
        assertEquals( "three", array[ index++ ] );
    }
    
    @Test
    public void bitSetMaxTest() {
        BitSet bitSet = new BitSet();
        bitSet.set( Integer.MAX_VALUE );
        long[] longArray = bitSet.toLongArray();
        assertEquals( "33554432", String.valueOf( longArray.length ) );
        
        bitSet.clear();
        bitSet.set( 1000000 );
        longArray = bitSet.toLongArray();
        byte[] byteArray = bitSet.toByteArray();
        assertEquals( "15626", String.valueOf( longArray.length ) );
        assertEquals( "125001", String.valueOf( byteArray.length ) );
        
        bitSet.clear();
        bitSet.set( 100000 );
        longArray = bitSet.toLongArray();
        byteArray = bitSet.toByteArray();
        assertEquals( "1563", String.valueOf( longArray.length ) );
        assertEquals( "12501", String.valueOf( byteArray.length ) );
        
        bitSet.clear();
        bitSet.set( 10000 );
        longArray = bitSet.toLongArray();
        byteArray = bitSet.toByteArray();
        assertEquals( "157", String.valueOf( longArray.length ) );
        assertEquals( "1251", String.valueOf( byteArray.length ) );
        
        bitSet.clear();
        bitSet.set( 1000 );
        longArray = bitSet.toLongArray();
        byteArray = bitSet.toByteArray();
        assertEquals( "16", String.valueOf( longArray.length ) );
        assertEquals( "126", String.valueOf( byteArray.length ) );
    }

}
