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

import com.inari.commons.StringUtils;
import com.inari.commons.config.IStringConfigurable;

public final class Vector2f implements IStringConfigurable {
    
    public float dx;
    public float dy;
    
    public Vector2f() {
        dx = 1;
        dy = 1;
    }
    
    public Vector2f( float dx, float dy ) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public final void fromConfigString( String stringValue ) {
        int index = stringValue.indexOf( StringUtils.VALUE_SEPARATOR );
        dx = Float.parseFloat( stringValue.substring( 0, index) );
        dy = Float.parseFloat( stringValue.substring( index + 1, stringValue.length() ) );
    }

    @Override
    public final String toConfigString() {
        return dx + StringUtils.VALUE_SEPARATOR_STRING + dy;
    }

    @Override
    public final String toString() {
        return "Vector2f:" + dx + StringUtils.VALUE_SEPARATOR_STRING + dy;
    }

}
