package com.inari.commons.geom;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PoisitionPathTest {

    @Test
    public void testCreation() {
        PositionPath ppath = new PositionPath();

        assertEquals( "PositionPath{positionPath=[]}", ppath.toString() );
    }

    @Test
    public void testAddRemove() {
        PositionPath ppath = new PositionPath();

        ppath.add( new Position( 1, 1 ) );
        ppath.add( new Position( 2, 1 ) );
        ppath.add( new Position( 1, 31 ) );

        assertEquals( "PositionPath{positionPath=[[x=1,y=1], [x=2,y=1], [x=1,y=31]]}", ppath.toString() );

        assertTrue( ppath.remove( 0 ) );

        assertEquals( "PositionPath{positionPath=[[x=2,y=1], [x=1,y=31]]}", ppath.toString() );

        assertFalse( ppath.remove( 100 ) );
        assertFalse( ppath.remove( -1 ) );

        ppath.add( -1 , new Position( 0, 0 ) );
        assertEquals( "PositionPath{positionPath=[[x=0,y=0], [x=2,y=1], [x=1,y=31]]}", ppath.toString() );
        ppath.add( 100 , new Position( 100, 0 ) );
        assertEquals( "PositionPath{positionPath=[[x=0,y=0], [x=2,y=1], [x=1,y=31], [x=100,y=0]]}", ppath.toString() );
    }

    @Test
    public void testFromToConfigString() {
        PositionPath ppath = new PositionPath();

        ppath.add( new Position( 1, 1 ) );
        ppath.add( new Position( 2, 1 ) );
        ppath.add( new Position( 1, 31 ) );

        String configString = ppath.toConfigString();
        assertEquals( "1,1|2,1|1,31", configString );

        PositionPath ppath2 = new PositionPath();
        ppath2.fromConfigString( configString );

        assertEquals( "PositionPath{positionPath=[[x=1,y=1], [x=2,y=1], [x=1,y=31]]}", ppath2.toString() );
    }
}
