/*******************************************************************************
 * Copyright (c) 2015 - 2016 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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

@Deprecated
public class QuadCoords extends RectCoords {
    
    public QuadCoords() {
        super();
    }
    
    public QuadCoords( Rectangle rect ) {
        super( rect );
    }
    
    public QuadCoords( float x, float y, float width, float height ) {
        super( x, y, width, height );
    }
    
    public QuadCoords( float[][] points ) {
        super( points );
    }
    
    
    @Override
    public final float width() {
        return getRightX() - getLeftX();
    }

    @Override
    public final float height() {
        return getBottomY() - getTopY();
    }

    @Override
    public final boolean intersects( RectCoords rect ) {
        // check completely left
        if ( getLeftX() <= rect.coords[ 1 ][ 0 ] ) {
            return false;
        }
        // check completely right
        if ( getRightX() >= rect.coords[ 0 ][ 0 ] ) {
            return false;
        }
        // check completely above
        if ( getTopY() >= rect.coords[ 0 ][ 1 ] ) {
            return false;
        }
        // check completely beneath
        if ( getBottomY() <= rect.coords[ 2 ][ 1 ] ) {
            return false;
        }
        // intersection
        return true;
    }

    public final boolean intersects( QuadCoords coords ) {
        // check completely left
        if ( coords.getRightX() <= getLeftX()  ) {
            return false;
        }
        // check completely right
        if ( coords.getLeftX() >= getRightX() ) {
            return false;
        }
        // check completely above
        if ( coords.getBottomY() <= getTopY() ) {
            return false;
        }
        // check completely beneath
        if ( coords.getTopY() >= getBottomY() ) {
            return false;
        }
        // intersection
        return true;
    }

    public final float getLeftX() {
        float left = Float.MAX_VALUE;
        for ( int i = 0; i < 4; i++ ) {
            if ( coords[ i ][ 0 ] < left ) {
                left = coords[ i ][ 0 ];
            }
        }
        return left;
    }
    
    public final float getRightX() {
        float right = -Float.MAX_VALUE;
        for ( int i = 0; i < 4; i++ ) {
            if ( coords[ i ][ 0 ] > right ) {
                right = coords[ i ][ 0 ];
            }
        }
        return right;
    }
    
    public final float getTopY() {
        float top = Float.MAX_VALUE;
        for ( int i = 0; i < 4; i++ ) {
            if ( coords[ i ][ 1 ] < top ) {
                top = coords[ i ][ 1 ];
            }
        }
        return top;
    }
    
    public final float getBottomY() {
        float bottom = -Float.MAX_VALUE;
        for ( int i = 0; i < 4; i++ ) {
            if ( coords[ i ][ 1 ] > bottom ) {
                bottom = coords[ i ][ 1 ];
            }
        }
        return bottom;
    }

    @Override
    public void fromConfigString(String stringValue) {
        StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
        for ( int i = 0; i < coords.length; i++ ) {
            for ( int j = 0; j < coords[ i ].length; j++ ) {
                coords[ i ][ j ] = Float.valueOf( st.nextToken() ).floatValue();
            }
        }
    }

    @Override
    public String toConfigString() {
        StringBuilder builder = new StringBuilder();
        
        for ( int i = 0; i < coords.length; i++ ) {
            for ( int j = 0; j < coords[ i ].length; j++ ) {
                builder.append( coords[ i ][ j ] ).append( StringUtils.VALUE_SEPARATOR_STRING );
            }
        }
        
        return builder.toString();
    }

    

}
