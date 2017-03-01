package com.inari.commons.geom;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BitMaskTest {
    
    @Test
    public void testInit() {
        BitMask bitMask = new BitMask( 10, 10 );
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );
        
        bitMask = new BitMask( 10, 10, 10, 10 );
        
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );

        bitMask = new BitMask( new Rectangle( 10, 10, 10, 10 ) );

        assertEquals(
            "BitMask [region=[x=10,y=10,width=10,height=10], bits=\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000]",
            bitMask.toString()
        );

        assertTrue( bitMask.isEmpty() );
    }

    @Test
    public void testReset() {
        BitMask bitMask = new BitMask( 0, 0, 10, 10 );

        assertEquals(
            "BitMask [region=[x=0,y=0,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );

        bitMask.reset( 0, 0, 5, 5 );
        assertEquals(
            "BitMask [region=[x=0,y=0,width=5,height=5], bits=\n"
                + "00000\n"
                + "00000\n"
                + "00000\n"
                + "00000\n"
                + "00000]",
            bitMask.toString()
        );

        bitMask.reset( new Rectangle( 5, 5, 5, 5 ) );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=5,height=5], bits=\n"
                + "00000\n"
                + "00000\n"
                + "00000\n"
                + "00000\n"
                + "00000]",
            bitMask.toString()
        );
    }
    
    @Test
    public void testSetRestBit() {
        BitMask bitMask = new BitMask( 5, 5, 10, 10 );
        
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );
        
        bitMask.setBit( 7, 7 );
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000100\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );
        
        bitMask.setBit( 7, 7, true );
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0010000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000100\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );

        bitMask.resetBit( 7, 7, true );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000100\n" +
                "0000000000\n" +
                "0000000000]",
            bitMask.toString()
        );

        bitMask.setBit( 5, 5, false );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000010000\n"
                + "0000000000\n"
                + "0000000100\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );

        bitMask.resetBit( 5 * 10 + 5 );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000100\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );
    }

    @Test
    public void testGetBit() {
        BitMask bitMask = new BitMask( 5, 5, 10, 10 );
        bitMask.setBit( 2, 2 );

        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0010000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );

        assertTrue( bitMask.getBit( 2, 2 ) );
        assertFalse( bitMask.getBit( 2, 3 ) );
    }

    @Test
    public void testMoveRegion() {
        BitMask bitMask = new BitMask( 0, 0, 10, 10 );
        assertEquals(
            "BitMask [region=[x=0,y=0,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );

        bitMask.moveRegion( 5, 5 );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );

        bitMask.moveRegion( -10, -10 );
        assertEquals(
            "BitMask [region=[x=-5,y=-5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );
    }
    
    @Test
    public void testSetResetRegion() {
        BitMask bitMask = new BitMask( 5, 5, 10, 10 );
        
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );
        
        bitMask.setRegion( 7, 7, 2, 2 );
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0011000000\n" + 
            "0011000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000]", 
            bitMask.toString() 
        );
        
        bitMask.setRegion( 7, 7, 2, 2, false );
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0011000000\n" + 
            "0011000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0000000110\n" + 
            "0000000110\n" + 
            "0000000000]", 
            bitMask.toString() 
        );
        
        bitMask.setRegion( 9, 10, 20, 2, true );
        assertEquals( 
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "0000000000\n" + 
            "0011000000\n" + 
            "0011000000\n" + 
            "0000000000\n" + 
            "0000111111\n" + 
            "0000111111\n" + 
            "0000000110\n" + 
            "0000000110\n" + 
            "0000000000]", 
            bitMask.toString() 
        );

        bitMask.resetRegion( 5, 5, 20, 3 );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0011000000\n"
                + "0000000000\n"
                + "0000111111\n"
                + "0000111111\n"
                + "0000000110\n"
                + "0000000110\n"
                + "0000000000]",
            bitMask.toString()
        );

        bitMask.resetRegion( 10, 10, 10, 3, true );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0011000000\n"
                + "0000000000\n"
                + "0000100000\n"
                + "0000100000\n"
                + "0000000000\n"
                + "0000000110\n"
                + "0000000000]",
            bitMask.toString()
        );

        bitMask.resetRegion( 100, 100, 10, 3, true );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0011000000\n"
                + "0000000000\n"
                + "0000100000\n"
                + "0000100000\n"
                + "0000000000\n"
                + "0000000110\n"
                + "0000000000]",
            bitMask.toString()
        );
    }

    @Test
    public void testHashIntersection() {
        BitMask bitMask = new BitMask( 5, 5, 10, 10 );

        // no intersection with empty bitMask
        assertFalse( bitMask.hasIntersection( new Rectangle( 5, 5, 10, 10 ) ) );

        bitMask.setBit( 5, 5 );
        assertEquals(
            "BitMask [region=[x=5,y=5,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000010000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            bitMask.toString()
        );

        assertTrue( bitMask.hasIntersection( new Rectangle( 5, 5, 10, 10 ) ) );
        assertTrue( bitMask.hasIntersection( new Rectangle( 1, 1, 10, 10 ) ) );
        assertFalse( bitMask.hasIntersection( new Rectangle( 0, 1, 10, 10 ) ) );
        assertFalse( bitMask.hasIntersection( new Rectangle( 1, 0, 10, 10 ) ) );

        assertTrue( bitMask.hasIntersection( new Rectangle( 10, 10, 10, 10 ) ) );
        assertFalse( bitMask.hasIntersection( new Rectangle( 11, 10, 10, 10 ) ) );
        assertFalse( bitMask.hasIntersection( new Rectangle( 10, 11, 10, 10 ) ) );

        bitMask = new BitMask( 5, 5, 2, 2 );
        bitMask.fill();
        assertEquals(
            "BitMask [region=[x=5,y=5,width=2,height=2], bits=\n"
                + "11\n"
                + "11]",
            bitMask.toString()
        );

        // boundaries x axis
        assertFalse( bitMask.hasIntersection( new Rectangle( 4, 6, 1, 1 ) ) );
        assertTrue( bitMask.hasIntersection( new Rectangle( 5, 6, 1, 1 ) ) );
        assertTrue( bitMask.hasIntersection( new Rectangle( 6, 6, 1, 1 ) ) );
        assertFalse( bitMask.hasIntersection( new Rectangle( 7, 6, 1, 1 ) ) );

        // boundaries y axis
        assertFalse( bitMask.hasIntersection( new Rectangle( 6, 4, 1, 1 ) ) );
        assertTrue( bitMask.hasIntersection( new Rectangle( 6, 5, 1, 1 ) ) );
        assertTrue( bitMask.hasIntersection( new Rectangle( 6, 6, 1, 1 ) ) );
        assertFalse( bitMask.hasIntersection( new Rectangle( 6, 7, 1, 1 ) ) );
    }

    @Test
    public void testAnd() {
        BitMask mask1 = new BitMask( 0, 0, 10, 10 );
        BitMask mask2 = new BitMask( 5, 5, 10, 10 );
        mask1.fill();
        mask2.fill();

        mask1.and( mask2 );
        assertEquals(
            "BitMask [region=[x=0,y=0,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000011111\n"
                + "0000011111\n"
                + "0000011111\n"
                + "0000011111\n"
                + "0000011111]",
            mask1.toString()
        );

        mask2.moveRegion( -8, -8 );
        mask1.and( mask2 );
        assertEquals(
            "BitMask [region=[x=0,y=0,width=10,height=10], bits=\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000011000\n"
                + "0000011000\n"
                + "0000000000\n"
                + "0000000000\n"
                + "0000000000]",
            mask1.toString()
        );
    }
    
    @Test
    public void testSetOr() {
        BitMask mask1 = new BitMask( 1, 1, 25, 25 );
        BitMask mask2 = new BitMask( 10, 10, 10, 10 );
        for ( int i = 0; i < 10; i++ ) {
            mask2.setBit( i, i );
        }
        
        assertEquals( 
            "BitMask [region=[x=1,y=1,width=25,height=25], bits=\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000]", 
            mask1.toString() 
        );
        
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=10,height=10], bits=\n" + 
            "1000000000\n" + 
            "0100000000\n" + 
            "0010000000\n" + 
            "0001000000\n" + 
            "0000100000\n" + 
            "0000010000\n" + 
            "0000001000\n" + 
            "0000000100\n" + 
            "0000000010\n" + 
            "0000000001]", 
            mask2.toString() 
        );
        
        mask1.or( mask2 );
        assertEquals( 
            "BitMask [region=[x=1,y=1,width=25,height=25], bits=\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000001000000000000000\n" + 
            "0000000000100000000000000\n" + 
            "0000000000010000000000000\n" + 
            "0000000000001000000000000\n" + 
            "0000000000000100000000000\n" + 
            "0000000000000010000000000\n" + 
            "0000000000000001000000000\n" + 
            "0000000000000000100000000\n" + 
            "0000000000000000010000000\n" + 
            "0000000000000000001000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000]", 
            mask1.toString() 
        );
        mask1.or( mask2, -12, -12 );
        assertEquals( 
            "BitMask [region=[x=1,y=1,width=25,height=25], bits=\n" + 
            "1000000000000000000000000\n" + 
            "0100000000000000000000000\n" + 
            "0010000000000000000000000\n" + 
            "0001000000000000000000000\n" + 
            "0000100000000000000000000\n" + 
            "0000010000000000000000000\n" + 
            "0000001000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000001000000000000000\n" + 
            "0000000000100000000000000\n" + 
            "0000000000010000000000000\n" + 
            "0000000000001000000000000\n" + 
            "0000000000000100000000000\n" + 
            "0000000000000010000000000\n" + 
            "0000000000000001000000000\n" + 
            "0000000000000000100000000\n" + 
            "0000000000000000010000000\n" + 
            "0000000000000000001000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000\n" + 
            "0000000000000000000000000]", 
            mask1.toString() 
        );
        
    }
    
    @Test
    public void testOrCase123() {
        BitMask mask1 = new BitMask( 1, 8, 6, 1 );
        BitMask mask2 = new BitMask( 1, 5, 5, 4 );
        
        mask2.setBit( 2, 0 );
        mask2.setBit( 4, 1 );
        mask2.setBit( 2, 2 );
        mask2.setBit( 3, 2 );
        mask2.setBit( 4, 2 );
        mask2.setBit( 0, 3 );
        mask2.setBit( 1, 3 );
        mask2.setBit( 2, 3 );
        mask2.setBit( 3, 3 );
        mask2.setBit( 4, 3 );
        
        assertEquals( 
            "BitMask [region=[x=1,y=5,width=5,height=4], bits=\n" + 
            "00100\n" + 
            "00001\n" + 
            "00111\n" + 
            "11111]", 
            mask2.toString() 
        );
        
        mask1.or( mask2, 1, 0 );
        
        assertEquals( 
            "BitMask [region=[x=1,y=8,width=6,height=1], bits=\n" + 
            "011111]", 
            mask1.toString() 
        );
    }
    
    @Test
    public void testOrCase456() {
        BitMask mask1 = new BitMask( 0, 0, 6, 8 );
        BitMask mask2 = new BitMask( 1, 6, 5, 3 );
        
        mask2.setBit( 4, 1 );
        mask2.setBit( 2, 2 );
        mask2.setBit( 3, 2 );
        mask2.setBit( 4, 2 );
        
        assertEquals( 
            "BitMask [region=[x=1,y=6,width=5,height=3], bits=\n" + 
            "00000\n" + 
            "00001\n" + 
            "00111]", 
            mask2.toString() 
        );
        
        mask1.or( mask2 );
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=6,height=8], bits=\n" + 
            "000000\n" + 
            "000000\n" + 
            "000000\n" + 
            "000000\n" + 
            "000000\n" + 
            "000000\n" + 
            "000000\n" + 
            "000001]", 
            mask1.toString() 
        );
    }
    
    @Test
    public void testCreateIntersectionMaskWithRegion() {
        BitMask intersection = new BitMask( 0, 0 );
        Rectangle region = new Rectangle( 0, 0, 8, 8 );
        BitMask mask = new BitMask( 0, 0, 8, 8 );
        mask.fill();
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            mask.toString() 
        );

        BitMask.createIntersectionMask( mask, region, intersection );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );

        region.x = -4;
        region.y = -4;
        BitMask.createIntersectionMask( mask, region, intersection );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 4;
        region.y = 4;
        BitMask.createIntersectionMask( mask, region, intersection );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 4;
        region.y = 4;
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( mask, region, intersection );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        region.x = 0;
        region.y = 0;
        BitMask.createIntersectionMask( mask, region, intersection );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        mask.region().x = 10;
        mask.region().y = 10;
        BitMask.createIntersectionMask( mask, region, intersection );
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
    }
    
    @Test
    public void testCreateIntersectionMaskWithRegionWithIntersectionAdjustOnMask() {
        BitMask intersection = new BitMask( 0, 0 );
        Rectangle region = new Rectangle( 0, 0, 8, 8 );
        BitMask mask = new BitMask( 0, 0, 8, 8 );
        mask.fill();
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            mask.toString() 
        );

        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );

        region.x = -4;
        region.y = -4;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 4;
        region.y = 4;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 4;
        region.y = 4;
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=8,y=8,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        region.x = 8;
        region.y = 8;
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=12,y=12,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        region.x = 0;
        region.y = 0;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        mask.region().x = 10;
        mask.region().y = 10;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
        
        region.x = -10;
        region.y = -10;
        mask.region().x = -10;
        mask.region().y = -10;
        BitMask.createIntersectionMask( mask, region, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
    }
    
    @Test
    public void testCreateIntersectionMaskWithRegionWithIntersectionAdjustOnRegion() {
        BitMask intersection = new BitMask( 0, 0 );
        Rectangle region = new Rectangle( 0, 0, 8, 8 );
        BitMask mask = new BitMask( 0, 0, 8, 8 );
        mask.fill();
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            mask.toString() 
        );

        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );

        region.x = -4;
        region.y = -4;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 4;
        region.y = 4;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 4;
        region.y = 4;
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        region.x = 8;
        region.y = 8;
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        region.x = 0;
        region.y = 0;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        mask.region().x = 10;
        mask.region().y = 10;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
        
        region.x = -10;
        region.y = -10;
        mask.region().x = -10;
        mask.region().y = -10;
        BitMask.createIntersectionMask( region, mask, intersection, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );

    }
    
    @Test
    public void testCreateIntersectionMaskWithRegionWithOffsetOnRegion() {
        BitMask intersection = new BitMask( 0, 0 );
        Rectangle region = new Rectangle( 0, 0, 8, 8 );
        BitMask mask = new BitMask( 0, 0, 8, 8 );
        mask.fill();
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            mask.toString() 
        );

        BitMask.createIntersectionMask( mask, region, intersection, 0, 0, false );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( mask, region, intersection, -4, -4, false );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( mask, region, intersection, 4, 4, false );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( mask, region, intersection, 4, 4, false );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( mask, region, intersection, 0, 0, false );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        mask.region().x = 10;
        mask.region().y = 10;
        BitMask.createIntersectionMask( mask, region, intersection, 0, 0, false );
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );

        region.x = 0;
        region.y = 0;
        mask.region().x = 0;
        mask.region().y = 0;
        BitMask.createIntersectionMask( mask, region, intersection, 0, 0, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( mask, region, intersection, -4, -4, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( mask, region, intersection, 4, 4, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        mask.region().x = -4;
        mask.region().y = -4;
        BitMask.createIntersectionMask( mask, region, intersection, 4, 4, true );
        assertEquals( 
            "BitMask [region=[x=8,y=8,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( mask, region, intersection, 0, 0, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        mask.region().x = 10;
        mask.region().y = 10;
        BitMask.createIntersectionMask( mask, region, intersection, 0, 0, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
    }
    
   
    
    @Test
    public void testCreateIntersectionMaskWithRegionWithOffsetOnMask() {
        BitMask intersection = new BitMask( 0, 0 );
        Rectangle region = new Rectangle( 0, 0, 8, 8 );
        BitMask mask = new BitMask( 0, 0, 8, 8 );
        mask.fill();
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            mask.toString() 
        );

        BitMask.createIntersectionMask( region, mask, intersection, 0, 0, false );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( region, mask, intersection, -4, -4, false );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( region, mask, intersection, 4, 4, false );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = -4;
        region.y = -4;
        BitMask.createIntersectionMask( region, mask, intersection, 4, 4, false );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( region, mask, intersection, 0, 0, false );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        BitMask.createIntersectionMask( region, mask, intersection, 10, 10, false );
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );

        region.x = 0;
        region.y = 0;
        BitMask.createIntersectionMask( region, mask, intersection, 0, 0, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( region, mask, intersection, -4, -4, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( region, mask, intersection, 4, 4, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = -4;
        region.y = -4;
        BitMask.createIntersectionMask( region, mask, intersection, 4, 4, true );
        assertEquals( 
            "BitMask [region=[x=8,y=8,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        BitMask.createIntersectionMask( region, mask, intersection, 0, 0, true );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111\n" + 
            "1111]", 
            intersection.toString() 
        );
        
        region.x = 10;
        region.y = 10;
        BitMask.createIntersectionMask( region, mask, intersection, 10, 10, true );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            intersection.toString() 
        );
    }
    
    @Test
    public void testCreateIntersectionMask() {
        BitMask intersection = new BitMask( 0, 0 );
        BitMask mask1 = new BitMask( 0, 0, 8, 8 );
        mask1.fill();
        BitMask mask2 = new BitMask( 0, 0, 8, 8 );
        for ( int i = 0; i < 8 * 8; i++ ) {
            if ( i % 2 > 0 ) {
                mask2.setBit( i );
            }
        }
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111\n" + 
            "11111111]", 
            mask1.toString() 
        );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101]", 
            mask2.toString() 
        );
        
        BitMask.createIntersectionMask( mask1, mask2, intersection );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=8,height=8], bits=\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101]", 
            intersection.toString() 
        );

        mask2.region().x = -4;
        mask2.region().y = -4;
        BitMask.createIntersectionMask( mask1, mask2, intersection );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "0101\n" + 
            "0101\n" + 
            "0101\n" + 
            "0101]", 
            intersection.toString() 
        );
        
        mask2.region().x = 4;
        mask2.region().y = 4;
        BitMask.createIntersectionMask( mask1, mask2, intersection );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=4,height=4], bits=\n" + 
            "0101\n" + 
            "0101\n" + 
            "0101\n" + 
            "0101]", 
            intersection.toString() 
        );
        
        mask2.region().x = 4;
        mask2.region().y = 4;
        mask1.region().x = -4;
        mask1.region().y = -4;
        BitMask.createIntersectionMask( mask1, mask2, intersection );
        assertEquals( 
            "BitMask [region=[x=4,y=4,width=0,height=0], bits=\n" + 
            "]", 
            intersection.toString() 
        );
        
        mask2.region().x = 0;
        mask2.region().y = 0;
        BitMask.createIntersectionMask( mask1, mask2, intersection );
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=4,height=4], bits=\n" + 
            "0101\n" + 
            "0101\n" + 
            "0101\n" + 
            "0101]", 
            intersection.toString() 
        );
        
        mask2.region().x = 10;
        mask2.region().y = 10;
        mask1.region().x = 10;
        mask1.region().y = 10;
        BitMask.createIntersectionMask( mask1, mask2, intersection );
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=8,height=8], bits=\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101\n" + 
            "01010101]", 
            intersection.toString() 
        );
    }


}
