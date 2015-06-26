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

import com.inari.commons.config.StringConfigurable;

public final class Vector1i implements StringConfigurable {
    
    public int d;
    
    public Vector1i() {
        d = 1;
    }
    
    public Vector1i( int d ) {
        this.d = d;
    }

    @Override
    public final void fromConfigString( String stringValue ) {
        d = Integer.parseInt( stringValue );
    }

    @Override
    public final String toConfigString() {
        return String.valueOf( d );
    }

    @Override
    public final String toString() {
        return "Vector1i:" + d;
    }

}
