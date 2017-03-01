package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Vector2iTest {

    @Test
    public void testCreation() {
        Vector2i v = new Vector2i();
        assertEquals( "1", String.valueOf( v.dx ) );
        assertEquals( "1", String.valueOf( v.dy ) );

        v = new Vector2i( 3, 56 );
        assertEquals( "3", String.valueOf( v.dx ) );
        assertEquals( "56", String.valueOf( v.dy ) );
    }

    @Test
    public void testFromToConfigString() {
        Vector2i v = new Vector2i( 2, 1 );
        String configString = v.toConfigString();
        assertEquals( "2,1", configString );
        Vector2i v2 = new Vector2i();
        v2.fromConfigString( configString );
        assertEquals( "2", String.valueOf( v2.dx ) );
        assertEquals( "1", String.valueOf( v2.dy ) );
        assertTrue( v.equals( v2 ) );
    }
}
