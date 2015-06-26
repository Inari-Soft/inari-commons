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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

public class PositionPath implements StringConfigurable, Iterable<Position> {
    
    private List<Position> positionPath = new ArrayList<Position>();

    @Override
    public Iterator<Position> iterator() {
        return positionPath.iterator();
    }

    @Override
    public void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            return;
        }
        
        for ( String listEntry : StringUtils.split( stringValue, StringUtils.LIST_VALUE_SEPARATOR_STRING ) ) {
            positionPath.add( new Position( listEntry ) );
        }
    }

    @Override
    public String toConfigString() {
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

}
