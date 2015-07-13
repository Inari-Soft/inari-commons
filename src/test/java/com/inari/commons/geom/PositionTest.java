package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PositionTest {
    
    @Test
    public void testPoint() {
        Position p1 = new Position();
        
        assertEquals( p1.x, 0 );
        assertEquals( p1.y, 0 );
        
        p1 = new Position( 10, 4 );
        
        assertEquals( p1.x, 10 );
        assertEquals( p1.y, 4 );
        
        Position p2 = new Position( p1 );
        
        assertEquals( p2.x, 10 );
        assertEquals( p2.y, 4 );
        
        Position p3 = new Position( "40,100" );
        
        assertEquals( p3.x, 40 );
        assertEquals( p3.y, 100 );
        
        try {
            p3 = new Position( "40.5,100" );
            fail( "this should not work and throw an exception!" );
        } catch ( NumberFormatException nfe ) {
            assertEquals( "For input string: \"40.5\"", nfe.getMessage() );
        }
    }
    
    @Test
    public void testFrom() {
        Position p1 = new Position();
        
        assertEquals( p1.x, 0 );
        assertEquals( p1.y, 0 );
        
        p1.fromConfigString( "100,40" );
        
        assertEquals( p1.x, 100 );
        assertEquals( p1.y, 40 );
        
        try {
            p1.fromConfigString( "hey" );
            fail( "this should not work and throw an exception!" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "The stringValue as invalid format: hey", iae.getMessage() );
        }
        
        Position p2 = new Position( 1, 1 );
        
        p1.setFrom( p2 );
        
        assertEquals( p1.x, 1 );
        assertEquals( p1.y, 1 );
    }
    
    @Test
    public void testToString() {
        Position p1 = new Position( 30, 40 );
        
        assertEquals( p1.x, 30 );
        assertEquals( p1.y, 40 );
        
        assertEquals( "[x=30,y=40]", p1.toString() );
    }
    
    @Test
    public void testEquality() {
        Position p1 = new Position( 30, 40 );
        Position p2 = new Position( 30, 40 );
        
        assertEquals( p1, p1 );
        assertEquals( p1, p2 );
        assertEquals( p2, p1 );
        assertEquals( p2, p2 );
        
        assertTrue( p1.equals( p2 ) );
        assertTrue( p2.equals( p1 ) );
        
        Position p3 = new Position( 40, 30 );
        
        assertFalse( p1.equals( p3 ) );
        assertFalse( p3.equals( p1 ) );
        assertFalse( p2.equals( p3 ) );
        assertFalse( p3.equals( p2 ) );
    }

}
