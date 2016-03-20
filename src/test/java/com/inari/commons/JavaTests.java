package com.inari.commons;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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

}
