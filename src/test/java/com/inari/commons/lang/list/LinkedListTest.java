package com.inari.commons.lang.list;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.inari.commons.lang.list.LinkedList;

public class LinkedListTest {

    @Test
    public void testLListCreation() throws Exception {
        
        LinkedList<String> list = new LinkedList<String>();
        
        Assert.assertTrue( list.isEmpty() );
        Assert.assertEquals( 0, list.size() );
    }
    
    @Test
    public void testLListAdd() throws Exception {
        LinkedList<String> list = new LinkedList<String>();
        
        list.addFirst( "B" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 1, list.size() );
        Assert.assertEquals( "|B|", list.toString( true ) );

        list.addFirst( "A" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 2, list.size() );
        Assert.assertEquals( "|A|B|", list.toString( true ) );
        
        list.addLast( "C" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 3, list.size() );
        Assert.assertEquals( "|A|B|C|", list.toString( true ) );
    }
    
    @Test
    public void testLListIterator() throws Exception {
        LinkedList<String> list1 = new LinkedList<String>();
        list1.addLast( "A" );
        list1.addLast( "B" );
        list1.addLast( "C" );
        
        Iterator<String> listIt = list1.iterator();
        
        Assert.assertNotNull( listIt );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "A", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "B", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "C", listIt.next() );
        Assert.assertFalse( listIt.hasNext() );
        
        LinkedList<String> list2 = new LinkedList<String>();
        list2.addLast( "X" );
        list2.addLast( "Y" );
        list2.addLast( "Z" );
        
        list1.addLast( list2 );
        
        listIt = list1.iterator();
        
        Assert.assertNotNull( listIt );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "A", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "B", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "C", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "X", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "Y", listIt.next() );
        Assert.assertTrue( listIt.hasNext() );
        Assert.assertEquals( "Z", listIt.next() );
        Assert.assertFalse( listIt.hasNext() );
        
        for ( String value : list1 ) {
            Assert.assertFalse( value == null );
        }
        
    }
    
    @Test
    public void testLListGet() throws Exception {
        
        LinkedList<String> list = new LinkedList<String>();
        list.addLast( "A" );
        list.addLast( "B" );
        list.addLast( "C" );
        
        Assert.assertEquals( "A", list.getAt( 0 ) );
        Assert.assertEquals( "B", list.getAt( 1 ) );
        Assert.assertEquals( "C", list.getAt( 2 ) );
        
        Assert.assertEquals( null, list.getAt( 3 ) );
        Assert.assertEquals( null, list.getAt( -1 ) );
        
    }
    
    @Test
    public void testLListInsert() throws Exception {
        LinkedList<String> list = new LinkedList<String>();
        
        list.insert( 0, "A" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 1, list.size() );
        Assert.assertEquals( "|A|", list.toString( true ) );
        
        list.insert( 1, "C" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 2, list.size() );
        Assert.assertEquals( "|A|C|", list.toString( true ) );
        
        list.insert( 1, "B" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 3, list.size() );
        Assert.assertEquals( "|A|B|C|", list.toString( true ) );
        
        list.insert( 100, "D" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 4, list.size() );
        Assert.assertEquals( "|A|B|C|D|", list.toString( true ) );
        
        list.insert( -1 , "a" );
        
        Assert.assertFalse( list.isEmpty() );
        Assert.assertEquals( 5, list.size() );
        Assert.assertEquals( "|a|A|B|C|D|", list.toString( true ) );
    }
    
    @Test
    public void testLListListAddAndRemove() throws Exception {
        LinkedList<String> list1 = new LinkedList<String>();
        LinkedList<String> list2 = new LinkedList<String>();
        LinkedList<String> list3 = new LinkedList<String>();
        
        list1.addLast( "A" );
        list1.addLast( "B" );
        list1.addLast( "C" );
        
        list2.addLast( "D" );
        list2.addLast( "E" );
        list2.addLast( "F" );
        
        list3.addLast( "G" );
        list3.addLast( "H" );
        list3.addLast( "I" );
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list1.addLast( list2 );
        
        Assert.assertEquals( "|A|B|C|D|E|F|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list2.addLast( list3 );
        
        Assert.assertEquals( "|A|B|C|D|E|F|G|H|I|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|G|H|I|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list2.remove();
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|G|H|I|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list3.remove();
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list1.addLast( list2 );
        list1.addLast( list3 );
        
        Assert.assertEquals( "|A|B|C|D|E|F|G|H|I|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list2.remove();
        list3.remove();
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|D|E|F|", list2.toString( true ) );
        Assert.assertEquals( "|G|H|I|", list3.toString( true ) );
        
        list3.addFirst( list2 );
        list2.addFirst( list1 );
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|A|B|C|D|E|F|", list2.toString( true ) );
        Assert.assertEquals( "|A|B|C|D|E|F|G|H|I|", list3.toString( true ) );
    }
    
    @Test
    public void testLListListInsert() throws Exception {
        LinkedList<String> list1 = new LinkedList<String>();
        LinkedList<String> list2 = new LinkedList<String>();
        LinkedList<String> list3 = new LinkedList<String>();
        
        list1.addLast( "A" );
        list1.addLast( "B" );
        list1.addLast( "C" );
        
        list2.addLast( "1" );
        list2.addLast( "2" );
        list2.addLast( "3" );
        
        list3.addLast( "I" );
        list3.addLast( "II" );
        list3.addLast( "III" );
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
        
        list1.insert( 1, list2 );
        
        Assert.assertEquals( "|A|1|2|3|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
        
        list2.insert( 2, list3 );
        
        Assert.assertEquals( "|A|1|2|I|II|III|3|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|I|II|III|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
        
        list2.remove();
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|I|II|III|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
        
        list3.remove();
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
        
        // NOTE: special case, insert a list in a list that has already an inserted list!
        list1.insert( 1, list2 );
        list1.insert( 3, list3 );
        
        Assert.assertEquals( "|A|1|2|I|II|III|3|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|I|II|III|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
        
        list2.remove();
        
        Assert.assertEquals( "|A|B|C|", list1.toString( true ) );
        Assert.assertEquals( "|1|2|I|II|III|3|", list2.toString( true ) );
        Assert.assertEquals( "|I|II|III|", list3.toString( true ) );
    }
    
//    @Test
//    public void testLListPerformance() throws Exception {
//        LinkedList<String> list = new LinkedList<String>();
//        
//        long startLLFill = System.currentTimeMillis();
//        for ( int i = 0; i < 1000; i++ ) {
//            list.addLast( "A" );
//        }
//        long endLLFill = System.currentTimeMillis();
//        
//        long startLLIt = System.currentTimeMillis();
//        int count = 0;
//        for ( String value : list ) {
//            count++;
//            String val = value;
//        }
//        long endLLIt = System.currentTimeMillis();
//        
//        ArrayList<String> list1 = new ArrayList<String>();
//        
//        long startALFill = System.currentTimeMillis();
//        for ( int i = 0; i < 1000; i++ ) {
//            list1.add( "A" );
//        }
//        long endALFill = System.currentTimeMillis();
//        
//        long startALIt = System.currentTimeMillis();
//        int count1 = 0;
//        for ( String value : list1 ) {
//            count1++;
//            String val = value;
//        }
//        long endALIt = System.currentTimeMillis();
//        
//        System.out.println("fill LinkedList with 1000000 nodes: " + ( endLLFill - startLLFill ) );
//        System.out.println("fill ArrayList with 1000000 nodes: " + ( endALFill - startALFill ) );
//        
//        System.out.println("Iterate LinkedList with 1000000 nodes: " + ( endLLIt - startLLIt ) );
//        System.out.println("Iterate ArrayList with 1000000 nodes: " + ( endALIt - startALIt ) );
//    }
    
}
