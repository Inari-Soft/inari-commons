package com.inari.commons.graphics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RGBColorTest {
    
    @Test
    public void testCreation() {
        RGBColor color = new RGBColor();
        assertEquals( "[r=0.0,g=0.0,b=0.0,a=1.0]", color.toString() );
        assertTrue( color.hasAlpha() );
        color = new RGBColor( .5f, .3f, .4f );
        assertEquals( "[r=0.5,g=0.3,b=0.4,a=-1.0]", color.toString() );
        assertFalse( color.hasAlpha() );
        color = new RGBColor( .5f, .3f, .4f, .6f );
        assertEquals( "[r=0.5,g=0.3,b=0.4,a=0.6]", color.toString() );
        // out of range --> range correction
        color = new RGBColor( 500.5f, -.3f, .4f, .6f );
        assertEquals( "[r=1.0,g=0.0,b=0.4,a=0.6]", color.toString() );
        
        color = RGBColor.create( 100, 100, 200 );
        assertEquals( "[r=0.39215687,g=0.39215687,b=0.78431374,a=-1.0]", color.toString() );
        // out of range --> range correction
        color = RGBColor.create( 100, -100, 500, 20 );
        assertEquals( "[r=0.39215687,g=0.0,b=1.0,a=0.078431375]", color.toString() );
    }
    
    @Test
    public void testFromToConfigString() {
        RGBColor color = new RGBColor( .5f, .3f, .4f, .6f );
        String configString = color.toConfigString();
        assertEquals( "0.5,0.3,0.4,0.6", configString );
        
        RGBColor color2 = new RGBColor();
        color2.fromConfigString( configString );
        assertEquals( "[r=0.5,g=0.3,b=0.4,a=0.6]", color2.toString() );
        assertEquals( color, color2 );
    }
    
    @Test
    public void testRGBA8888() {
        RGBColor color = new RGBColor( .5f, .3f, .4f, .6f );
        assertEquals( "2135713535", String.valueOf( color.getRGB8888() ) );
        assertEquals( "2135713433", String.valueOf( color.getRGBA8888() ) );
    }

}
