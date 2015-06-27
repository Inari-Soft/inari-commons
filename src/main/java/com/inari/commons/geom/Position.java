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


public class Position implements StringConfigurable {
    
    public int x;
    public int y;
    
    public Position() {
        x = 0;
        y = 0;
    }
    
    public Position( int x, int y ) {
        this.x = x;
        this.y = y;
    }
    
    public Position( String positionString ) {
        fromConfigString( positionString );
    }
    
    public Position( Position loc ) {
        setFrom( loc );
    }

    public final void setFrom( Position p ) {
        x = p.x;
        y = p.y;
    }
    
    public void fromOther( Position other ) {
        x = other.x;
        y = other.y;
    }

    @Override
    public void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            x = 0; y = 0;
            return;
        }
        
        StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
        x = Integer.valueOf( st.nextToken() ).intValue();
        y = Integer.valueOf( st.nextToken() ).intValue();
    }

    @Override
    public String toConfigString() {
        return x + StringUtils.VALUE_SEPARATOR_STRING + y;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "[x=" );
        builder.append( x );
        builder.append( ",y=" );
        builder.append( y );
        builder.append( "]" );
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + x;
        result = 37 * result + y;
        return result;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if (obj instanceof Position) {
            Position p = (Position) obj;
            return ( 
                ( x == p.x ) && 
                ( y == p.y ) 
            );
        } else {
            return false;
        }
    }

}
