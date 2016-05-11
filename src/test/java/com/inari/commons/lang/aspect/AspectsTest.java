package com.inari.commons.lang.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AspectsTest {

    @Test
    public void testAspects() {
        AspectGroup aspectGroup = new AspectGroup( "TestAspect1" );
        aspectGroup.createAspect( "TestAspect1" );
        
        Aspects aspect1 = aspectGroup.createAspects();
        Aspects aspect2 = aspectGroup.createAspects();
        Aspects aspect3 = aspectGroup.createAspects();
        
        assertEquals( "Aspects [group=TestAspect1, bitset={}, size=64]", aspect1.toString() );
        assertEquals( "Aspects [group=TestAspect1, bitset={}, size=64]", aspect2.toString() );
        assertEquals( "Aspects [group=TestAspect1, bitset={}, size=64]", aspect3.toString() );
        
        aspect1.bitset.set( 1 );
        aspect1.bitset.set( 3 );
        aspect1.bitset.set( 5 );
        
        aspect2.bitset.set( 2 );
        aspect2.bitset.set( 10 );
        
        aspect3.bitset.set( 50 );
        
        assertEquals( "Aspects [group=TestAspect1, bitset={1, 3, 5}, size=64]", aspect1.toString() );
        assertEquals( "Aspects [group=TestAspect1, bitset={2, 10}, size=64]", aspect2.toString() );
        assertEquals( "Aspects [group=TestAspect1, bitset={50}, size=64]", aspect3.toString() );
        
        aspect1.bitset.set( 6 );
        
        assertEquals( "Aspects [group=TestAspect1, bitset={1, 3, 5, 6}, size=64]", aspect1.toString() );
    }
    
    
    
    @Test
    public void testInclude() {
        AspectGroup aspectGroup = new AspectGroup( "TestAspect2" );
        
        Aspects aspect1 = aspectGroup.createAspects();
        Aspects aspect2 = aspectGroup.createAspects();
        Aspects aspect3 = aspectGroup.createAspects();
        Aspects aspect4 = aspectGroup.createAspects();
        
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
        AspectGroup aspectGroup = new AspectGroup( "TestAspect3" );
        
        Aspects aspect1 = aspectGroup.createAspects();
        Aspects aspect2 = aspectGroup.createAspects();
        Aspects aspect3 = aspectGroup.createAspects();
        Aspects aspect4 = aspectGroup.createAspects();
        
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
