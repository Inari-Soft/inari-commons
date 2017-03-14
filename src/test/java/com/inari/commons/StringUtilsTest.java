package com.inari.commons;


import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;


public class StringUtilsTest {
    
    @Test
    public void isEmptyTest() throws Exception {
        Assert.assertTrue( StringUtils.isEmpty( null ) );
        Assert.assertTrue( StringUtils.isEmpty( "" ) );
        Assert.assertFalse( StringUtils.isEmpty( " " ) );
        Assert.assertFalse( StringUtils.isEmpty( "d" ) );
    }
    
    @Test
    public void isBlankTest() throws Exception {
        Assert.assertTrue( StringUtils.isBlank( null ) );
        Assert.assertTrue( StringUtils.isBlank( "" ) );
        Assert.assertTrue( StringUtils.isBlank( " " ) );
        Assert.assertFalse( StringUtils.isBlank( "d" ) );
    }

    @Test
    public void testSplit() {
        String testString = "2.3.4.5.6.76.4.3";

        Collection<String> split = StringUtils.split( testString, "." );
        assertEquals( "[2, 3, 4, 5, 6, 76, 4, 3]", String.valueOf( split ) );

        split = StringUtils.split( testString, "," );
        assertEquals( "[2.3.4.5.6.76.4.3]", String.valueOf( split ) );

        split = StringUtils.split( testString, "" );
        assertEquals( "[2.3.4.5.6.76.4.3]", String.valueOf( split ) );
        assertNull( StringUtils.split( "", null ) );
        assertNull( StringUtils.split( "", "," ) );
        assertNull( StringUtils.split( null, "," ) );
    }

    @Test
    public void testJoin() {
        Collection<String> strings = new ArrayList<String>();
        Collection<Integer> ints = new ArrayList<Integer>();
        strings.add( "one" );
        strings.add( "two" );
        strings.add( "tree" );
        ints.add( 1 );
        ints.add( 2 );
        ints.add( 3 );

        String join1 = StringUtils.join( strings, "," );
        String join2 = StringUtils.join( ints, "," );
        assertEquals( "one,two,tree", join1 );
        assertEquals( "1,2,3", join2 );

        join1 = StringUtils.join( strings, "|hello|" );
        assertEquals( "one|hello|two|hello|tree", join1 );
    }

    @Test
    public void testSplitToArray() {
        String testString = "2.3.4.5.6.76.4.3";

        String[] split = StringUtils.splitToArray( testString, "." );
        assertEquals( "[2, 3, 4, 5, 6, 76, 4, 3]", Arrays.toString( split ) );

        split = StringUtils.splitToArray( testString, "," );
        assertEquals( "[2.3.4.5.6.76.4.3]", Arrays.toString( split ) );

        split = StringUtils.splitToArray( testString, "" );
        assertEquals( "[2.3.4.5.6.76.4.3]", Arrays.toString( split ) );
        assertNull( StringUtils.splitToArray( "", null ) );
        assertNull( StringUtils.splitToArray( "", "," ) );
        assertNull( StringUtils.splitToArray( null, "," ) );
    }

    @Test
    public void testSplitToMap() {
        String testString = "v1=1,v2=2,v3=3,v6=4";

        assertNull( StringUtils.splitToMap( "", null, null ) );
        assertNull( StringUtils.splitToMap( "", ",", null ) );
        assertNull( StringUtils.splitToMap( "", null, "," ) );

        Map<String, String> split = StringUtils.splitToMap( testString, ",", "=" );
        assertEquals( "{v1=1, v2=2, v3=3, v6=4}", String.valueOf( split ) );

        split = StringUtils.splitToMap( testString, ".", "=" );
        assertEquals( "{v1=1,v2=2,v3=3,v6=4}", String.valueOf( split ) );
        Map.Entry<String, String> entry = split.entrySet().iterator().next();
        assertEquals( "v1", String.valueOf( entry.getKey() ) );
        assertEquals( "1,v2=2,v3=3,v6=4", String.valueOf( entry.getValue() ) );

        split = StringUtils.splitToMap( testString, ",", "." );
        assertEquals( "{}", String.valueOf( split ) );
    }

    @Test
    public void testFillPrepending() {
        String testString = "99";

        String result = StringUtils.fillPrepending( testString, '0', 4 );
        assertEquals( "0099", result );

        result = StringUtils.fillPrepending( testString, '0', 2 );
        assertEquals( "99", result );

        result = StringUtils.fillPrepending( testString, '0', 1 );
        assertEquals( "99", result );
    }

    @Test
    public void testFillAppending() {
        String testString = "99";

        String result = StringUtils.fillAppending( testString, '0', 4 );
        assertEquals( "9900", result );

        result = StringUtils.fillAppending( testString, '0', 2 );
        assertEquals( "99", result );

        result = StringUtils.fillAppending( testString, '0', 1 );
        assertEquals( "99", result );
    }

    @Test
    public void testArray2DToString() {
        int[][] test = new int[][] { { 1,2,3 }, { 4,5,6 }, { 7, 8, 9 } };

        String result = StringUtils.array2DToString( test );
        assertEquals( "[[1, 2, 3][4, 5, 6][7, 8, 9]]", result );

        assertNull( StringUtils.array2DToString( null ) );
    }

    @Test
    public void testBitSetFromString() {
        String testString = "00001101100110100011";

        BitSet bitSet = StringUtils.bitsetFromString( testString );
        assertEquals( "{4, 5, 7, 8, 11, 12, 14, 18, 19}", String.valueOf( bitSet ) );

        testString = "00001103400170100011";

        bitSet = StringUtils.bitsetFromString( testString );
        assertEquals( "{4, 5, 7, 8, 11, 12, 14, 18, 19}", String.valueOf( bitSet ) );

        testString =
            "0000\n"
            + "1101\n"
            + "1001\n"
            + "1010\n"
            + "0011\n";

        bitSet = StringUtils.bitsetFromString( testString );
        assertEquals( "{4, 5, 7, 8, 11, 12, 14, 18, 19}", String.valueOf( bitSet ) );

    }

    @Test
    public void testBitSetToString() {
        BitSet test = new BitSet();
        test.set( 4 );
        test.set( 5 );
        test.set( 7 );
        test.set( 8 );
        test.set( 11 );
        test.set( 12 );
        test.set( 14 );
        test.set( 18 );
        test.set( 19 );

        String result = StringUtils.bitsetToString( test, 4, 5 );
        assertEquals(
            "0000\n"
            + "1101\n"
            + "1001\n"
            + "1010\n"
            + "0011",
            result
        );

        result = StringUtils.bitsetToString( test, 20, 1 );
        assertEquals(
            "00001101100110100011",
            result
        );

        result = StringUtils.bitsetToString( test, 20, 0 );
        assertEquals(
            "",
            result
        );

        result = StringUtils.bitsetToString( test, 20, 2 );
        assertEquals(
            "00001101100110100011\n"
            + "00000000000000000000",
            result
        );

        result = StringUtils.bitsetToString( test, 5, 2 );
        assertEquals(
            "00001\n"
            + "10110",
            result
        );
    }
}
