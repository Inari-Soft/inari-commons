package com.inari.commons.lang.indexed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;

public class IndexerTest {
    
    @Before
    public void init() {
        Indexer.clear();
    }
    
    @Test
    public void testClassTypeCompare() {
        AA aa = new AA();
        BB bb = new BB();
        Class<?> aaType = aa.getClass();
        Class<?> bbType = bb.getClass();
        assertTrue( AA.class == AA.class );
        assertTrue( AA.class == aa.getClass() );
        assertTrue( AA.class == aaType );
        assertFalse( AA.class == bbType );
        
        assertTrue( AA.class.equals( AA.class ) );
        assertTrue( AA.class.equals( aa.getClass() ) );
        assertTrue( AA.class.equals( aaType ) );
        assertFalse( AA.class.equals( bbType ) );
    }
    
    // NOTE: this test was used to find out what BitSet gives on bitset.nextClearBit( 0 ) for a bitset with full cardinality ( all bits are set to true )
    //       if it would give -1 or the next ( not already allocated bit ) index.
    //       conclusion: the next ( not already allocated bit ) index
    @Test
    public void testNextClearBitOnFullBitSet() {
        BitSet bitset = new BitSet();
        
        assertEquals( 64, bitset.size() );
        
        for ( int i = 0; i < 64; i++ ) {
            bitset.set( i );
        }
        
        assertEquals( 64, bitset.size() );
        assertEquals( 
            "{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63}", 
            bitset.toString() 
        );
        
        // !!!
        assertEquals( 64, bitset.nextClearBit( 0 ) );
        
        bitset.set( bitset.nextClearBit( 0 ) );
        assertEquals( 128, bitset.size() );
    }
    
    @Test
    public void testIndexedObjects() {
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        
        TestIndexedObject indexedObject1 = new TestIndexedObject();
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            "  class com.inari.commons.lang.indexed.TestIndexedObject : {0}\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        assertEquals( 0, indexedObject1.index() );
        assertEquals( 1, Indexer.getIndexedObjectSize( TestIndexedObject.class ) );
        
        TestIndexedObject indexedObject2 = new TestIndexedObject();
        TestIndexedObject indexedObject3 = new TestIndexedObject();
        TestIndexedObject indexedObject4 = new TestIndexedObject();
        TestIndexedObject indexedObject5 = new TestIndexedObject();
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            "  class com.inari.commons.lang.indexed.TestIndexedObject : {0, 1, 2, 3, 4}\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        assertEquals( 1, indexedObject2.index() );
        assertEquals( 2, indexedObject3.index() );
        assertEquals( 3, indexedObject4.index() );
        assertEquals( 4, indexedObject5.index() );
        assertEquals( 5, Indexer.getIndexedObjectSize( TestIndexedObject.class ) );
        
        indexedObject2.dispose();
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            "  class com.inari.commons.lang.indexed.TestIndexedObject : {0, 2, 3, 4}\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        assertEquals( -1, indexedObject2.index() );
        assertEquals( 5, Indexer.getIndexedObjectSize( TestIndexedObject.class ) );
        
        TestIndexedObject indexedObject6 = new TestIndexedObject();
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            "  class com.inari.commons.lang.indexed.TestIndexedObject : {0, 1, 2, 3, 4}\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        assertEquals( 1, indexedObject6.index() );
        assertEquals( 5, Indexer.getIndexedObjectSize( TestIndexedObject.class ) );
    }
    
    @Test
    public void testIndexedTypeInstantiation() {
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        
        AA aa1 = new AA();
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        AB ab1 = new AB();
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        AC ac1 = new AC();
        
        assertEquals( 0, aa1.index() );
        assertEquals( 1, ab1.index() );
        assertEquals( 2, ac1.index() );
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        AA aa2 = new AA();
        AB ab2 = new AB();
        AC ac2 = new AC();
        
        assertEquals( 0, aa2.index() );
        assertEquals( 1, ab2.index() );
        assertEquals( 2, ac2.index() );
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        BA ba1 = new BA();
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        BB bb1 = new BB();
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        BC bc1 = new BC();
        
        assertEquals( 0, ba1.index() );
        assertEquals( 1, bb1.index() );
        assertEquals( 2, bc1.index() );
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "    2:com.inari.commons.lang.indexed.BC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        BA ba2 = new BA();
        BB bb2 = new BB();
        BC bc2 = new BC();
        
        assertEquals( 0, ba2.index() );
        assertEquals( 1, bb2.index() );
        assertEquals( 2, bc2.index() );
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "    2:com.inari.commons.lang.indexed.BC\n" + 
            "  }\n" + 
            "}",
            Indexer.dump() 
        );
    }
    
    @Test
    public void testPreDefinition() {
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AA.class, A.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AB.class, A.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AC.class, A.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AA.class, A.class );
        Indexer.getIndexForType( AB.class, A.class );
        Indexer.getIndexForType( AC.class, A.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BA.class, B.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BB.class, B.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BC.class, B.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "    2:com.inari.commons.lang.indexed.BC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BA.class, B.class );
        Indexer.getIndexForType( BB.class, B.class );
        Indexer.getIndexForType( BC.class, B.class );

        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "    2:com.inari.commons.lang.indexed.BC\n" + 
            "  }\n" + 
            "}",
            Indexer.dump() 
        );
    }
    
    @Test
    public void testPreDefinitionWithoutIndexedType() {
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AA.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AB.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AC.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( AA.class );
        Indexer.getIndexForType( AB.class );
        Indexer.getIndexForType( AC.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BA.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BB.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BC.class );
        
        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "    2:com.inari.commons.lang.indexed.BC\n" + 
            "  }\n" + 
            "}", 
            Indexer.dump() 
        );
        
        Indexer.getIndexForType( BA.class );
        Indexer.getIndexForType( BB.class );
        Indexer.getIndexForType( BC.class );

        assertEquals( 
            "IndexProvider : {\n" + 
            " * Indexed Objects :\n" + 
            " * Indexed Types :\n" + 
            "  class com.inari.commons.lang.indexed.A : {\n" + 
            "    0:com.inari.commons.lang.indexed.AA\n" + 
            "    1:com.inari.commons.lang.indexed.AB\n" + 
            "    2:com.inari.commons.lang.indexed.AC\n" + 
            "  }\n" + 
            "  interface com.inari.commons.lang.indexed.B : {\n" + 
            "    0:com.inari.commons.lang.indexed.BA\n" + 
            "    1:com.inari.commons.lang.indexed.BB\n" + 
            "    2:com.inari.commons.lang.indexed.BC\n" + 
            "  }\n" + 
            "}",
            Indexer.dump() 
        );
    }
    
    @Test
    public void testWrongPreDefinition() {
        try {
            Indexer.getIndexForType( AA.class, B.class );
            fail( "IllegalArgumentException expected" );
        } catch ( IllegalArgumentException e ) {
            assertEquals( "IndexedType missmatch: indexedType:class com.inari.commons.lang.indexed.AA is not a valid substitute of indexedBaseType:interface com.inari.commons.lang.indexed.B", e.getMessage() );
        } 
        
        try {
            Indexer.getIndexForType( BA.class, A.class );
            fail( "IllegalArgumentException expected" );
        } catch ( IllegalArgumentException e ) {
            assertEquals( "IndexedType missmatch: indexedType:class com.inari.commons.lang.indexed.BA is not a valid substitute of indexedBaseType:class com.inari.commons.lang.indexed.A", e.getMessage() );
        } 
    }
    
    @Test
    public void testCheckIndexedType() {
        Indexer.checkIndexedType( AA.class, A.class );
        Indexer.checkIndexedType( AA.class, AA.class );
        
        try {
            Indexer.checkIndexedType( A.class, A.class );
            fail( "Exception expected" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "indexedType: class com.inari.commons.lang.indexed.A is not an instantiable class", iae.getMessage() );
        }
        
        try {
            Indexer.checkIndexedType( A.class, AA.class );
            fail( "Exception expected" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "indexedType: class com.inari.commons.lang.indexed.A is not an instantiable class", iae.getMessage() );
        }
        
        Indexer.checkIndexedType( BB.class, B.class );
        Indexer.checkIndexedType( BB.class, BB.class );
        
        try {
            Indexer.checkIndexedType( B.class, B.class );
            fail( "Exception expected" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "indexedType: interface com.inari.commons.lang.indexed.B is not an instantiable class", iae.getMessage() );
        }
        
        try {
            Indexer.checkIndexedType( B.class, BB.class );
            fail( "Exception expected" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "indexedType: interface com.inari.commons.lang.indexed.B is not an instantiable class", iae.getMessage() );
        }
        
        
        
        try {
            Indexer.checkIndexedType( AA.class, B.class );
            fail( "Exception expected" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "IndexedType missmatch: indexedType:class com.inari.commons.lang.indexed.AA is not a valid substitute of indexedBaseType:interface com.inari.commons.lang.indexed.B", iae.getMessage() );
        }
        
        try {
            Indexer.checkIndexedType( BC.class, A.class );
            fail( "Exception expected" );
        } catch ( IllegalArgumentException iae ) {
            assertEquals( "IndexedType missmatch: indexedType:class com.inari.commons.lang.indexed.BC is not a valid substitute of indexedBaseType:class com.inari.commons.lang.indexed.A", iae.getMessage() );
        }
    }
}
