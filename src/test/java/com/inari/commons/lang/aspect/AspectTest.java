package com.inari.commons.lang.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AspectTest {
    
    @Test
    public void testAspects() {
        Aspect aspect1 = new Aspect( 5 );
        Aspect aspect2 = new Aspect( 10 );
        Aspect aspect3 = new Aspect( 100 );
        
        assertEquals( "Aspect [ size=64 bitset={} ]", aspect1.toString() );
        assertEquals( "Aspect [ size=64 bitset={} ]", aspect2.toString() );
        assertEquals( "Aspect [ size=128 bitset={} ]", aspect3.toString() );
        
        aspect1.bitset.set( 1 );
        aspect1.bitset.set( 3 );
        aspect1.bitset.set( 5 );
        
        aspect2.bitset.set( 2 );
        aspect2.bitset.set( 10 );
        
        aspect3.bitset.set( 50 );
        
        assertEquals( "Aspect [ size=64 bitset={1, 3, 5} ]", aspect1.toString() );
        assertEquals( "Aspect [ size=64 bitset={2, 10} ]", aspect2.toString() );
        assertEquals( "Aspect [ size=128 bitset={50} ]", aspect3.toString() );
        
        aspect1.bitset.set( 6 );
        
        assertEquals( "Aspect [ size=64 bitset={1, 3, 5, 6} ]", aspect1.toString() );
    }
    
    @Test
    public void testInclude() {
        Aspect aspect1 = new Aspect( 5 );
        Aspect aspect2 = new Aspect( 5 );
        Aspect aspect3 = new Aspect( 10 );
        Aspect aspect4 = new Aspect( 10 );
        
        assertFalse( aspect1.include( aspect2 ) );
        assertFalse( aspect2.include( aspect3 ) );
        assertFalse( aspect3.include( aspect4 ) );
        assertFalse( aspect4.include( aspect4 ) );
        
        aspect1.bitset.set( 1 );
        aspect1.bitset.set( 3 );
        aspect1.bitset.set( 5 );
        
        aspect2.bitset.set( 1 );
        aspect2.bitset.set( 5 );
        
        aspect3.bitset.set( 3 );
        aspect3.bitset.set( 5 );
        
        aspect4.bitset.set( 3 );
        aspect4.bitset.set( 5 );
        aspect4.bitset.set( 9 );
        
        assertTrue( aspect1.include( aspect2 ) );
        assertFalse( aspect2.include( aspect1 ) );
        
        assertTrue( aspect1.include( aspect3 ) );
        assertFalse( aspect3.include( aspect1 ) );
        
        assertTrue( aspect4.include( aspect3 ) );
        assertFalse( aspect3.include( aspect4 ) );
        
        assertTrue( aspect1.include( aspect1 ) );
    }
    
    @Test
    public void testExclude() {
        Aspect aspect1 = new Aspect( 5 );
        Aspect aspect2 = new Aspect( 5 );
        Aspect aspect3 = new Aspect( 10 );
        Aspect aspect4 = new Aspect( 10 );
        
        assertFalse( aspect1.exclude( aspect2 ) );
        assertFalse( aspect2.exclude( aspect3 ) );
        assertFalse( aspect4.exclude( aspect4 ) );
        
        aspect1.bitset.set( 1 );
        aspect1.bitset.set( 3 );
        aspect1.bitset.set( 5 );
        
        aspect2.bitset.set( 0 );
        aspect2.bitset.set( 2 );
        aspect2.bitset.set( 4 );
        
        aspect3.bitset.set( 3 );
        aspect3.bitset.set( 5 );
        
        aspect4.bitset.set( 3 );
        aspect4.bitset.set( 5 );
        aspect4.bitset.set( 9 );
        
        assertTrue( aspect1.exclude( aspect2 ) );
        assertTrue( aspect2.exclude( aspect1 ) );
        
        assertFalse( aspect1.exclude( aspect3 ) );
        assertTrue( aspect3.exclude( aspect2 ) );
        
        
        assertFalse( aspect1.exclude( aspect1 ) );
    }

}
