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

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

/** A simple two dimensional (x/y) vector within float precision */
public final class Vector2f implements StringConfigurable {

    /** The vector value on x direction */
    public float dx;
    /** The vector value on y direction */
    public float dy;

    /** Use this to create a default Vector2f with dx=1.0 and dy=1.0 */
    public Vector2f() {
        dx = 1.0f;
        dy = 1.0f;
    }

    /** Use this to create a Vector2f with specified values
     *
     * @param dx the x direction value of the new Vector2f
     * @param dy the y direction value of the new Vector2f
     */
    public Vector2f( float dx, float dy ) {
        this.dx = dx;
        this.dy = dy;
    }

    /** Use this to set the Vector2f attributes from specified configuration String value with the
     *  format: [dx],[dy].
     *
     * @param stringValue the configuration String value
     * @throws IllegalArgumentException If the String value as a invalid format
     * @throws NumberFormatException if the dx/dy values from the String value aren't numbers
     */
    @Override
    public final void fromConfigString( String stringValue ) {
        int index = stringValue.indexOf( StringUtils.VALUE_SEPARATOR );
        dx = Float.parseFloat( stringValue.substring( 0, index) );
        dy = Float.parseFloat( stringValue.substring( index + 1, stringValue.length() ) );
    }

    /** Use this to get a configuration String value that represents this Vector2f
     *  and can be used to reset the attributes of a Vector2f by using fromConfigString
     *  The format is: [dx],[dy].
     *  @return A configuration String value that represents this Vector2f
     */
    @Override
    public final String toConfigString() {
        return dx + StringUtils.VALUE_SEPARATOR_STRING + dy;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o )
            return true;
        if( o == null || getClass() != o.getClass() )
            return false;

        Vector2f vector2f = (Vector2f) o;

        if( Float.compare( vector2f.dx, dx ) != 0 )
            return false;
        return Float.compare( vector2f.dy, dy ) == 0;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "[dx=" );
        builder.append( dx );
        builder.append( ",dy=" );
        builder.append( dy );
        builder.append( "]" );
        return builder.toString();
    }

    

}
