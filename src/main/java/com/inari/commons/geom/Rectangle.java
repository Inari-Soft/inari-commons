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


public class Rectangle extends Position {

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
