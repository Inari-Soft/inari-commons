package com.inari.commons.lang.list;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DynArrayTest {

    @Test
    public void testCreation() {
        DynArray<String> array1 = new DynArray<String>();
        DynArray<String> array2 = new DynArray<String>( 10 );
        
        assertEquals( 
            "DynArray [list=[], size()=0, capacity()=0]", 
            array1.toString() 
        );
        assertEquals( 
            "DynArray [list=[null, null, null, null, null, null, null, null, null, null], size()=0, capacity()=10]", 
            array2.toString() 
        );
    }

}
