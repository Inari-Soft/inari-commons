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
        return "Position: " + toConfigString();
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
