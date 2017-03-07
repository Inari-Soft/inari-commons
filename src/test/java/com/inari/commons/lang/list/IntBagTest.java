package com.inari.commons.lang.list;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import org.junit.Test;

import com.inari.commons.lang.IntIterator;

public class IntBagTest {
    
    @Test
    public void testCreation() {
        IntBag array = new IntBag();
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=0, length=10, array=[-2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        assertEquals( "-2147483648", String.valueOf( array.getNullValue() ) );
        assertEquals( "10", String.valueOf( array.getExpand() ) );
        assertEquals( "0", String.valueOf( array.size() ) );
        assertEquals( "10", String.valueOf( array.length() ) );
        assertTrue( array.isEmpty() );
        
        array = new IntBag( 3 );
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=0, length=3, array=[-2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        
        array = new IntBag( 3, -1 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=0, length=3, array=[-1, -1, -1]]", 
            array.toString() 
        );
        
        array = new IntBag( 3, -1, 20 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=20, size=0, length=3, array=[-1, -1, -1]]", 
            array.toString() 
        );

        BitSet bitset = new BitSet();
        bitset.set( 2 );
        bitset.set( 12 );
        array = new IntBag( bitset, -1, 20 );
        assertEquals(
            "IntBag [nullValue=-1, expand=20, size=2, length=2, array=[2, 12]]",
            array.toString()
        );
        assertFalse( array.isEmpty() );

        array.clear();
        assertEquals(
            "IntBag [nullValue=-1, expand=20, size=0, length=2, array=[-1, -1]]",
            array.toString()
        );
    }

    @Test
    public void testNullValue() {
        IntBag array = new IntBag();
        array.set( 2, 3 );
        array.set( 6, 2 );
        assertEquals(
            "IntBag [nullValue=-2147483648, expand=10, size=2, length=10, array=[-2147483648, -2147483648, 3, -2147483648, -2147483648, -2147483648, 2, -2147483648, -2147483648, -2147483648]]",
            array.toString()
        );

        assertTrue( Integer.MIN_VALUE == array.getNullValue() );

        array.setNullValue( -1 );
        assertEquals(
            "IntBag [nullValue=-1, expand=10, size=2, length=10, array=[-1, -1, 3, -1, -1, -1, 2, -1, -1, -1]]",
            array.toString()
        );

        try {
            array.setNullValue( 2 );
            fail( "IllegalArgumentException expected here " );
        } catch( IllegalArgumentException iae ) {
            assertEquals( "The IntBag contains already a not-null value that is equals to the new null-value", iae.getMessage() );
        }

    }
    
    @Test
    public void testAdd() {
        IntBag array = new IntBag();
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=0, length=10, array=[-2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        
        array.add( 1 );
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=1, length=10, array=[1, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        array.add( 2 );
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=2, length=10, array=[1, 2, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        array.add( 3 );
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=3, length=10, array=[1, 2, 3, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );

        array = new IntBag( 10, -1, 10 );
        array.addAll( 1, 4, 5 );
        assertEquals(
            "IntBag [nullValue=-1, expand=10, size=3, length=10, array=[1, 4, 5, -1, -1, -1, -1, -1, -1, -1]]",
            array.toString()
        );

        array.clear();
        IntBag array2 = new IntBag();
        array2.set( 2, 3 );
        array2.set( 5, 1 );
        array.addAll( array2 );
        assertEquals(
            "IntBag [nullValue=-1, expand=10, size=2, length=10, array=[3, 1, -1, -1, -1, -1, -1, -1, -1, -1]]",
            array.toString()
        );
    }
    
    @Test
    public void testRemove() {
        IntBag array = new IntBag();
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=0, length=10, array=[-2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        
        assertFalse( array.remove( 2 ) );
        
        array.add( 1 );
        array.add( 4 );
        array.add( 7 );
        
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=3, length=10, array=[1, 4, 7, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
        
        assertFalse( array.remove( 2 ) );
        
        assertTrue( array.remove( 4 ) );
        
        assertEquals( 
            "IntBag [nullValue=-2147483648, expand=10, size=2, length=10, array=[1, -2147483648, 7, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]]", 
            array.toString() 
        );
    }
    
    @Test
    public void testAddWithExpand() {
        IntBag array = new IntBag( 3, -1 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=0, length=3, array=[-1, -1, -1]]", 
            array.toString() 
        );
        
        array.add( 11 );
        array.add( 22 );
        array.add( 33 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=3, length=3, array=[11, 22, 33]]", 
            array.toString() 
        );
        
        array.add( 44 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=4, length=13, array=[11, 22, 33, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1]]", 
            array.toString() 
        );
    }
    
    @Test
    public void testSetWithExpand() {
        IntBag array = new IntBag( 3, -1 );
        array.setExpand( 4 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=4, size=0, length=3, array=[-1, -1, -1]]", 
            array.toString() 
        );
        
        array.set( 0, 11 );
        array.set( 1, 22 );
        array.set( 2, 33 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=4, size=3, length=3, array=[11, 22, 33]]", 
            array.toString() 
        );
        
        array.set( 3, 44 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=4, size=4, length=8, array=[11, 22, 33, 44, -1, -1, -1, -1]]", 
            array.toString() 
        );
        
        array.set( 7, 88 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=4, size=5, length=8, array=[11, 22, 33, 44, -1, -1, -1, 88]]", 
            array.toString() 
        );
        
        array.set( 20, 100 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=4, size=6, length=25, array=[11, 22, 33, 44, -1, -1, -1, 88, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 100, -1, -1, -1, -1]]", 
            array.toString() 
        );
    }
    
    @Test
    public void testTrim() {
        IntBag array = new IntBag( 3, -1 );
        array.add( 11 );
        array.add( 22 );
        array.add( 33 );
        array.add( 44 );
        
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=4, length=13, array=[11, 22, 33, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1]]", 
            array.toString() 
        );
        
        array.trim();
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=4, length=4, array=[11, 22, 33, 44]]", 
            array.toString() 
        );
        
        array.remove( 22 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=3, length=4, array=[11, -1, 33, 44]]", 
            array.toString() 
        );
        
        array.trim();
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=3, length=3, array=[11, 44, 33]]", 
            array.toString() 
        );
        
        array = new IntBag( 3, -1 );
        array.add( 11 );
        array.add( 22 );
        array.add( 33 );
        array.remove( 22 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=2, length=3, array=[11, -1, 33]]", 
            array.toString() 
        );
        
        array.trim();
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=2, length=2, array=[11, 33]]", 
            array.toString() 
        );
        
        array.add( 55 );
        array.add( 66 );
        array.add( 77 );
        array.remove( 11 );
        array.remove( 66 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=3, length=12, array=[-1, 33, 55, -1, 77, -1, -1, -1, -1, -1, -1, -1]]", 
            array.toString() 
        );
        
        array.trim();
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=3, length=3, array=[77, 33, 55]]", 
            array.toString() 
        );
    }
    
    @Test
    public void testIterator() {
        IntBag array = new IntBag( 3, -1 );
        
        assertFalse( array.iterator().hasNext() );
        
        array.add( 11 );
        array.add( 22 );
        array.add( 33 );
        array.add( 44 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=4, length=13, array=[11, 22, 33, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1]]", 
            array.toString() 
        );
        
        IntIterator iterator = array.iterator();
        assertTrue( iterator.hasNext() );
        assertEquals( 11, iterator.next() );
        assertTrue( iterator.hasNext() );
        assertEquals( 22, iterator.next() );
        assertTrue( iterator.hasNext() );
        assertEquals( 33, iterator.next() );
        assertTrue( iterator.hasNext() );
        assertEquals( 44, iterator.next() );
        assertFalse( iterator.hasNext() );
        
        array.remove( 22 );
        array.remove( 33 );
        assertEquals( 
            "IntBag [nullValue=-1, expand=10, size=2, length=13, array=[11, -1, -1, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1]]", 
            array.toString() 
        );
        iterator = array.iterator();
        assertTrue( iterator.hasNext() );
        assertEquals( 11, iterator.next() );
        assertTrue( iterator.hasNext() );
        assertEquals( 44, iterator.next() );
        assertFalse( iterator.hasNext() );
    }
    
    @Test
    public void performanceTest() {
        int runs = 10000;
        Random random = new Random();
        Runtime runtime = Runtime.getRuntime();
        
        IntBag array = new IntBag();
        long startTime = System.nanoTime();
        System.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        for ( int i = 0; i < runs; i++ ) {
            array.add( i );
        }
        for ( int i = 0; i < runs; i++ ) {
            int nextInt = random.nextInt( runs );
            array.add( nextInt );
            nextInt = random.nextInt( runs );
            array.remove( nextInt );
        }
        long endTime = System.nanoTime();
        System.gc();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println( "IntBag no init:   " + ( endTime - startTime ) + "     " + ( memoryAfter - memoryBefore ) );
        
        array = new IntBag( runs );
        startTime = System.nanoTime();
        for ( int i = 0; i < runs; i++ ) {
            array.add( i );
        }
        for ( int i = 0; i < runs; i++ ) {
            int nextInt = random.nextInt( runs );
            array.add( nextInt );
            nextInt = random.nextInt( runs );
            array.remove( nextInt );
        }
        endTime = System.nanoTime();
        System.out.println( "IntBag with init: " + ( endTime - startTime ) );
        
        ArrayList<Integer> collection = new ArrayList<Integer>();
        startTime = System.nanoTime();
        System.gc();
        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        for ( int i = 0; i < runs; i++ ) {
            collection.add( i );
        }
        for ( int i = 0; i < runs; i++ ) {
            int nextInt = random.nextInt( runs );
            collection.add( nextInt );
            nextInt = random.nextInt( runs );
            collection.remove( (Integer) nextInt );
        }
        endTime = System.nanoTime();
        System.gc();
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println( "ArrayList:             " + ( endTime - startTime ) + "     " + ( memoryAfter - memoryBefore ) );
        
        BitSet bitset = new BitSet();
        startTime = System.nanoTime();
        System.gc();
        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        for ( int i = 0; i < runs; i++ ) {
            bitset.set( i );
        }
        for ( int i = 0; i < runs; i++ ) {
            int nextInt = random.nextInt( runs );
            bitset.set( nextInt );
            nextInt = random.nextInt( runs );
            bitset.clear( nextInt );
        }
        endTime = System.nanoTime();
        System.gc();
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println( "BitSet:                " + ( endTime - startTime ) + "     " + ( memoryAfter - memoryBefore ) );
    }

}
