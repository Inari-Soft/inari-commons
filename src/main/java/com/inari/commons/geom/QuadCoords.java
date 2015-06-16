/**
* Copyright (c) 2009-2013, Andreas Hefti, anhefti@yahoo.de 
* All rights reserved.
*
* This software is licensed to you under the Apache License, Version 2.0
* (the "License"); You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* . Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* . Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* . Neither the name "InariUtils" nor the names of its contributors may be
* used to endorse or promote products derived from this software without
* specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package com.inari.commons.geom;

import java.util.StringTokenizer;

import com.inari.commons.StringUtils;

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
        if ( getLeftX() <= coords.getRightX() ) {
            return false;
        }
        // check completely right
        if ( getRightX() >= coords.getLeftX() ) {
            return false;
        }
        // check completely above
        if ( getTopY() >= coords.getBottomY() ) {
            return false;
        }
        // check completely beneath
        if ( getBottomY() <= coords.getTopY() ) {
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
        float right = Float.MIN_VALUE;
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
        float bottom = Float.MIN_VALUE;
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
