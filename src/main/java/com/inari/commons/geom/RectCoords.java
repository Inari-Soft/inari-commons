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
package com.inari.commons.geom;

import java.util.StringTokenizer;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

public class RectCoords implements StringConfigurable {
    
    public final float[][] coords;
    
    public RectCoords() {
        coords = new float[ 4 ][ 2 ];
        clear();
    }
    
    public RectCoords( Rectangle rect ) {
        this( rect.x, rect.y, rect.width, rect.height );
    }
    
    public RectCoords( float x, float y, float width, float height ) {
        coords = new float[ 4 ][ 2 ];
        setCoords(x, y, width, height);
    }

    protected RectCoords( float[][] coords ) {
        if ( ( coords.length != 4 ) || ( coords[ 0 ].length != 2 ) ) {
            throw new IllegalArgumentException( "The points array must have a dimension of [ 4 ][ 2 ]" );
        }
        this.coords = coords;
    }

    public float width() {
        return coords[ 1 ][ 0 ] - coords[ 0 ][ 0 ];
    }
    
    public float height() {
        return coords[ 2 ][ 1 ] - coords[ 0 ][ 1 ];
    }
    
    public void clear() {
        for ( int i = 0; i < coords.length; i++ ) {
            for ( int j = 0; j < coords[ i ].length; j++ ) {
                coords[ i ][ j ] = 0.0f;
            }
        }
    }
    
    public boolean intersects( RectCoords rect ) {
        if ( rect == null ) {
            return false;
        }
        // check completely left
        if ( coords[ 0 ][ 0 ] <= rect.coords[ 1 ][ 0 ] ) {
            return false;
        }
        // check completely right
        if ( coords[ 1 ][ 0 ] >= rect.coords[ 0 ][ 0 ] ) {
            return false;
        }
        // check completely above
        if ( coords[ 2 ][ 1 ] >= rect.coords[ 0 ][ 1 ] ) {
            return false;
        }
        // check completely beneath
        if ( coords[ 0 ][ 1 ] <= rect.coords[ 2 ][ 1 ] ) {
            return false;
        }
        // intersection
        return true;
    } 
    
    public boolean intersects( Rectangle rect ) {
        if ( rect == null ) {
            return false;
        }
        float x = rect.x;
        float y = rect.y;
        float x2 = rect.x + rect.width;
        float y2 = rect.y + rect.height;
        
        // check completely left
        if ( coords[ 0 ][ 0 ] <= x2 ) {
            return false;
        }
        // check completely right
        if ( coords[ 1 ][ 0 ] >= x ) {
            return false;
        }
        // check completely above
        if ( coords[ 2 ][ 1 ] >= y ) {
            return false;
        }
        // check completely beneath
        if ( coords[ 0 ][ 1 ] <= y2 ) {
            return false;
        }
        // intersection
        return true;
    }


    @Override
    public void fromConfigString( String stringValue ) {
        float x = 0;
        float y = 0;
        float width = 0;
        float height = 0;
        
        if ( !StringUtils.isBlank( stringValue ) ) {
            StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
            x = Float.valueOf( st.nextToken() ).floatValue();
            y = Float.valueOf( st.nextToken() ).floatValue();
            width = Float.valueOf( st.nextToken() ).floatValue();
            height = Float.valueOf( st.nextToken() ).floatValue();
        }
        
        setCoords( x, y, width, height );
    }

    @Override
    public String toConfigString() {
        float x = coords[ 0 ][ 0 ];
        float y = coords[ 0 ][ 1 ];
        float width = coords[ 1 ][ 0 ] - coords[ 0 ][ 0 ];
        float height = coords[ 3 ][ 1 ] - coords[ 0 ][ 1 ];
        
        return x + StringUtils.VALUE_SEPARATOR_STRING + y + StringUtils.VALUE_SEPARATOR_STRING + width + StringUtils.VALUE_SEPARATOR_STRING + height;
    } 
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "QuadCoords[\n" );
        
        for ( int i = 0; i < coords.length; i++ ) {
            builder.append( "  " ).append( "p" ).append( i ).append( "x" ).append( ":" ).append( coords[ i ][ 0 ] );
            builder.append( "  " ).append( "p" ).append( i ).append( "y" ).append( ":" ).append( coords[ i ][ 1 ] );
            builder.append( "\n" );
        }
        
        builder.append( "]\n" );
        return builder.toString();
    }
    
    private void setCoords( float x, float y, float width, float height ) {
        float x2 = x + width;
        float y2 = y + height;
        
        coords[ 0 ][ 0 ] = x;
        coords[ 0 ][ 1 ] = y;
        coords[ 1 ][ 0 ] = x2;
        coords[ 1 ][ 1 ] = y;
        coords[ 2 ][ 0 ] = x2;
        coords[ 2 ][ 1 ] = y2;
        coords[ 3 ][ 0 ] = x;
        coords[ 3 ][ 1 ] = y2;
    }

}
