package com.inari.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;

public class GeomUtilsTest {
    
    @Test
    public void testDistance() {
        Position p1 = new Position( 0, 0 );
        
        Position p2 = new Position( 1, 0 );
        assertEquals( "1.0", String.valueOf( GeomUtils.getDistance( p1, p2 ) ) );
        assertTrue( 1.0f == GeomUtils.getDistance( p1, p2 ) );
        
        p2 = new Position( 0, 1 );
        assertEquals( "1.0", String.valueOf( GeomUtils.getDistance( p1, p2 ) ) );
        assertTrue( 1.0f == GeomUtils.getDistance( p1, p2 ) );
        
        p2 = new Position( 1, 1 );
        assertEquals( "1.4142135", String.valueOf( GeomUtils.getDistance( p1, p2 ) ) );
        assertTrue( 1.4142135f == GeomUtils.getDistance( p1, p2 ) );
        
        p2 = new Position( 2, 1 );
        assertEquals( "2.236068", String.valueOf( GeomUtils.getDistance( p1, p2 ) ) );
        assertTrue( 2.236068f == GeomUtils.getDistance( p1, p2 ) );
        
        p2 = new Position( 2, 2 );
        assertEquals( "2.828427", String.valueOf( GeomUtils.getDistance( p1, p2 ) ) );
        assertTrue( 2.828427f == GeomUtils.getDistance( p1, p2 ) );

        p2 = new Position( 4, 2 );
        assertEquals( "4.472136", String.valueOf( GeomUtils.getDistance( p1, p2 ) ) );
        assertTrue( 4.472136f == GeomUtils.getDistance( p1, p2 ) );
    }
    
    @Test
    public void testIntersect() {
        Rectangle r1 = new Rectangle( 0, 0, 100, 100 );
        
        Rectangle r2 = new Rectangle( 0, 0, 100, 100 );
        assertTrue( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 100, 0, 0, 0 );
        assertTrue( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 101, 0, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, 100, 0, 0 );
        assertTrue( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, 101, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, 0, 0, 0 );
        assertTrue( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, -1, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, -1, 0, 1 );
        assertTrue( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( -1, 0, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( -1, 0, 1, 0 );
        assertTrue( GeomUtils.intersect( r1, r2 ) );
    }
    
    @Test
    public void testGetIntersectionCode() {
        Rectangle r1 = new Rectangle( 0, 0, 100, 100 );
        Rectangle r2 = new Rectangle( 50, 50, 100, 100 );
        
        assertEquals( "17", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        assertTrue( ( 17 & GeomUtils.LEFT_SIDE ) > 0 );
        assertTrue( ( 17 & GeomUtils.TOP_SIDE ) > 0 );
        assertEquals( "12", String.valueOf( GeomUtils.getIntersectionCode( r2, r1 ) ) );
        assertTrue( ( 12 & GeomUtils.RIGHT_SIDE ) > 0 );
        assertTrue( ( 12 & GeomUtils.BOTTOM_SIDE ) > 0 );
        
        r2 = new Rectangle( 100, 100, 100, 100 );
        assertEquals( "0", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        r2 = new Rectangle( 99, 99, 100, 100 );
        assertEquals( "17", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        r2 = new Rectangle( 100, 99, 100, 100 );
        assertEquals( "0", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        r2 = new Rectangle( 50, 50, 100, 10 );
        assertEquals( "25", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        assertTrue( ( 25 & GeomUtils.LEFT_SIDE ) > 0 );
        assertTrue( ( 25 & GeomUtils.TOP_SIDE ) > 0 );
        assertTrue( ( 25 & GeomUtils.BOTTOM_SIDE ) > 0 );
        
        assertEquals( "4", String.valueOf( GeomUtils.getIntersectionCode( r2, r1 ) ) );
        assertTrue( ( 4 & GeomUtils.RIGHT_SIDE ) > 0 );
    }
    
    @Test
    public void testIntersection() {
        Rectangle r1 = new Rectangle( 0, 0, 100, 100 );
        Rectangle r2 = new Rectangle( 50, 50, 100, 100 );
        
        Rectangle intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=50,y=50,width=50,height=50]", intersection.toString() );
        intersection = GeomUtils.intersection( r2, r1 );
        assertEquals( "[x=50,y=50,width=50,height=50]", intersection.toString() );
        assertTrue( intersection.area() > 0 );
        
        r2 = new Rectangle( 100, 100, 100, 100 );
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=100,y=100,width=0,height=0]", intersection.toString() );
        assertFalse( intersection.area() > 0 );
        
        r2 = new Rectangle( 101, 100, 100, 100 );
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=101,y=100,width=0,height=0]", intersection.toString() );
        assertFalse( intersection.area() > 0 );
        
        r2 = new Rectangle( 101, 101, 100, 100 );
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=101,y=101,width=0,height=0]", intersection.toString() );
        assertFalse( intersection.area() > 0 );
        
        r2 = new Rectangle( 99, 101, 100, 100 );
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=99,y=101,width=1,height=0]", intersection.toString() );
        assertFalse( intersection.area() > 0 );
        
        r2 = new Rectangle( 99, 99, 100, 100 );
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=99,y=99,width=1,height=1]", intersection.toString() );
        assertTrue( intersection.area() > 0 );
        
        r2 = new Rectangle( -10, -10, 50, 50 );
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=0,y=0,width=40,height=40]", intersection.toString() );
        assertTrue( intersection.area() > 0 );
        
    }

}
