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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

/** A simple list of {@link Position} that defines a path of {@link Position{@link  in a two dimensional
 *  coordinate system with integer precision.
 *  <p>
 *  Use this if a path of Positions is needed in a two dimensional coordinate system
 *  with integer precision.
 */
public final class PositionPath implements StringConfigurable, Iterable<Position> {
    
    private List<Position> positionPath = new ArrayList<Position>();

    /** Get the {@link Position} Iterator to iterate through the path. */
    @Override
    public Iterator<Position> iterator() {
        return positionPath.iterator();
    }

    public final void add( Position p ) {
        positionPath.add( p );
    }

    public final void add( int index, Position p ) {
        if ( index < 0 ) {
            index = 0;
        }

        if ( index >= positionPath.size() ) {
            positionPath.add( p );
            return;
        }

        positionPath.add( index, p );
    }

    public boolean remove( int index ) {
        if ( index < 0 || index >= positionPath.size() ) {
            return false;
        }

        positionPath.remove( index );
        return true;
    }

    /** Use this to set the path elemements from a specified configuration String value
     *  with the format: [xValue1],[yValue1]|[xValue2],[yValue2]|[xValue3],[yValue3]|...
     *
     * @param stringValue the configuration String value
     * @throws IllegalArgumentException If the String value as a invalid format
     * @throws NumberFormatException if the x/y values from the String value aren't numbers
     */
    @Override
    public final void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            return;
        }
        
        for ( String listEntry : StringUtils.split( stringValue, StringUtils.LIST_VALUE_SEPARATOR_STRING ) ) {
            positionPath.add( new Position( listEntry ) );
        }
    }

    /** Use this to get a configuration String value that represents this PositionPath
     *  and can be used to reset the attributes of a PositionPath by using fromConfigString
     *  The format is: [xValue1],[yValue1]|[xValue2],[yValue2]|[xValue3],[yValue3]|...
     *  @return A configuration String value that represents this PositionPath
     */
    @Override
    public final String toConfigString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Position> ip = positionPath.iterator();
        while ( ip.hasNext() ) {
            Position p = ip.next();
            sb.append( p.x ).append( StringUtils.VALUE_SEPARATOR ).append( p.y );
            if ( ip.hasNext() ) {
                sb.append( StringUtils.LIST_VALUE_SEPARATOR );
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer( "PositionPath{" );
        sb.append( "positionPath=" ).append( positionPath );
        sb.append( '}' );
        return sb.toString();
    }
}
