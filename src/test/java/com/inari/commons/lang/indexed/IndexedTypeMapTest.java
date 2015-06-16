package com.inari.commons.lang.indexed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IndexedTypeMapTest {
    
    @Test
    public void testCreationAndEmpty() {
        IndexedTypeMap<String> map = new IndexedTypeMap<String>( A.class, String.class );
        
        assertEquals( 
            "IndexedTypeMap [indexedType=A, valueType=String, size=0\n" + 
            "  map: {\n" + 
            "  }\n" + 
            "]", 
            map.toString() 
        );
    }
    
    @Test
    @SuppressWarnings( "unused" )
    public void testPut() {
        
        // NOTE: this is to ensure the index order (in IndexPorvider) for this test is always the same.
        AA aa = new AA();
        AB ab = new AB();
        AC ac = new AC();
        
        IndexedTypeMap<String> map = new IndexedTypeMap<String>( A.class, String.class );
        
        
        map.put( AA.class, "AA Indexed" );
        
        assertEquals( 
            "IndexedTypeMap [indexedType=A, valueType=String, size=1\n" + 
            "  map: {\n" + 
            "    AA:AA Indexed\n" + 
            "  }\n" + 
            "]", 
            map.toString() 
        );
        
        map.put( AC.class, "AC Indexed" );
        
        assertEquals( 
            "IndexedTypeMap [indexedType=A, valueType=String, size=2\n" + 
            "  map: {\n" + 
            "    AA:AA Indexed\n" + 
            "    AC:AC Indexed\n" + 
            "  }\n" + 
            "]", 
            map.toString() 
        );
        
        map.put( AB.class, "AB Indexed" );
        
        assertEquals( 
            "IndexedTypeMap [indexedType=A, valueType=String, size=3\n" + 
            "  map: {\n" + 
            "    AA:AA Indexed\n" + 
            "    AB:AB Indexed\n" + 
            "    AC:AC Indexed\n" + 
            "  }\n" + 
            "]", 
            map.toString() 
        );
        
        IndexedTypeMap<String> map1 = new IndexedTypeMap<String>( A.class, String.class );
        IndexedTypeMap<String> map2 = new IndexedTypeMap<String>( A.class, String.class );
        
        map1.put( AA.class, "POSITION_0" );
        map1.put( AC.class, "POSITION_2" );
        
        map2.put( 0, "POSITION_0" );
        map2.put( 2, "POSITION_2" );
        
        assertEquals( map1.toString(), map2.toString() );
        
        // Test try to put wrong type
        IndexedTypeMap<String> map3 = new IndexedTypeMap<String>( A.class, String.class );
        try {
            map3.put( BA.class, "WrongType" ); // The type is wrong because it is not a substitute of A, the base type of the map
            fail( "Expecting Exception here!" );
        } catch ( Exception e ) {
            assertEquals( 
                "IndexedType missmatch: indexType:class com.inari.commons.lang.indexed.BA is not a valid substitute of indexedType:class com.inari.commons.lang.indexed.A", 
                e.getMessage() 
            );
        }
    }

}
