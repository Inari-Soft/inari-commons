package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Vector2fTest {

    @Test
    public void testCreation() {
        Vector2f v = new Vector2f();
        assertEquals( "1.0", String.valueOf( v.dx ) );
        assertEquals( "1.0", String.valueOf( v.dy ) );

        v = new Vector2f( 3.45f, 56.3f );
        assertEquals( "3.45", String.valueOf( v.dx ) );
        assertEquals( "56.3", String.valueOf( v.dy ) );
    }

    @Test
    public void testFromToConfigString() {
        Vector2f v = new Vector2f( 2.33f, 1.1f );
        String configString = v.toConfigString();
        assertEquals( "2.33,1.1", configString );
        Vector2f v2 = new Vector2f();
        v2.fromConfigString( configString );
        assertEquals( "2.33", String.valueOf( v2.dx ) );
        assertEquals( "1.1", String.valueOf( v2.dy ) );
        assertTrue( v.equals( v2 ) );
    }
}
