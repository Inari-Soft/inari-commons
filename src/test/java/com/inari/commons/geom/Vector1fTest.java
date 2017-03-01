package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Vector1fTest {

    @Test
    public void testCreation() {
        Vector1f v = new Vector1f();
        assertEquals( "1.0", String.valueOf( v.d ) );

        v = new Vector1f( 3.45f );
        assertEquals( "3.45", String.valueOf( v.d ) );
    }

    @Test
    public void testFromToConfigString() {
        Vector1f v = new Vector1f( 2.33f );
        String configString = v.toConfigString();
        assertEquals( "2.33", configString );
        Vector1f v2 = new Vector1f();
        v2.fromConfigString( configString );
        assertEquals( "2.33", String.valueOf( v2.d ) );
        assertTrue( v.equals( v2 ) );
    }


}
