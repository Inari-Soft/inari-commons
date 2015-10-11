/*******************************************************************************
 * Copyright (c) 2015, Andreas Hefti, inarisoft@yahoo.de 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.inari.commons.graphics;

import java.util.StringTokenizer;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

/** A simple color value with read/green/blue and alpha value in float precision.
 *  
 *  Use this if a simple string value configurable color value within float precision is needed.
 *  
 * @author andreashefti
 *
 */
public final class RGBColor implements StringConfigurable {
    
    /** Some predefined color values */
    public static enum Colors {
        BLACK( new RGBColor( 0f, 0f, 0f, 1f ) ),
        WHITE( new RGBColor( 1f, 1f, 1f, 1f ) ),
        RED( new RGBColor( 1f, 0f, 0f, 1f ) ),
        GREEN ( new RGBColor( 0f, 1f, 0f, 1f ) ),
        BLU( new RGBColor( 0f, 0f, 1f, 1f ) )
        ;
        private RGBColor prototype;

        private Colors( RGBColor prototype ) {
            this.prototype = prototype;
        }
        
        public RGBColor get() {
            return new RGBColor( prototype );
        }
    }

    /** The red ratio value of the color */
    public float r;
    /** The green ratio value of the color */
    public float g;
    /** The blue ratio value of the color */
    public float b;
    /** The alpha ratio value of the color */
    public float a = 0;
    
    /** Create new RGBColor with r/g/b = 0.0f and alpha = 1.0f */
    public RGBColor() {
        this.r = 0.0f;
        this.g = 0.0f;
        this.b = 0.0f;
        this.a = 1.0f;
    }

    /** Create new RGBColor with specified r/g/b ratio values and no alpha (-1.0f) 
     * @param r The red ratio value of the color: 0 - 1
     * @param g The green ratio value of the color: 0 - 1
     * @param b The blue ratio value of the color: 0 - 1
     */
    public RGBColor( float r, float g, float b ) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = -1.0f;
        adjustValues();
    }
    
    /** Create new RGBColor with specified r/g/b/a ratio values
     * @param r The red ratio value of the color: 0 - 1
     * @param g The green ratio value of the color: 0 - 1
     * @param b The blue ratio value of the color: 0 - 1
     * @param a The alpha ratio value of the color: 0 - 1
     */
    public RGBColor( float r, float g, float b, float a ) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        adjustValues();
    }
    
    /** Create a RGBColor form specified configuration string value.
     *  @see RGBColor.fromConfigString
     *  @param colorString RGBColor configuration string value
     */
    public RGBColor( String colorString ) {
        fromConfigString( colorString );
    }
    
    /** Copy constructor */
    public RGBColor( RGBColor source ) {
        this.r = source.r;
        this.g = source.g;
        this.b = source.b;
        this.a = source.a;
    }
    
    /** Indicates if this RGBColor has an alpha ratio value (a >= 0)
     *  @return true if this RGBColor has an alpha ratio value (a >= 0)
     */
    public final boolean hasAlpha() {
        return a >= 0;
    }
    
    /** Adjust the ration values to be between 0.0 and 1,0
     *  Except the a ration values is only adjust to the upper bound value (negative a value means no alpha)
     */
    public final void adjustValues() {
        if ( r < 0.0f )
            r = 0.0f;
        if ( r > 1.0f )
            r = 1.0f;
        if ( g < 0.0f )
            g = 0.0f;
        if ( g > 1.0f )
            g = 1.0f;
        if ( b < 0.0f )
            b = 0.0f;
        if ( b > 1.0f )
            b = 1.0f;
        if ( a > 1.0f )
            a = 1.0f;
    }
    
    public final int getRGBA8888() {
        return ( (int) ( r * 255 ) << 24 ) | ( (int) ( g * 255 ) << 16 ) | ( (int) ( b * 255 ) << 8 ) | (int) ( a * 255 );
    }
    
    public final int getRGB8888() {
        return ( (int) ( r * 255 ) << 24 ) | ( (int) ( g * 255 ) << 16 ) | ( (int) ( b * 255 ) << 8 ) | 255;
    }

    /** Use this to set the RBColor attributes from specified configuration String value with the
     *  format: [r],[g],[b](,[a]).
     *
     * @param stringValue the configuration String value
     * @throws IllegalArgumentException If the String value as a invalid format
     * @throws NumberFormatException if the r/g/b/a values from the String value aren't valid float numbers
     */
    @Override
    public final void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            r = 0; g = 0; b = 0; a = 1;
            return;
        }

        StringTokenizer st = new StringTokenizer( stringValue, "," );
        r = Float.valueOf( st.nextToken() ).floatValue();
        g = Float.valueOf( st.nextToken() ).floatValue();
        b = Float.valueOf( st.nextToken() ).floatValue();
        if ( st.hasMoreTokens() ) {
            a = Float.valueOf( st.nextToken() ).floatValue();
        } else {
            a = -1;
        }
        adjustValues();
    }

    /** Use this to get a configuration String value that represents this RGBColor
     *  and can be used to set the attributes of a RGBColor by using fromConfigString
     *  The format is: [r],[g],[b](,[a]).
     *  @return A configuration String value that represents this RGBColor
     */
    @Override
    public final String toConfigString() {
        StringBuilder sb = new StringBuilder();
        sb.append( r ).append( "," );
        sb.append( g ).append( "," );
        sb.append( b );
        if ( hasAlpha () ) {
            sb.append( "," ).append( a );
        }
        return sb.toString();
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "[r=" );
        builder.append( r );
        builder.append( ",g=" );
        builder.append( g );
        builder.append( ",b=" );
        builder.append( b );
        builder.append( ",a=" );
        builder.append( a );
        builder.append( "]" );
        return builder.toString();
    }
    
    /** Create new RGBColor with specified r/g/b ratio values and no alpha (-1.0f) 
     * @param r The red ratio value of the color: 0 - 255
     * @param g The green ratio value of the color: 0 - 255
     * @param b The blue ratio value of the color: 0 - 255
     */
    public static final RGBColor create( int r, int g, int b ) {
        return new RGBColor( r / 255f, g / 255f, b / 255f );
    }
    
    /** Create new RGBColor with specified r/g/b/a ratio values
     * @param r The red ratio value of the color: 0 - 255
     * @param g The green ratio value of the color: 0 - 255
     * @param b The blue ratio value of the color: 0 - 255
     * @param a The alpha ratio value of the color: 0 - 255
     */
    public static final RGBColor create( int r, int g, int b, int a ) {
        return new RGBColor( r / 255f, g / 255f, b / 255f, a / 255f );
    }

}
