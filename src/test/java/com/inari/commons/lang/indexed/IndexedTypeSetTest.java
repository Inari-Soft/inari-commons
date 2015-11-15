package com.inari.commons.lang.indexed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class IndexedTypeSetTest {
    
    @Before
    public void init() {
        Indexer.clear();
    }
    
    @Test
    public void testCreation() {

        IndexedTypeSet aSet1 = new IndexedTypeSet( A.AIndexedTypeKey.class );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=0 size:0, indexed={} ]", aSet1.toString() );
        
        IndexedTypeSet aSet2 = new IndexedTypeSet( A.AIndexedTypeKey.class, 3 );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=3 size:0, indexed={null,null,null} ]", aSet2.toString() );
        
        IndexedTypeSet aSet3 = new IndexedTypeSet( A.AIndexedTypeKey.class, 4 );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=4 size:0, indexed={null,null,null,null} ]", aSet3.toString() );
        
        IndexedTypeSet bSet1 = new IndexedTypeSet( B.BIndexedTypeKey.class );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=0 size:0, indexed={} ]", bSet1.toString() );
    }
    
    @Test
    public void testPopulate1() {
        IndexedTypeSet aSet1 = new IndexedTypeSet( A.AIndexedTypeKey.class );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=0 size:0, indexed={} ]", aSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=AIndexedTypeKey, bitset={}, types={} ]", aSet1.getAspect().toString() );
        
        AA aa = new AA();
        AB ab = new AB();
        AC ac = new AC();
        
        assertTrue( aa.typeIndex() == 0 );
        assertTrue( ab.typeIndex() == 1 );
        assertTrue( ac.typeIndex() == 2 );
        
        aSet1.set( aa );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=1 size:1, indexed={AA} ]", aSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=AIndexedTypeKey, bitset={0}, types={AA} ]", aSet1.getAspect().toString() );
        
        aSet1.set( ac );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=3 size:2, indexed={AA,null,AC} ]", aSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=AIndexedTypeKey, bitset={0, 2}, types={AA,AC} ]", aSet1.getAspect().toString() );
        
        aSet1.set( ab );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=3 size:3, indexed={AA,AB,AC} ]", aSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=AIndexedTypeKey, bitset={0, 1, 2}, types={AA,AB,AC} ]", aSet1.getAspect().toString() );

    }
    
    @Test
    public void testPopulate11() {
        IndexedTypeSet aSet1 = new IndexedTypeSet(  A.AIndexedTypeKey.class );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=0 size:0, indexed={} ]", aSet1.toString() );
        
        AA aa = new AA();
        AC ac = new AC();
        AB ab = new AB();
        
        assertTrue( aa.typeIndex() == 0 );
        assertTrue( ab.typeIndex() == 2 );
        assertTrue( ac.typeIndex() == 1 );
        
        aSet1.set( aa );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=1 size:1, indexed={AA} ]", aSet1.toString() );
        
        aSet1.set( ac );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=2 size:2, indexed={AA,AC} ]", aSet1.toString() );
        
        aSet1.set( ab );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=3 size:3, indexed={AA,AC,AB} ]", aSet1.toString() );

    }
    
    @Test
    public void testPopulate2() {
        BA ba = new BA();
        BB bb = new BB();
        BC bc = new BC();
        
        assertTrue( ba.typeIndex() == 0 );
        assertTrue( bb.typeIndex() == 1 );
        assertTrue( bc.typeIndex() == 2 );
        
        IndexedTypeSet bSet1 = new IndexedTypeSet( B.BIndexedTypeKey.class );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:0, indexed={null,null,null} ]", bSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=BIndexedTypeKey, bitset={}, types={} ]", bSet1.getAspect().toString() );

        bSet1.set( ba );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:1, indexed={BA,null,null} ]", bSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=BIndexedTypeKey, bitset={0}, types={BA} ]", bSet1.getAspect().toString() );
        
        bSet1.set( bc );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:2, indexed={BA,null,BC} ]", bSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=BIndexedTypeKey, bitset={0, 2}, types={BA,BC} ]", bSet1.getAspect().toString() );
        
        bSet1.set( bb );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:3, indexed={BA,BB,BC} ]", bSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=BIndexedTypeKey, bitset={0, 1, 2}, types={BA,BB,BC} ]", bSet1.getAspect().toString() );
    }
    
    @Test
    public void testIteration() {
        BA ba = new BA();
        BB bb = new BB();
        BC bc = new BC();
        
        assertTrue( ba.typeIndex() == 0 );
        assertTrue( bb.typeIndex() == 1 );
        assertTrue( bc.typeIndex() == 2 );
        
        IndexedTypeSet bSet1 = new IndexedTypeSet( B.BIndexedTypeKey.class );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:0, indexed={null,null,null} ]", bSet1.toString() );
        Iterable<IndexedType> iterable = bSet1.getIterable();
        assertNotNull( iterable );
        Iterator<IndexedType> iterator = iterable.iterator();
        assertFalse( iterator.hasNext() );

        bSet1.set( ba );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:1, indexed={BA,null,null} ]", bSet1.toString() );
        iterable = bSet1.getIterable();
        assertNotNull( iterable );
        iterator = iterable.iterator();
        assertTrue( iterator.hasNext() );
        IndexedType next = iterator.next();
        assertEquals( ba.toString(), next.toString() );
        assertFalse( iterator.hasNext() );
        
        bSet1.set( bc );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:2, indexed={BA,null,BC} ]", bSet1.toString() );
        iterable = bSet1.getIterable();
        assertNotNull( iterable );
        iterator = iterable.iterator();
        assertTrue( iterator.hasNext() );
        next = iterator.next();
        assertEquals( ba.toString(), next.toString() );
        assertTrue( iterator.hasNext() );
        next = iterator.next();
        assertEquals( bc.toString(), next.toString() );
        assertFalse( iterator.hasNext() );
        
        bSet1.set( bb );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:3, indexed={BA,BB,BC} ]", bSet1.toString() );
        iterable = bSet1.getIterable();
        assertNotNull( iterable );
        iterator = iterable.iterator();
        assertTrue( iterator.hasNext() );
        next = iterator.next();
        assertEquals( ba.toString(), next.toString() );
        assertTrue( iterator.hasNext() );
        next = iterator.next();
        assertEquals( bb.toString(), next.toString() );
        assertTrue( iterator.hasNext() );
        next = iterator.next();
        assertEquals( bc.toString(), next.toString() );
        assertFalse( iterator.hasNext() );
    }
    
    @Test
    @SuppressWarnings( "unused" )
    public void testGet() {
        
        AA aa = new AA();
        AB ab = new AB();
        AC ac = new AC();
        
        IndexedTypeSet aSet1 = new IndexedTypeSet(  A.AIndexedTypeKey.class );
        aSet1.set( aa );
        aSet1.set( ac );
        assertEquals( "IndexedTypeSet [ indexedBaseType=AIndexedTypeKey length=3 size:2, indexed={AA,null,AC} ]", aSet1.toString() );
        
        AA _aa = aSet1.get( AA.class );
        assertNotNull( _aa );
        assertTrue( aa == _aa );
        
        AB _ab = aSet1.get( AB.class );
        assertNull( _ab );
        
        AC _ac = aSet1.get( AC.class );
        assertNotNull( _ac );
        assertTrue( ac == _ac );
    }
    
    @Test
    public void testRemove() {
        BA ba = new BA();
        BB bb = new BB();
        BC bc = new BC();
        
        assertTrue( ba.typeIndex() == 0 );
        assertTrue( bb.typeIndex() == 1 );
        assertTrue( bc.typeIndex() == 2 );
        
        IndexedTypeSet bSet1 = new IndexedTypeSet( B.BIndexedTypeKey.class );
        bSet1.set( ba );
        bSet1.set( bc );
        bSet1.set( bb );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:3, indexed={BA,BB,BC} ]", bSet1.toString() );
        
        IndexedType removed = bSet1.remove( bb );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:2, indexed={BA,null,BC} ]", bSet1.toString() );
        assertEquals( bb, removed );
        
        removed = bSet1.remove( bb );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:2, indexed={BA,null,BC} ]", bSet1.toString() );
        assertNull( removed );
        
        removed = bSet1.remove( bc );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:1, indexed={BA,null,null} ]", bSet1.toString() );
        assertEquals( bc, removed );
    }
    
    @Test
    public void testClear() {
        BA ba = new BA();
        BB bb = new BB();
        BC bc = new BC();
        
        assertTrue( ba.typeIndex() == 0 );
        assertTrue( bb.typeIndex() == 1 );
        assertTrue( bc.typeIndex() == 2 );
        
        IndexedTypeSet bSet1 = new IndexedTypeSet( B.BIndexedTypeKey.class );
        bSet1.set( ba );
        bSet1.set( bc );
        bSet1.set( bb );
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:3, indexed={BA,BB,BC} ]", bSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=BIndexedTypeKey, bitset={0, 1, 2}, types={BA,BB,BC} ]", bSet1.getAspect().toString() );
        
        bSet1.clear();
        assertEquals( "IndexedTypeSet [ indexedBaseType=BIndexedTypeKey length=3 size:0, indexed={null,null,null} ]", bSet1.toString() );
        assertEquals( "IndexedAspect [ indexedBaseType=BIndexedTypeKey, bitset={}, types={} ]", bSet1.getAspect().toString() );

    }
 
}
