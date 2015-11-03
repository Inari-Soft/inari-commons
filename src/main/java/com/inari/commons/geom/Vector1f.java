/*******************************************************************************
 * Copyright (c) 2015 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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

/** A simple one dimensional vector within float precision */
public final class Vector1f implements StringConfigurable {

    /** The vector value */
    public float d;

    /** Use this to create a default Vector1f with d=1.0 */
    public Vector1f() {
        d = 1.0f;
    }

    /** Use this to create a Vector1f with specified value */
    public Vector1f( float d ) {
        this.d = d;
    }

    @Override
    public final void fromConfigString( String stringValue ) {
        d = Float.parseFloat( stringValue );
    }

    @Override
    public final String toConfigString() {
        return String.valueOf( d );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "[d=" );
        builder.append( d );
        builder.append( "]" );
        return builder.toString();
    }

    

}
