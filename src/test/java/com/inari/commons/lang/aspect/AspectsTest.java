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
        
        Aspect aspect1 = aspectGroup.createAspect( "aspect1", 1 );
        Aspect aspect2 = aspectGroup.createAspect( "aspect2", 2 );
        Aspect aspect3 = aspectGroup.createAspect( "aspect3", 3 );
        Aspect aspect5 = aspectGroup.createAspect( "aspect5", 4 );
        Aspect aspect10 = aspectGroup.createAspect( "aspect10", 10 );
        Aspect aspect50 = aspectGroup.createAspect( "aspect50", 50 );
        
        Aspects aspects1 = aspectGroup.createAspects();
        Aspects aspects2 = aspectGroup.createAspects();
        Aspects aspects3 = aspectGroup.createAspects();

        assertEquals( "Aspects [group=TestAspect1 {}]", aspects1.toString() );
        assertEquals( "Aspects [group=TestAspect1 {}]", aspects2.toString() );
        assertEquals( "Aspects [group=TestAspect1 {}]", aspects3.toString() );
        assertEquals( "64", String.valueOf( aspects1.size() ) );
        assertEquals( "64", String.valueOf( aspects2.size() ) );
        assertEquals( "64", String.valueOf( aspects3.size() ) );
        
        aspects1.set( aspect1 );
        aspects1.set( aspect3 );
        aspects1.set( aspect5 );
        
        aspects2.set( aspect2 );
        aspects2.set( aspect10 );
        
        aspects3.set( aspect50 );
        
        assertEquals( "Aspects [group=TestAspect1 {aspect1, aspect3, aspect5}]", aspects1.toString() );
        assertEquals( "Aspects [group=TestAspect1 {aspect2, aspect10}]", aspects2.toString() );
        assertEquals( "Aspects [group=TestAspect1 {aspect50}]", aspects3.toString() );
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
