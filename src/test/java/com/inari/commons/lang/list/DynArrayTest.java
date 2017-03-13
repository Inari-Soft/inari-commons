package com.inari.commons.lang.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import com.inari.commons.lang.Method;

public class DynArrayTest {

    @Test
    public void testCreation() {
        DynArray<String> array1 = DynArray.create( String.class );
        DynArray<String> array2 = DynArray.create( String.class, 10 );
        
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null], size()=0, capacity()=50]", 
            array1.toString() 
        );
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null], size()=0, capacity()=10]", 
            array2.toString() 
        );
    }
    
    
    @Test
    public void testCreationUntyped() {
        DynArray<DynArray<String>> array1 = DynArray.createTyped( DynArray.class );
        array1.set( 0, DynArray.create( String.class ) );
        DynArray<String>[] array = array1.getArray();
        DynArray<String> instance =  array[ 0 ];
        
        assertNotNull( instance );
    }
    
    @Test
    public void testTrim() {
        DynArray<String> array = DynArray.create( String.class, 20, 5 );
        array.set( 3, "1" );
        array.set( 6, "2" );
        array.set( 9, "3" );
        array.set( 14, "4" );
        array.set( 15, "5" );
        
        assertEquals( 
            "DynArray [list=[null, null, null, 1, null, null, 2, null, null, 3, null, null, null, null, 4, 5, null, null, null, null], size()=5, capacity()=20]", 
            array.toString() 
        );
        
        array.trim();
        
        assertEquals( 
            "DynArray [list=[1, 2, 3, 4, 5], size()=5, capacity()=5]", 
            array.toString() 
        );
        
        array.add( "6" );
        
        assertEquals( 
            "DynArray [list=[1, 2, 3, 4, 5, 6, null, null, null, null], size()=6, capacity()=10]", 
            array.toString() 
        );
        
        array.trim();
        
        assertEquals( 
            "DynArray [list=[1, 2, 3, 4, 5, 6], size()=6, capacity()=6]", 
            array.toString() 
        );
        
        array.trim();
        
        assertEquals( 
            "DynArray [list=[1, 2, 3, 4, 5, 6], size()=6, capacity()=6]", 
            array.toString() 
        );
    }
    @Test
    public void testSetGet() {
        DynArray<String> array = DynArray.create( String.class, 10, 10 );
        
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null], size()=0, capacity()=10]", 
            array.toString() 
        );
        
        array.set( 1, "one" );
        array.set( 3, "two" );
        array.set( 6, "tree" );
        array.set( 8, "four" );
        try {
            array.set( -1, "" );
            fail( "IndexOutOfBoundsException expeceted here" );
        } catch ( IndexOutOfBoundsException e ) {
            assertEquals( "-1", e.getMessage() );
        }
        
        assertEquals( 
            "DynArray [list=[null, one, null, two, null, null, tree, null, four, null], size()=4, capacity()=10]", 
            array.toString() 
        );
        
        assertFalse( array.contains( 0 ) );
        assertTrue( array.contains( 1 ) );
        assertEquals( "one", array.get( 1 ) );
        assertNull( array.get( 2 ) );
        assertEquals( "two", array.get( 3 ) );
        try {
            array.get( -1 );
            fail( "IndexOutOfBoundsException expeceted here" );
        } catch ( IndexOutOfBoundsException e ) {
            assertEquals( "-1", e.getMessage() );
        }
        try {
            array.get( 100 );
            fail( "IndexOutOfBoundsException expeceted here" );
        } catch ( IndexOutOfBoundsException e ) {
            assertEquals( "100", e.getMessage() );
        }
        
        array.set(  9, "last" );
        assertEquals( 
            "DynArray [list=[null, one, null, two, null, null, tree, null, four, last], size()=5, capacity()=10]", 
            array.toString() 
        );
        
        array.set(  10, "next" );
        assertEquals( 
            "DynArray [list=[null, one, null, two, null, null, tree, null, four, last, next, null, null, null, null, null, null, null, null, null], size()=6, capacity()=20]", 
            array.toString() 
        );
    }
    
    @Test
    public void testAddRemove() {
        DynArray<String> array = DynArray.create( String.class, 10, 5 );
        array.set( 1, "one" );
        array.set( 3, "two" );
        array.set( 6, "tree" );
        array.set( 8, "four" );
        
        assertEquals( 
            "DynArray [list=[null, one, null, two, null, null, tree, null, four, null], size()=4, capacity()=10]", 
            array.toString() 
        );
        
        assertEquals( 0, array.add( "add1" ) );
        assertEquals( 2, array.add( "add2" ) );
        assertEquals( 4, array.add( "add3" ) );
        assertEquals( 5, array.add( "add4" ) );
        assertEquals( 7, array.add( "add5" ) );
        assertEquals( 9, array.add( "add6" ) );
        
        assertEquals( 
            "DynArray [list=[add1, one, add2, two, add3, add4, tree, add5, four, add6], size()=10, capacity()=10]", 
            array.toString() 
        );
        
        assertEquals( 10, array.add( "add7" ) );
        
        assertEquals( 
            "DynArray [list=[add1, one, add2, two, add3, add4, tree, add5, four, add6, add7, null, null, null, null], size()=11, capacity()=15]", 
            array.toString() 
        );
        
        // remove
        assertEquals( 1, array.remove( "one" ) );
        assertEquals( 3, array.remove( "two" ) );
        assertEquals( 6, array.remove( "tree" ) );
        assertEquals( 8, array.remove( "four" ) );
        assertEquals( -1, array.remove( "fvssfgsfgf" ) );
        
        assertEquals( 
            "DynArray [list=[add1, null, add2, null, add3, add4, null, add5, null, add6, add7, null, null, null, null], size()=7, capacity()=15]", 
            array.toString() 
        );
    }
    
    @Test
    public void testGrow() {
        DynArray<String> array1 = DynArray.create( String.class, 10, 10 );
        DynArray<String> array2 = DynArray.create( String.class, 10, 10 );
        
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null], size()=0, capacity()=10]", 
            array1.toString() 
        );
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null], size()=0, capacity()=10]", 
            array2.toString() 
        );
        
        array1.set( 15, "15" );
        array2.set( 21, "21" );
        
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 15, null, null, null, null], size()=1, capacity()=20]", 
            array1.toString() 
        );
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 21, null, null, null, null, null, null, null, null], size()=1, capacity()=30]", 
            array2.toString() 
        );
    }
    
    @Test
    public void testSort() {
        DynArray<String> array = DynArray.create( String.class, 10 );
        array.set( 1, "one" );
        array.set( 3, "two" );
        array.set( 6, "tree" );
        array.set( 8, "four" );
        
        assertEquals( 
            "DynArray [list=[null, one, null, two, null, null, tree, null, four, null], size()=4, capacity()=10]", 
            array.toString() 
        );
        
        array.sort( new Comparator<String>() {

            @Override
            public int compare( String o1, String o2 ) {
                if ( o1 == null && o2 == null ) {
                    return 0;
                } else if ( o1 == null ) {
                    return 1;
                } else if ( o2 == null ) {
                    return -1;
                }
                return o1.compareTo( o2 );
            }
        } );
        
        assertEquals( 
            "DynArray [list=[four, one, tree, two, null, null, null, null, null, null], size()=4, capacity()=10]", 
            array.toString() 
        );
    }
    
    @Test
    public void testSetGetPerformance() {
        int operations = 100000;
        
        Random random = new Random();
        ArrayList<String> arrayList = new ArrayList<String>();
        DynArray<String> dynArray = DynArray.create( String.class );
        
        System.out.println( operations + " add/set operations on none initialized lists:" );
        
        long startTime = System.nanoTime();
        for ( int i = 0; i < operations; i++ ) {
            arrayList.add( random.nextInt( arrayList.size() + 1 ), String.valueOf( i ) );
        }
        long time = System.nanoTime() - startTime;
        
        System.out.println( "ArrayList:"  + time ); 
        
        startTime = System.nanoTime();
        for ( int i = 0; i < operations; i++ ) {
            dynArray.set( random.nextInt( operations ), String.valueOf( i ) );
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray :"  + time ); 
        
        
        
        arrayList = new ArrayList<String>( operations + 1 );
        dynArray = DynArray.create( String.class, operations + 1 );
        for ( int i = 0; i < operations + 1; i++ ) {
            arrayList.add( i, "arrayList" );
            dynArray.set( i, "dynArray" );
        }
        
        System.out.println( operations + " remove operations on initialized lists:" );
        
        startTime = System.nanoTime();
        for ( int i = 0; i < operations; i++ ) {
            arrayList.remove( random.nextInt( arrayList.size() ) );
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "ArrayList:"  + time ); 
        
        startTime = System.nanoTime();
        for ( int i = 0; i < operations; i++ ) {
            dynArray.remove( random.nextInt( operations ) );
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray :"  + time ); 
    }
    
    private final static String entry = "ENTRY";
    @Test
    public void testIteratorPerformance() {
        int operations = 10000;
        int runns = 30 * 50;
        
        ArrayList<String> arrayList = new ArrayList<String>( operations );
        DynArray<String> dynArray = DynArray.create( String.class, operations );
        StaticList<String> staticList = new StaticList<String>( operations );
        
        String[] array = new String[ operations ];
        for ( int i = 0; i < operations; i++ ) {
            arrayList.add( i, entry );
            dynArray.set( i, entry );
            array[ i ] = entry;
            staticList.set( i, entry );
        }
        
        long startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            for( String value : arrayList ) {
                if ( value != null ) {
                }
            }
        }
        long time = System.nanoTime() - startTime;
        
        System.out.println( "Iteration Performance: " + operations + " items" );
        System.out.println( "ArrayList              :"  + time );
        
        startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            for( @SuppressWarnings( "unused" ) String value : staticList ) {
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "StaticList iterator    :"  + time );
        
        startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            for( int i = 0; i < staticList.capacity(); i++ ) {
                if ( staticList.get( i ) != null ) {
                    
                }
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "StaticList             :"  + time );
        
        startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            for( int i = 0; i < array.length; i++ ) {
                if ( array[ i ] != null ) {
                    
                }
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "Array                  :"  + time );
        
        final Method<String> method = new Method<String>() {
            @Override
            public final void call( String value ) {
                // TPDP
            }
        };
        startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            dynArray.forEach( method );
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray with Method   :"  + time );
        
        startTime = System.nanoTime();
        int capacity = dynArray.capacity();
        for ( int run = 0; run < runns; run++ ) {
            for( int i = 0; i < capacity; i++ ) {
                if ( dynArray.get( i ) != null ) {
                    
                }
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray with get      :"  + time );
        
        startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            for( int i = 0; i < dynArray.capacity(); i++ ) {
                if ( dynArray.get( i ) != null ) {
                    
                }
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray with get + cap:"  + time );
        
        startTime = System.nanoTime();
        capacity = dynArray.capacity();
        for ( int run = 0; run < runns; run++ ) {
            for( int i = 0; i < capacity; i++ ) {
                if ( dynArray.contains( i ) ) {
                    
                }
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray with contains :"  + time );
        
        startTime = System.nanoTime();
        for ( int run = 0; run < runns; run++ ) {
            Iterator<String> listIterator = dynArray.iterator();
            while ( listIterator.hasNext() ) {
                if ( listIterator.next() != null ) {
                }
            }
        }
        time = System.nanoTime() - startTime;
        
        System.out.println( "DynArray listIt        :"  + time );
        
    }

}
