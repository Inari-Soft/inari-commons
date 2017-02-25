package com.inari.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.inari.commons.geom.Direction;
import com.inari.commons.geom.Orientation;
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
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 101, 0, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, 100, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, 101, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, 0, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, -1, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 0, -1, 0, 1 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( -1, 0, 0, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( -1, 0, 1, 0 );
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        
        r2 = new Rectangle( 100, 100, 2, 2 );
        
        assertFalse( GeomUtils.intersect( r1, r2 ) );
        assertFalse( GeomUtils.intersect( r2, r1 ) );
    }
    
    @Test
    public void testGetIntersectionCode() {
        Rectangle r1 = new Rectangle( 0, 0, 100, 100 );
        Rectangle r2 = new Rectangle( 50, 50, 100, 100 );
        
        assertEquals( "17", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        assertEquals( "12", String.valueOf( GeomUtils.getIntersectionCode( r2, r1 ) ) );
        
        r2 = new Rectangle( 100, 100, 100, 100 );
        assertEquals( "0", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        r2 = new Rectangle( 99, 99, 100, 100 );
        assertEquals( "17", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        r2 = new Rectangle( 100, 99, 100, 100 );
        assertEquals( "0", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        r2 = new Rectangle( 50, 50, 100, 10 );
        assertEquals( "25", String.valueOf( GeomUtils.getIntersectionCode( r1, r2 ) ) );
        
        assertEquals( "4", String.valueOf( GeomUtils.getIntersectionCode( r2, r1 ) ) );
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
    
    @Test
    public void testIntersection2() {
        Rectangle r1 = new Rectangle( 0, 0, 8, 8 );
        Rectangle r2 = new Rectangle( 0, 0, 8, 8 );
        
        Rectangle intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=0,y=0,width=8,height=8]", intersection.toString() );
        
        r2.x++;
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=1,y=0,width=7,height=8]", intersection.toString() );
        
        r2.x = 7;
        intersection = GeomUtils.intersection( r1, r2 );
        assertEquals( "[x=7,y=0,width=1,height=8]", intersection.toString() );
    }
    
    @Test
    public void testIntersectionAA() {
        assertEquals( "0", String.valueOf( GeomUtils.intersection( 0, 10, 20, 10 ) ) );
        assertEquals( "0", String.valueOf( GeomUtils.intersection( 100, 10, 20, 10 ) ) );
        
        assertEquals( "10", String.valueOf( GeomUtils.intersection( 0, 100, 20, 10 ) ) );
        assertEquals( "10", String.valueOf( GeomUtils.intersection( 20, 10, 0, 100 ) ) );
        
        assertEquals( "20", String.valueOf( GeomUtils.intersection( 10, 100, 0, 30 ) ) );
        assertEquals( "10", String.valueOf( GeomUtils.intersection( 0, 10, -10, 30 ) ) );
        assertEquals( "5", String.valueOf( GeomUtils.intersection( 0, 10, -10, 15 ) ) );
    }
    
    @Test
    public void testIntersectionWithResult() {
        Rectangle r1 = new Rectangle( 0,0,10,10 );
        Rectangle r2 = new Rectangle( 10,0,10,10 );
        Rectangle r3 = new Rectangle( 20,0,10,10 );
        
        Rectangle intersection = new Rectangle();
        
        GeomUtils.intersection( r1, r2, intersection );
        assertEquals( "[x=10,y=0,width=0,height=10]", intersection.toString() );
        GeomUtils.intersection( r2, r1, intersection );
        assertEquals( "[x=10,y=0,width=0,height=10]", intersection.toString() );
        GeomUtils.intersection( r2, r3, intersection );
        assertEquals( "[x=20,y=0,width=0,height=10]", intersection.toString() );
        
        
    }
    
    @Test
    public void testContains() {
        Rectangle r1 = new Rectangle( 1, 1, 10, 10 );
        
        assertTrue( GeomUtils.contains( r1, 1, 1 ) );
        assertTrue( GeomUtils.contains( r1, 5, 5 ) );
        assertTrue( GeomUtils.contains( r1, 1, 10 ) );
        assertTrue( GeomUtils.contains( r1, 10, 1 ) );
        
        assertFalse( GeomUtils.contains( r1, 0, 0 ) );
        assertFalse( GeomUtils.contains( r1, 0, 1 ) );
        assertFalse( GeomUtils.contains( r1, 1, 0 ) );
        assertFalse( GeomUtils.contains( r1, 1, 11 ) );
        assertFalse( GeomUtils.contains( r1, 11, 1 ) );
        
    }

    @Test
    public void movePositionTest() {
        Position p = new Position( 0, 0 );

        assertEquals( "[x=0,y=0]", p.toString() );

        GeomUtils.movePosition( p, Orientation.EAST );
        assertEquals( "[x=1,y=0]", p.toString() );
        GeomUtils.movePosition( p, Orientation.SOUTH );
        assertEquals( "[x=1,y=1]", p.toString() );
        GeomUtils.movePosition( p, Orientation.WEST );
        assertEquals( "[x=0,y=1]", p.toString() );
        GeomUtils.movePosition( p, Orientation.NORTH );
        assertEquals( "[x=0,y=0]", p.toString() );

        GeomUtils.movePosition( p, Orientation.EAST, 1 );
        assertEquals( "[x=1,y=0]", p.toString() );
        GeomUtils.movePosition( p, Orientation.SOUTH, 2 );
        assertEquals( "[x=1,y=2]", p.toString() );
        GeomUtils.movePosition( p, Orientation.WEST, 3 );
        assertEquals( "[x=-2,y=2]", p.toString() );
        GeomUtils.movePosition( p, Orientation.NORTH, 4 );
        assertEquals( "[x=-2,y=-2]", p.toString() );

        p.x = 0;
        p.y = 0;
        GeomUtils.movePosition( p, Orientation.EAST,1, false );
        assertEquals( "[x=1,y=0]", p.toString() );
        GeomUtils.movePosition( p, Orientation.SOUTH,1, false );
        assertEquals( "[x=1,y=-1]", p.toString() );
        GeomUtils.movePosition( p, Orientation.WEST,1, false );
        assertEquals( "[x=0,y=-1]", p.toString() );
        GeomUtils.movePosition( p, Orientation.NORTH,1, false );
        assertEquals( "[x=0,y=0]", p.toString() );

        GeomUtils.movePosition( p, Direction.NORTH_EAST, 1, true );
        assertEquals( "[x=1,y=-1]", p.toString() );

    }

}
