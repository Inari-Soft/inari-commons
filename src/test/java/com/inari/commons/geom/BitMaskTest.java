package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;

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
    }
    
    @Test
    public void testSetBit() {
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
    }
    
    @Test
    public void testSetRegion() {
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
    public void testCreateIntersectionMaskWithRegion() {
        BitMask mask1 = new BitMask( 10, 10, 10, 10 );
        for ( int y = 0; y < 10; y++ ) {
            for ( int x = 0; x < 10; x++ ) {
                if ( x % 2 != 0 ) {
                    mask1.setBit( x, y, false );
                }
            }
        }
        
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=10,height=10], bits=\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101\n" + 
            "0101010101]", 
            mask1.toString() 
        );
        
        BitMask result = new BitMask( 0, 0 );
        Rectangle region = new Rectangle( 0, 0, 10, 10 );
        
        // the region do not intersect with mask1 at all, so result should be empty
        BitMask.createIntersectionMask( mask1, region, result );
        
        assertEquals( 
            "BitMask [region=[x=0,y=0,width=0,height=0], bits=\n" + 
            "]", 
            result.toString() 
        );
        
        // now we shift the region 2 in x and y
        BitMask.createIntersectionMask( mask1, region, result, 2, 2 );
        // NOTE: x and y of the region of the mask intersection are relative to the origin
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=2,height=2], bits=\n" + 
            "01\n" + 
            "01]", 
            result.toString() 
        );
        
        // now we create a new region that intersects mask1 by origin
        region = new Rectangle( 5, 5, 10, 10 );
        BitMask.createIntersectionMask( mask1, region, result );
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=5,height=5], bits=\n" + 
            "01010\n" + 
            "01010\n" + 
            "01010\n" + 
            "01010\n" + 
            "01010]", 
            result.toString() 
        );
        
        
        BitMask.createIntersectionMask( mask1, region, result, 10, 10 );
        assertEquals( 
            "BitMask [region=[x=15,y=15,width=5,height=5], bits=\n" + 
            "10101\n" + 
            "10101\n" + 
            "10101\n" + 
            "10101\n" + 
            "10101]", 
            result.toString() 
        );
    }
    
    @Test
    public void testCreateIntersectionMask() { 
        BitMask mask1 = new BitMask( 10, 10, 10, 10 );
        BitMask mask2 = new BitMask( 10, 10, 10, 10 );
        for ( int y = 0; y < 10; y++ ) {
            for ( int x = 0; x < 10; x++ ) {
                if ( x >= y ) {
                    mask1.setBit( x, y );
                }
                if ( x < y ) {
                    mask2.setBit( x, y );
                }
            }
        }
        
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=10,height=10], bits=\n" + 
            "1111111111\n" + 
            "0111111111\n" + 
            "0011111111\n" + 
            "0001111111\n" + 
            "0000111111\n" + 
            "0000011111\n" + 
            "0000001111\n" + 
            "0000000111\n" + 
            "0000000011\n" + 
            "0000000001]", 
            mask1.toString() 
        );
        
        assertEquals( 
            "BitMask [region=[x=10,y=10,width=10,height=10], bits=\n" + 
            "0000000000\n" + 
            "1000000000\n" + 
            "1100000000\n" + 
            "1110000000\n" + 
            "1111000000\n" + 
            "1111100000\n" + 
            "1111110000\n" + 
            "1111111000\n" + 
            "1111111100\n" + 
            "1111111110]", 
            mask2.toString() 
        );
        
        BitMask result = new BitMask( 0, 0 );
        BitMask.createIntersectionMask( mask1, mask2, result );
        
        // NOTE: we have an intersection mask as result with no set bits here because we have a regions intersect but not the set-bits
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
            result.toString() 
        );
        
        // now we shift one to the right
        BitMask.createIntersectionMask( mask1, mask2, result, 1, 0 );
        assertEquals( 
            "BitMask [region=[x=16,y=15,width=4,height=5], bits=\n" + 
            "0000\n" + 
            "1000\n" + 
            "0100\n" + 
            "0010\n" + 
            "0001]", 
            result.toString() 
        );
    }
    

}
