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


public final class Rectangle extends Position {

    public int width;
    public int height;
    
    public Rectangle() {
        super( 0, 0 );
        width = 0;
        height = 0;
    }
    
    public Rectangle ( Rectangle source ) {
        super( source.x, source.y );
        this.width = source.width;
        this.height = source.height;
    }
    
    public Rectangle( Position pos, int width, int height ) {
        super( pos );
        this.width = width;
        this.height = height;
    }
    
    public Rectangle( int xpos, int ypos, int width, int height ) {
        super( xpos, ypos );
        this.width = width;
        this.height = height;
    }
    
    public Rectangle( String rectString ) {
        StringTokenizer st = new StringTokenizer( rectString, StringUtils.VALUE_SEPARATOR_STRING );
        super.x = Integer.parseInt( st.nextToken() );
        super.y = Integer.parseInt( st.nextToken() );
        width = Integer.parseInt( st.nextToken() );
        height = Integer.parseInt( st.nextToken() );
    }
    
    public int area() {
        return width * height;
    }
    
    public void fromOther( Rectangle other ) {
        super.fromOther( other );
        width = other.width;
        height = other.height;
    }

    @Override
    public void fromConfigString( String stringValue ) {
        if ( !StringUtils.isBlank( stringValue ) ) {
            StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
            x = Integer.valueOf( st.nextToken() ).intValue();
            y = Integer.valueOf( st.nextToken() ).intValue();
            width = Integer.valueOf( st.nextToken() ).intValue();
            height = Integer.valueOf( st.nextToken() ).intValue();
        } 
    }

    @Override
    public String toConfigString() {
        StringBuilder sb = new StringBuilder();
        sb.append( x ).append( StringUtils.VALUE_SEPARATOR ).append( y ).append( StringUtils.VALUE_SEPARATOR );
        sb.append( width ).append( StringUtils.VALUE_SEPARATOR ).append( height );
        return sb.toString();
    }

    @Override
    public final int hashCode() {
        int result = 1;
        result = 37 * result + x;
        result = 37 * result + y;
        result = 37 * result + width;
        result = 37 * result + height;
        return result;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj;
            return ( 
                ( this.x == r.x ) && 
                ( this.y == r.y ) &&
                ( this.width == r.width ) &&
                ( this.height == r.height ) 
            );
        } else {
            return false;
        }
    }
    
    

    @Override
    public String toString() {
        return "Rectangle: " + toConfigString();
    }

    

}
