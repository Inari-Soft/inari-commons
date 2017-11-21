package com.inari.commons.lang;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import org.junit.Test;

public class TypedKeyTest {

    @Test
    public void test() {
        try {
            TypedKey.create( null, null );
            fail( "IllegalArgumentException expected here" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "id is null", iae.getMessage() );
        }

        try {
            TypedKey.create( "name", null );
            fail( "IllegalArgumentException expected here" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "type is null", iae.getMessage() );
        }

        @SuppressWarnings( "rawtypes" )
        TypedKey<ArrayList> key = TypedKey.create( "name", ArrayList.class );
        assertEquals( "name:ArrayList", key.toString() );

        TypedKey<String> key1 = TypedKey.create( "name", String.class );
        TypedKey<String> key2 = TypedKey.create( "name2", String.class );
        TypedKey<Integer> key3 = TypedKey.create( "name", Integer.class );
        TypedKey<String> key4 = TypedKey.create( "name", String.class );

        assertFalse( key1.equals( key2 ) );
        assertFalse( key1.equals( key3 ) );
        assertFalse( key2.equals( key3 ) );
        assertTrue( key1.equals( key1 ) );
        assertTrue( key1.equals( key4 ) );

        Object str1 = "fhvnoifhv";
        Object int1 = new Integer( 1 );

        String str2 = key1.cast( str1 );
        assertEquals( str1, str2 );

        try {
            String intToStringCast = key1.cast( int1 );
            fail( "ClassCastException expected here" );
        } catch ( ClassCastException cce ) {
            assertEquals( "Cannot cast java.lang.Integer to java.lang.String", cce.getMessage() );
        }

        assertTrue( key1.hashCode() == key4.hashCode() );

        assertEquals( String.class, key1.type() );
        assertEquals( "name", key1.id() );
    }
}
