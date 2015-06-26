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

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

public final class Vector2i implements StringConfigurable {
    
    public int dx;
    public int dy;
    
    public Vector2i() {
        dx = 1;
        dy = 1;
    }
    
    public Vector2i( int dx, int dy ) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public final void fromConfigString( String stringValue ) {
        int index = stringValue.indexOf( StringUtils.VALUE_SEPARATOR );
        dx = Integer.parseInt( stringValue.substring( 0, index) );
        dy = Integer.parseInt( stringValue.substring( index + 1, stringValue.length() ) );
    }

    @Override
    public final String toConfigString() {
        return dx + StringUtils.VALUE_SEPARATOR_STRING + dy;
    }

    @Override
    public final String toString() {
        return "Vector2i:" + dx + StringUtils.VALUE_SEPARATOR_STRING + dy;
    }

}
