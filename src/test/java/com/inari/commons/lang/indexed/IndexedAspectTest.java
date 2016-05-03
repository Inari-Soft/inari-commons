package com.inari.commons.lang.indexed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IndexedAspectTest {
    
    
//    @Test
//    public void testCreation() {
//        IndexedAspect attrA1 = IndexedAspectBuilder.c( A.class );
//        IndexedAspect attrA2 = new IndexedAspect( AA.class );
//        
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={}, types={} ]", attrA1.toString() );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={}, types={} ]", attrA2.toString() );
//        
//        attrA1.set( AA.class );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={0}, types={AA} ]", attrA1.toString() );
//        attrA1.set( AB.class );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={0, 1}, types={AA,AB} ]", attrA1.toString() );
//        attrA1.set( AC.class );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={0, 1, 2}, types={AA,AB,AC} ]", attrA1.toString() );
//        
//        attrA1.reset( AB.class );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={0, 2}, types={AA,AC} ]", attrA1.toString() );
//        attrA1.reset( AA.class );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={2}, types={AC} ]", attrA1.toString() );
//        attrA1.reset( AA.class );
//        assertEquals( "Aspect [ indexedBaseType=A, bitset={2}, types={AC} ]", attrA1.toString() );
//    }
    
    @Test
    public void testInclude() {
        IndexedTypeAspects attrA1 = IndexedTypeAspectBuilder.build( A.AIndexedTypeKey.class );
        IndexedTypeAspects attrA2 = IndexedTypeAspectBuilder.build( A.AIndexedTypeKey.class );
        
        assertFalse( attrA1.include( attrA2 ) );
        assertFalse( attrA2.include( attrA1 ) );
        
        // A1=AA : A2=NONE
        IndexedTypeAspectBuilder.set( attrA1, AA.class );
        assertFalse( attrA1.include( attrA2 ) );
        assertFalse( attrA2.include( attrA1 ) );
        
        // A1=AA : A2=AA
        IndexedTypeAspectBuilder.set( attrA2, AA.class );
        assertTrue( attrA1.include( attrA2 ) );
        assertTrue( attrA2.include( attrA1 ) );
        
        // A1=AA,AB : A2=AA
        IndexedTypeAspectBuilder.set( attrA1, AB.class );
        assertEquals( "IndexedTypeAspects [ indexedBaseType=AIndexedTypeKey, bitset={0, 1}, types={AA,AB} ]", attrA1.toString() );
        assertEquals( "IndexedTypeAspects [ indexedBaseType=AIndexedTypeKey, bitset={0}, types={AA} ]", attrA2.toString() );
        assertTrue( attrA1.include( attrA2 ) );
        assertFalse( attrA2.include( attrA1 ) );
        
        // A1=AA,AB : A2=AA,AC
        IndexedTypeAspectBuilder.set( attrA2, AC.class );
        assertFalse( attrA1.include( attrA2 ) );
        assertFalse( attrA2.include( attrA1 ) );
        
        // A1=AA,AC : A2=AA,AC
        IndexedTypeAspectBuilder.reset( attrA1, AB.class );
        IndexedTypeAspectBuilder.set( attrA1, AC.class );
        assertTrue( attrA1.include( attrA2 ) );
        assertTrue( attrA2.include( attrA1 ) );
        
        // A1=AA,AB,AC : A2=AA,AC
        IndexedTypeAspectBuilder.set( attrA1, AB.class );
        assertTrue( attrA1.include( attrA2 ) );
        assertFalse( attrA2.include( attrA1 ) );
    }

}
