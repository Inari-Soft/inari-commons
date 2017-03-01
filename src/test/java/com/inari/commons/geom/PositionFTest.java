package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PositionFTest {

    @Test
    public void testPoint() {
        PositionF p1 = new PositionF();

        assertTrue( p1.x == 0f );
        assertTrue( p1.y == 0f );

        p1 = new PositionF( 10, 4 );

        assertTrue( p1.x == 10 );
        assertTrue( p1.y == 4 );

        PositionF p2 = new PositionF( p1 );

        assertTrue( p2.x == 10 );
        assertTrue( p2.y == 4 );

        PositionF p3 = new PositionF( "40,100" );

        assertTrue( p3.x == 40 );
        assertTrue( p3.y == 100 );

        try {
            p3 = new PositionF( "40.5,100" );
            fail( "this should not work and throw an exception!" );
        } catch ( NumberFormatException nfe ) {
            assertEquals( "For input string: \"40.5\"", nfe.getMessage() );
        }
    }

    @Test
    public void testFrom() {
        PositionF p1 = new PositionF();

        assertTrue( p1.x == 0 );
        assertTrue( p1.y == 0 );

        p1.fromConfigString( "100,40" );

        assertTrue( p1.x == 100 );
        assertTrue( p1.y == 40 );

        try {
            p1.fromConfigString( "hey" );
            fail( "this should not work and throw an exception!" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "The stringValue as invalid format: hey", iae.getMessage() );
        }

        PositionF p2 = new PositionF( 1, 1 );

        p1.setFrom( p2 );

        assertTrue( p1.x == 1 );
        assertTrue( p1.y == 1 );
    }

    @Test
    public void testToString() {
        PositionF p1 = new PositionF( 30, 40 );

        assertTrue( p1.x == 30 );
        assertTrue( p1.y == 40 );

        assertEquals( "[x=30.0,y=40.0]", p1.toString() );
    }

    @Test
    public void testEquality() {
        PositionF p1 = new PositionF( 30, 40 );
        PositionF p2 = new PositionF( 30, 40 );

        assertEquals( p1, p1 );
        assertEquals( p1, p2 );
        assertEquals( p2, p1 );
        assertEquals( p2, p2 );

        assertTrue( p1.equals( p2 ) );
        assertTrue( p2.equals( p1 ) );

        PositionF p3 = new PositionF( 40, 30 );

        assertFalse( p1.equals( p3 ) );
        assertFalse( p3.equals( p1 ) );
        assertFalse( p2.equals( p3 ) );
        assertFalse( p3.equals( p2 ) );
    }
}
