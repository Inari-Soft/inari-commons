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


public final class RGBColor implements StringConfigurable {
    
    // TODO remove static references (Color is mutable so we should create always a new instance)
    
    public static final RGBColor BLACK = new RGBColor( 0f, 0f, 0f, 1f, "BLACK" );
    public static final RGBColor WHITE = new RGBColor( 1f, 1f, 1f, 1f, "WHITE" );
    public static final RGBColor RED = new RGBColor( 1f, 0f, 0f, 1f, "RED" );
    public static final RGBColor GREEN = new RGBColor( 0f, 1f, 0f, 1f, "GREEN" );
    public static final RGBColor BLU = new RGBColor( 0f, 0f, 1f, 1f, "BLU" );
    
    
    public static final RGBColor RED_01ALPHA = new RGBColor( 1f, 0f, 0f, .1f, "RED_01ALPHA" );
    public static final RGBColor RED_02ALPHA = new RGBColor( 1f, 0f, 0f, .2f, "RED_02ALPHA" );
    public static final RGBColor RED_03ALPHA = new RGBColor( 1f, 0f, 0f, .3f, "RED_03ALPHA" );
    public static final RGBColor RED_04ALPHA = new RGBColor( 1f, 0f, 0f, .4f, "RED_04ALPHA" );
    public static final RGBColor RED_05ALPHA = new RGBColor( 1f, 0f, 0f, .5f, "RED_05ALPHA" );
    public static final RGBColor RED_06ALPHA = new RGBColor( 1f, 0f, 0f, .6f, "RED_06ALPHA" );
    public static final RGBColor RED_07ALPHA = new RGBColor( 1f, 0f, 0f, .7f, "RED_07ALPHA" );
    public static final RGBColor RED_08ALPHA = new RGBColor( 1f, 0f, 0f, .8f, "RED_08ALPHA" );
    public static final RGBColor RED_09ALPHA = new RGBColor( 1f, 0f, 0f, .9f, "RED_09ALPHA" );
    
    public static final RGBColor GREEN_01ALPHA = new RGBColor( 0f, 1f, 0f, .1f, "GREEN_01ALPHA" );
    public static final RGBColor GREEN_02ALPHA = new RGBColor( 0f, 1f, 0f, .2f, "GREEN_02ALPHA" );
    public static final RGBColor GREEN_03ALPHA = new RGBColor( 0f, 1f, 0f, .3f, "GREEN_03ALPHA" );
    public static final RGBColor GREEN_04ALPHA = new RGBColor( 0f, 1f, 0f, .4f, "GREEN_04ALPHA" );
    public static final RGBColor GREEN_05ALPHA = new RGBColor( 0f, 1f, 0f, .5f, "GREEN_05ALPHA" );
    public static final RGBColor GREEN_06ALPHA = new RGBColor( 0f, 1f, 0f, .6f, "GREEN_06ALPHA" );
    public static final RGBColor GREEN_07ALPHA = new RGBColor( 0f, 1f, 0f, .7f, "GREEN_07ALPHA" );
    public static final RGBColor GREEN_08ALPHA = new RGBColor( 0f, 1f, 0f, .8f, "GREEN_08ALPHA" );
    public static final RGBColor GREEN_09ALPHA = new RGBColor( 0f, 1f, 0f, .9f, "GREEN_09ALPHA" );
    
    public static final RGBColor BLUE_01ALPHA = new RGBColor( 0f, 0f, 1f, .1f, "BLUE_01ALPHA" );
    public static final RGBColor BLUE_02ALPHA = new RGBColor( 0f, 0f, 1f, .2f, "BLUE_02ALPHA" );
    public static final RGBColor BLUE_03ALPHA = new RGBColor( 0f, 0f, 1f, .3f, "BLUE_03ALPHA" );
    public static final RGBColor BLUE_04ALPHA = new RGBColor( 0f, 0f, 1f, .4f, "BLUE_04ALPHA" );
    public static final RGBColor BLUE_05ALPHA = new RGBColor( 0f, 0f, 1f, .5f, "BLUE_05ALPHA" );
    public static final RGBColor BLUE_06ALPHA = new RGBColor( 0f, 0f, 1f, .6f, "BLUE_06ALPHA" );
    public static final RGBColor BLUE_07ALPHA = new RGBColor( 0f, 0f, 1f, .7f, "BLUE_07ALPHA" );
    public static final RGBColor BLUE_08ALPHA = new RGBColor( 0f, 0f, 1f, .8f, "BLUE_08ALPHA" );
    public static final RGBColor BLUE_09ALPHA = new RGBColor( 0f, 0f, 1f, .9f, "BLUE_09ALPHA" );
    
    public float r;
    public float g;
    public float b;
    public float a = 0;
    
    
    public RGBColor() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 1;
    }
    
    public RGBColor( float r, float g, float b ) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = -1;
    }
    
    public RGBColor( float r, float g, float b, float a ) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public RGBColor( String colorString ) {
        fromConfigString( colorString );
    }
    
    public RGBColor( RGBColor source ) {
        this.r = source.r;
        this.g = source.g;
        this.b = source.b;
        this.a = source.a;
    }
    
    protected RGBColor( float r, float g, float b, float a, String id ) {
        this( r, g, b, a );
    };
    
    public final boolean hasAlpha() {
        return a >= 0;
    }

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
    }

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

}
