package com.inari.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.inari.commons.geom.Direction;
import com.inari.commons.geom.Orientation;
import org.junit.Test;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.geom.Vector2i;

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
        assertTrue( GeomUtils.contains( r1, new Position( 10, 1 ) ) );
        
        assertFalse( GeomUtils.contains( r1, 0, 0 ) );
        assertFalse( GeomUtils.contains( r1, 0, 1 ) );
        assertFalse( GeomUtils.contains( r1, 1, 0 ) );
        assertFalse( GeomUtils.contains( r1, 1, 11 ) );
        assertFalse( GeomUtils.contains( r1, new Position( 11, 1 ) ) );

        assertTrue( GeomUtils.contains( r1, new Rectangle( 2, 2, 2, 2 ) ) );
        assertFalse( GeomUtils.contains( r1, new Rectangle( 0, 0, 2, 2 ) ) );
        assertFalse( GeomUtils.contains( r1, new Rectangle( 2, 2, 10, 2 ) ) );
        assertTrue( GeomUtils.contains( r1, new Rectangle( 1, 1, 10, 10 ) ) );
        assertTrue( GeomUtils.contains( r1, new Rectangle( 1, 1, 9, 10 ) ) );
        assertTrue( GeomUtils.contains( r1, new Rectangle( 1, 1, 10, 9 ) ) );
        assertTrue( GeomUtils.contains( r1, new Rectangle( 1, 1, 9, 9 ) ) );

        assertFalse( GeomUtils.contains( r1, new Rectangle( 1, 1, 11, 10 ) ) );
        assertFalse( GeomUtils.contains( r1, new Rectangle( 1, 1, 10, 11 ) ) );
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

    @Test
    public void testUnion() {
        Rectangle r1 = new Rectangle( 0, 0, 1, 1 );
        Rectangle r2 = new Rectangle( 9, 9, 1, 1 );

        Rectangle union = GeomUtils.union( r1, r2 );
        assertEquals( "[x=0,y=0,width=10,height=10]", String.valueOf( union ) );

        r1 = new Rectangle( -10, -10, 1, 1 );
        union = GeomUtils.union( r1, r2 );
        assertEquals( "[x=-10,y=-10,width=20,height=20]", String.valueOf( union ) );
    }

    @Test
    public void testGetBoundary() {
        Rectangle r1 = new Rectangle( -10, -10, 15, 16 );

        assertEquals( "-10", String.valueOf( GeomUtils.getBoundary( r1, GeomUtils.LEFT_SIDE ) ) );
        assertEquals( "5", String.valueOf( GeomUtils.getBoundary( r1, GeomUtils.BOTTOM_SIDE ) ) );
        assertEquals( "4", String.valueOf( GeomUtils.getBoundary( r1, GeomUtils.RIGHT_SIDE ) ) );
        assertEquals( "-10", String.valueOf( GeomUtils.getBoundary( r1, GeomUtils.TOP_SIDE ) ) );
    }

    @Test
    public void testgetOppositeSide() {
        assertTrue( GeomUtils.RIGHT_SIDE == GeomUtils.getOppositeSide( GeomUtils.LEFT_SIDE ) );
        assertTrue( GeomUtils.BOTTOM_SIDE == GeomUtils.getOppositeSide( GeomUtils.TOP_SIDE ) );
        assertTrue( GeomUtils.TOP_SIDE == GeomUtils.getOppositeSide( GeomUtils.BOTTOM_SIDE ) );
        assertTrue( GeomUtils.LEFT_SIDE == GeomUtils.getOppositeSide( GeomUtils.RIGHT_SIDE ) );
    }

    @Test
    public void testsetOutsideBoundary() {
        Rectangle r1 = new Rectangle( 0, 0, 10, 10 );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.RIGHT_SIDE, 15 );
        assertEquals( "[x=0,y=0,width=15,height=10]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.BOTTOM_SIDE, 15 );
        assertEquals( "[x=0,y=0,width=15,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.BOTTOM_SIDE, 15 );
        assertEquals( "[x=0,y=0,width=15,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.LEFT_SIDE, 0 );
        assertEquals( "[x=0,y=0,width=15,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.LEFT_SIDE, 1 );
        assertEquals( "[x=1,y=0,width=14,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.LEFT_SIDE, -1 );
        assertEquals( "[x=-1,y=0,width=16,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.LEFT_SIDE, 0 );
        assertEquals( "[x=0,y=0,width=15,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.TOP_SIDE, 0 );
        assertEquals( "[x=0,y=0,width=15,height=15]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.TOP_SIDE, 1 );
        assertEquals( "[x=0,y=1,width=15,height=14]", String.valueOf( r1 ) );

        GeomUtils.setOutsideBoundary( r1, GeomUtils.TOP_SIDE, -1 );
        assertEquals( "[x=0,y=-1,width=15,height=16]", String.valueOf( r1 ) );
    }

    @Test
    public void testrotateLeft() {
        assertTrue( Direction.NORTH_EAST == GeomUtils.rotateLeft( Direction.NORTH ) );
        assertTrue( Direction.EAST == GeomUtils.rotateLeft( Direction.NORTH_EAST ) );
        assertTrue( Direction.SOUTH_EAST == GeomUtils.rotateLeft( Direction.EAST ) );
        assertTrue( Direction.SOUTH == GeomUtils.rotateLeft( Direction.SOUTH_EAST ) );
        assertTrue( Direction.SOUTH_WEST == GeomUtils.rotateLeft( Direction.SOUTH ) );
        assertTrue( Direction.WEST == GeomUtils.rotateLeft( Direction.SOUTH_WEST ) );
        assertTrue( Direction.NORTH_WEST == GeomUtils.rotateLeft( Direction.WEST ) );
        assertTrue( Direction.NORTH == GeomUtils.rotateLeft( Direction.NORTH_WEST ) );
        assertTrue( Direction.NONE == GeomUtils.rotateLeft( Direction.NONE ) );
    }

    @Test
    public void testrotateRight() {
        assertTrue( Direction.NORTH_WEST == GeomUtils.rotateRight( Direction.NORTH ) );
        assertTrue( Direction.WEST == GeomUtils.rotateRight( Direction.NORTH_WEST ) );
        assertTrue( Direction.SOUTH_WEST == GeomUtils.rotateRight( Direction.WEST ) );
        assertTrue( Direction.SOUTH == GeomUtils.rotateRight( Direction.SOUTH_WEST ) );
        assertTrue( Direction.SOUTH_EAST == GeomUtils.rotateRight( Direction.SOUTH ) );
        assertTrue( Direction.EAST == GeomUtils.rotateRight( Direction.SOUTH_EAST ) );
        assertTrue( Direction.NORTH_EAST == GeomUtils.rotateRight( Direction.EAST ) );
        assertTrue( Direction.NORTH == GeomUtils.rotateRight( Direction.NORTH_EAST ) );
        assertTrue( Direction.NONE == GeomUtils.rotateRight( Direction.NONE ) );
    }

    @Test
    public void testrotateLeft2() {
        assertTrue( Direction.EAST == GeomUtils.rotateLeft2( Direction.NORTH ) );
        assertTrue( Direction.SOUTH_EAST == GeomUtils.rotateLeft2( Direction.NORTH_EAST ) );
        assertTrue( Direction.SOUTH == GeomUtils.rotateLeft2( Direction.EAST ) );
        assertTrue( Direction.SOUTH_WEST == GeomUtils.rotateLeft2( Direction.SOUTH_EAST ) );
        assertTrue( Direction.WEST == GeomUtils.rotateLeft2( Direction.SOUTH ) );
        assertTrue( Direction.NORTH_WEST == GeomUtils.rotateLeft2( Direction.SOUTH_WEST ) );
        assertTrue( Direction.NORTH == GeomUtils.rotateLeft2( Direction.WEST ) );
        assertTrue( Direction.NORTH_EAST == GeomUtils.rotateLeft2( Direction.NORTH_WEST ) );
        assertTrue( Direction.NONE == GeomUtils.rotateLeft2( Direction.NONE ) );
    }

    @Test
    public void testrotateRight2() {
        assertTrue( Direction.WEST == GeomUtils.rotateRight2( Direction.NORTH ) );
        assertTrue( Direction.SOUTH_WEST == GeomUtils.rotateRight2( Direction.NORTH_WEST ) );
        assertTrue( Direction.SOUTH == GeomUtils.rotateRight2( Direction.WEST ) );
        assertTrue( Direction.SOUTH_EAST == GeomUtils.rotateRight2( Direction.SOUTH_WEST ) );
        assertTrue( Direction.EAST == GeomUtils.rotateRight2( Direction.SOUTH ) );
        assertTrue( Direction.NORTH_EAST == GeomUtils.rotateRight2( Direction.SOUTH_EAST ) );
        assertTrue( Direction.NORTH == GeomUtils.rotateRight2( Direction.EAST ) );
        assertTrue( Direction.NORTH_WEST == GeomUtils.rotateRight2( Direction.NORTH_EAST ) );
        assertTrue( Direction.NONE == GeomUtils.rotateRight2( Direction.NONE ) );
    }

    @Test
    public void testisVertical() {
        assertTrue( GeomUtils.isVertical( Direction.NORTH ) );
        assertTrue( GeomUtils.isVertical( Direction.SOUTH ) );
        assertFalse( GeomUtils.isVertical( Direction.EAST ) );
        assertFalse( GeomUtils.isVertical( Direction.WEST ) );
        assertFalse( GeomUtils.isVertical( Direction.NORTH_EAST ) );
        assertFalse( GeomUtils.isVertical( Direction.NORTH_WEST ) );
        assertFalse( GeomUtils.isVertical( Direction.SOUTH_EAST ) );
        assertFalse( GeomUtils.isVertical( Direction.SOUTH_WEST ) );
        assertFalse( GeomUtils.isVertical( Direction.NONE ) );
    }

    @Test
    public void testisHorizontal() {
        assertFalse( GeomUtils.isHorizontal( Direction.NORTH ) );
        assertFalse( GeomUtils.isHorizontal( Direction.SOUTH ) );
        assertTrue( GeomUtils.isHorizontal( Direction.EAST ) );
        assertTrue( GeomUtils.isHorizontal( Direction.WEST ) );
        assertFalse( GeomUtils.isHorizontal( Direction.NORTH_EAST ) );
        assertFalse( GeomUtils.isHorizontal( Direction.NORTH_WEST ) );
        assertFalse( GeomUtils.isHorizontal( Direction.SOUTH_EAST ) );
        assertFalse( GeomUtils.isHorizontal( Direction.SOUTH_WEST ) );
        assertFalse( GeomUtils.isHorizontal( Direction.NONE ) );
    }

    @Test
    public void testtranslateTo() {
        Position p1 = new Position( 0, 0 );

        GeomUtils.translateTo( p1, new Position( 10, -67 ) );
        assertEquals( "[x=10,y=-67]", String.valueOf( p1 ) );
    }

    @Test
    public void testtranslate() {
        Position p1 = new Position( 10, 10 );

        GeomUtils.translate( p1, new Vector2i( 4, 6 ) );
        assertEquals( "[x=14,y=16]", String.valueOf( p1 ) );
    }

    @Test
    public void testtranslatedPos() {
        Position p1 = new Position( 10, 5 );
        assertTrue( 9 == GeomUtils.getTranslatedXPos( p1, Direction.WEST ) );
        assertTrue( 9 == GeomUtils.getTranslatedXPos( p1, Direction.SOUTH_WEST ) );
        assertTrue( 9 == GeomUtils.getTranslatedXPos( p1, Direction.NORTH_WEST ) );

        assertTrue( 11 == GeomUtils.getTranslatedXPos( p1, Direction.EAST ) );
        assertTrue( 11 == GeomUtils.getTranslatedXPos( p1, Direction.SOUTH_EAST ) );
        assertTrue( 11 == GeomUtils.getTranslatedXPos( p1, Direction.NORTH_EAST ) );

        assertTrue( 4 == GeomUtils.getTranslatedYPos( p1, Direction.NORTH ) );
        assertTrue( 4 == GeomUtils.getTranslatedYPos( p1, Direction.NORTH_EAST ) );
        assertTrue( 4 == GeomUtils.getTranslatedYPos( p1, Direction.NORTH_WEST ) );

        assertTrue( 6 == GeomUtils.getTranslatedYPos( p1, Direction.SOUTH ) );
        assertTrue( 6 == GeomUtils.getTranslatedYPos( p1, Direction.SOUTH_EAST ) );
        assertTrue( 6 == GeomUtils.getTranslatedYPos( p1, Direction.SOUTH_WEST ) );

        assertTrue( 9 == GeomUtils.getTranslatedXPos( p1, Direction.WEST, 1 ) );
        assertTrue( 8 == GeomUtils.getTranslatedXPos( p1, Direction.SOUTH_WEST, 2 ) );
        assertTrue( 7 == GeomUtils.getTranslatedXPos( p1, Direction.NORTH_WEST, 3 ) );

        assertTrue( 11 == GeomUtils.getTranslatedXPos( p1, Direction.EAST, 1 ) );
        assertTrue( 12 == GeomUtils.getTranslatedXPos( p1, Direction.SOUTH_EAST, 2 ) );
        assertTrue( 13 == GeomUtils.getTranslatedXPos( p1, Direction.NORTH_EAST, 3 ) );

        assertTrue( -5 == GeomUtils.getTranslatedYPos( p1, Direction.NORTH, 10 ) );
        assertTrue( -15 == GeomUtils.getTranslatedYPos( p1, Direction.NORTH_EAST, 20 ) );
        assertTrue( -25 == GeomUtils.getTranslatedYPos( p1, Direction.NORTH_WEST, 30 ) );

        assertTrue( 15 == GeomUtils.getTranslatedYPos( p1, Direction.SOUTH, 10 ) );
        assertTrue( 25 == GeomUtils.getTranslatedYPos( p1, Direction.SOUTH_EAST, 20 ) );
        assertTrue( 35 == GeomUtils.getTranslatedYPos( p1, Direction.SOUTH_WEST, 30 ) );
    }

}
