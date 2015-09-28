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

}
