package com.inari.commons.geom;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class QuadCoordsTest {
    
    @Test
    public void test() {
        QuadCoords coords = new QuadCoords();
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:0.0  p0y:0.0\n" + 
            "  p1x:0.0  p1y:0.0\n" + 
            "  p2x:0.0  p2y:0.0\n" + 
            "  p3x:0.0  p3y:0.0\n" + 
            "]\n", 
            coords.toString() 
        );
        
        coords = new QuadCoords( new Rectangle( 4, 5, 50, 60 ) );
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:4.0  p0y:5.0\n" + 
            "  p1x:54.0  p1y:5.0\n" + 
            "  p2x:54.0  p2y:65.0\n" + 
            "  p3x:4.0  p3y:65.0\n" + 
            "]\n", 
            coords.toString() 
        );
        
        coords = new QuadCoords( 0.1f, 0.1f, 1f, 0.5f );
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:0.1  p0y:0.1\n" + 
            "  p1x:1.1  p1y:0.1\n" + 
            "  p2x:1.1  p2y:0.6\n" + 
            "  p3x:0.1  p3y:0.6\n" + 
            "]\n", 
            coords.toString() 
        );
        
        float[][] value = new float[][] { { 0.0f, 0.1f }, { 1.0f, 1.1f }, { 2.0f, 2.1f }, { 3.0f, 3.1f } };
        coords = new QuadCoords( value );
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:0.0  p0y:0.1\n" + 
            "  p1x:1.0  p1y:1.1\n" + 
            "  p2x:2.0  p2y:2.1\n" + 
            "  p3x:3.0  p3y:3.1\n" + 
            "]\n", 
            coords.toString() 
        );

        coords = new QuadCoords( new Rectangle( -11, 0, 10, 10 ) );
        assertEquals(
            "QuadCoords[\n"
                + "  p0x:-11.0  p0y:0.0\n"
                + "  p1x:-1.0  p1y:0.0\n"
                + "  p2x:-1.0  p2y:10.0\n"
                + "  p3x:-11.0  p3y:10.0\n"
                + "]\n",
            coords.toString()
        );
    }
    
    @Test
    public void testWidthHeight() {
        QuadCoords coords = new QuadCoords( new Rectangle( 4, 5, 50, 60 ) );
        assertEquals( "50.0", String.valueOf( coords.width() ) );
        assertEquals( "60.0", String.valueOf( coords.height() ) );
    }

    @Test
    public void testVertex() {
        QuadCoords coords1 = new QuadCoords( new Rectangle( 2, 3, 10, 20 ) );

        assertEquals( "2.0", String.valueOf( coords1.getLeftX() ) );
        assertEquals( "12.0", String.valueOf( coords1.getRightX() ) );
        assertEquals( "3.0", String.valueOf( coords1.getTopY() ) );
        assertEquals( "23.0", String.valueOf( coords1.getBottomY() ) );

        coords1 = new QuadCoords( new Rectangle( -2, -3, 10, 20 ) );

        assertEquals( "-2.0", String.valueOf( coords1.getLeftX() ) );
        assertEquals( "8.0", String.valueOf( coords1.getRightX() ) );
        assertEquals( "-3.0", String.valueOf( coords1.getTopY() ) );
        assertEquals( "17.0", String.valueOf( coords1.getBottomY() ) );
    }

    @Test
    public void testIntersection() {
        QuadCoords coords1 = new QuadCoords( new Rectangle( 0, 0, 10, 10 ) );
        QuadCoords coords2 = new QuadCoords( new Rectangle( 0, 0, 10, 10 ) );

        assertTrue( coords1.intersects( coords2 ) );

        coords2 = new QuadCoords( new Rectangle( -9, 0, 10, 10 ) );
        assertTrue( coords1.intersects( coords2 ) );
        coords2 = new QuadCoords( new Rectangle( -10, 0, 10, 10 ) );
        assertFalse( coords1.intersects( coords2 ) );
        coords2 = new QuadCoords( new Rectangle( 9, 0, 10, 10 ) );
        assertTrue( coords1.intersects( coords2 ) );
        coords2 = new QuadCoords( new Rectangle( 10, 0, 10, 10 ) );
        assertFalse( coords1.intersects( coords2 ) );

        coords2 = new QuadCoords( new Rectangle( 0, -9, 10, 10 ) );
        assertTrue( coords1.intersects( coords2 ) );
        coords2 = new QuadCoords( new Rectangle( 0, -10, 10, 10 ) );
        assertFalse( coords1.intersects( coords2 ) );
        coords2 = new QuadCoords( new Rectangle( 0, 9, 10, 10 ) );
        assertTrue( coords1.intersects( coords2 ) );
        coords2 = new QuadCoords( new Rectangle( 0, 10, 10, 10 ) );
        assertFalse( coords1.intersects( coords2 ) );

    }

}
