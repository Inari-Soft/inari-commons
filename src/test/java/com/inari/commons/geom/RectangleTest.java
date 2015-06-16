package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RectangleTest {

    @Test
    public void testRectangle() {
        Rectangle r1 = new Rectangle();
        
        assertEquals( r1.x, 0 );
        assertEquals( r1.y, 0 );
        assertEquals( r1.width, 0 );
        assertEquals( r1.height, 0 );
        
        r1 = new Rectangle( 10, 10, 100, 200 );
        
        assertEquals( r1.x, 10 );
        assertEquals( r1.y, 10 );
        assertEquals( r1.width, 100 );
        assertEquals( r1.height, 200 );
        
        Rectangle r2 = new Rectangle( r1 );
        
        assertEquals( r2.x, 10 );
        assertEquals( r2.y, 10 );
        assertEquals( r2.width, 100 );
        assertEquals( r2.height, 200 );
        
        Rectangle r3 = new Rectangle( "20,20,300,400" );
        
        assertEquals( r3.x, 20 );
        assertEquals( r3.y, 20 );
        assertEquals( r3.width, 300 );
        assertEquals( r3.height, 400 );
    }
    
    @Test
    public void testFrom() {
        Rectangle r1 = new Rectangle();
        r1.fromConfigString( "1,1,1,1" );
        
        assertEquals( r1.x, 1 );
        assertEquals( r1.y, 1 );
        assertEquals( r1.width, 1 );
        assertEquals( r1.height, 1 );
        
        Rectangle r2 = new Rectangle();
        r2.setFrom( r1 );
        
        assertEquals( r1.x, 1 );
        assertEquals( r1.y, 1 );
        assertEquals( r1.width, 1 );
        assertEquals( r1.height, 1 );
    }
    
    @Test
    public void testToString() {
        Rectangle r1 = new Rectangle( 30, 20, 111, 444 );
        
        assertEquals( r1.x, 30 );
        assertEquals( r1.y, 20 );
        assertEquals( r1.width, 111 );
        assertEquals( r1.height, 444 );
        
        assertEquals( "Rectangle: 30,20,111,444", r1.toString() );
    }
    
    public void testEquality() {
        Rectangle r1 = new Rectangle( 1, 1, 111, 111 );
        Rectangle r2 = new Rectangle( 1, 1, 111, 111 );
        
        assertTrue( r1.equals( r2 ) );
        assertTrue( r2.equals( r1 ) );
        
        Rectangle r3 = new Rectangle( 2, 1, 111, 111 );
        
        assertFalse( r1.equals( r3 ) );
        assertFalse( r3.equals( r1 ) );
        assertFalse( r2.equals( r3 ) );
        assertFalse( r3.equals( r2 ) );
    }
}
