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

/** A simple x/y axis position with integer precision.
 *  <p>
 *  Use this if a simple x/y position with a integer precision is needed */
public class Position implements StringConfigurable {

    /** The x axis value of the position */
    public int x;
    /** The y axis value of the position */
    public int y;

    /** Use this to create a new Position with 0/0 initialization. */
    public Position() {
        x = 0;
        y = 0;
    }

    /** Use this to create a new Position with specified initialization
     *
     * @param x The x axis value of the position
     * @param y The y axis value of the position
     */
    public Position( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    /** Use this to create a new Position form configuration String value.
     *
     * @param positionString configuration String value. See also fromConfigString method documentation
     */
    public Position( String positionString ) {
        fromConfigString( positionString );
    }

    /** Use this as a copy constructor */
    public Position( Position loc ) {
        setFrom( loc );
    }

    /** Use this to set the x/y axis values from specified Position p
     *  @param p the Position to get/take the attributes from
     */
    public final void setFrom( Position p ) {
        x = p.x;
        y = p.y;
    }

    /** Use this to set  the x/y axis values from specified configuration String value with the
     *  format: [xValue],[yValue].
     *
     * @param stringValue the configuration String value
     * @throws IllegalArgumentException If the String value as a invalid format
     * @throws NumberFormatException if the x/y values from the String value aren't numbers
     */
    @Override
    public void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            x = 0; y = 0;
            return;
        }

        if ( !stringValue.contains( StringUtils.VALUE_SEPARATOR_STRING ) ) {
            throw new IllegalArgumentException( "The stringValue as invalid format: " + stringValue );
        }
        
        StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
        x = Integer.valueOf( st.nextToken() ).intValue();
        y = Integer.valueOf( st.nextToken() ).intValue();
    }

    /** Use this to get a configuration String value that represents this Position
     *  and can be used to reset the attributes of a Position by using fromConfigString
     *  The format is: [xValue],[yValue].
     *  @return A configuration String value that represents this Position
     */
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
        if ( obj instanceof Position ) {
            Position p = (Position) obj;
            return ( 
                ( x == p.x ) && 
                ( y == p.y ) 
            );
        }

        return false;
    }

}
