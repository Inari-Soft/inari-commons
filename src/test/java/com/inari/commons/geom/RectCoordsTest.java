package com.inari.commons.geom;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.inari.commons.StringUtils;

public class RectCoordsTest {
    
    @Test
    public void test() {
        RectCoords coords = new RectCoords();
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:0.0  p0y:0.0\n" + 
            "  p1x:0.0  p1y:0.0\n" + 
            "  p2x:0.0  p2y:0.0\n" + 
            "  p3x:0.0  p3y:0.0\n" + 
            "]\n", 
            coords.toString() 
        );
        
        coords = new RectCoords( new Rectangle( 4, 5, 50, 60 ) );
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:4.0  p0y:5.0\n" + 
            "  p1x:54.0  p1y:5.0\n" + 
            "  p2x:54.0  p2y:65.0\n" + 
            "  p3x:4.0  p3y:65.0\n" + 
            "]\n", 
            coords.toString() 
        );
        
        coords = new RectCoords( 0.1f, 0.1f, 1f, 0.5f );
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:0.1  p0y:0.1\n" + 
            "  p1x:1.1  p1y:0.1\n" + 
            "  p2x:1.1  p2y:0.6\n" + 
            "  p3x:0.1  p3y:0.6\n" + 
            "]\n", 
            coords.toString() 
        );
    }
    
    @Test
    public void testFromToString() {
        String configString = 
                "10.1" + StringUtils.VALUE_SEPARATOR_STRING + 
                "10.1" + StringUtils.VALUE_SEPARATOR_STRING + 
                "40.1" + StringUtils.VALUE_SEPARATOR_STRING + 
                "50.1";
        
        RectCoords coords = new RectCoords();
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:0.0  p0y:0.0\n" + 
            "  p1x:0.0  p1y:0.0\n" + 
            "  p2x:0.0  p2y:0.0\n" + 
            "  p3x:0.0  p3y:0.0\n" + 
            "]\n", 
            coords.toString() 
        );
        
        coords.fromConfigString( configString );
        assertEquals( 
            "QuadCoords[\n" + 
            "  p0x:10.1  p0y:10.1\n" + 
            "  p1x:50.199997  p1y:10.1\n" + 
            "  p2x:50.199997  p2y:60.199997\n" + 
            "  p3x:10.1  p3y:60.199997\n" + 
            "]\n", 
            coords.toString() 
        );
        
        assertEquals( configString, coords.toConfigString() );
    }

    @Test
    public void testIntersection() {
        RectCoords coords1 = new RectCoords( new Rectangle( 0, 0, 10, 10 ) );
        RectCoords coords2 = new RectCoords( new Rectangle( 0, 0, 10, 10 ) );

        assertTrue( coords1.intersects( coords2 ) );

        coords2 = new RectCoords( new Rectangle( -9, 0, 10, 10 ) );
        assertTrue( coords1.intersects( coords2 ) );
        coords2 = new RectCoords( new Rectangle( -10, 0, 10, 10 ) );
        assertFalse( coords1.intersects( coords2 ) );
    }

}
