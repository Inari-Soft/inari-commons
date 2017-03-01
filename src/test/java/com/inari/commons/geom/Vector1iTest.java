package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Vector1iTest {

    @Test
    public void testCreation() {
        Vector1i v = new Vector1i();
        assertEquals( "1", String.valueOf( v.d ) );

        v = new Vector1i( 3 );
        assertEquals( "3", String.valueOf( v.d ) );
    }

    @Test
    public void testFromToConfigString() {
        Vector1i v = new Vector1i( 2 );
        String configString = v.toConfigString();
        assertEquals( "2", configString );
        Vector1i v2 = new Vector1i();
        v2.fromConfigString( configString );
        assertEquals( "2", String.valueOf( v2.d ) );
        assertTrue( v.equals( v2 ) );
    }
}
