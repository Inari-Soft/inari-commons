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

public enum Direction {
    
    NONE ( Horizontal.NONE, Vertical.NONE ),                    // No Direction     : NONE
    NORTH ( Horizontal.NONE, Vertical.UP ),                     // North            : UP
    NORTH_EAST ( Horizontal.RIGHT, Vertical.UP ),               // North/East       : RIGHT/UP
    EAST ( Horizontal.RIGHT, Vertical.NONE ),                   // East             : RIGHT
    SOUTH_EAST ( Horizontal.RIGHT, Vertical.DOWN ),             // South/East       : RIGHT/DOWN
    SOUTH ( Horizontal.NONE, Vertical.DOWN ),                   // South            : DOWN
    SOUTH_WEST ( Horizontal.LEFT, Vertical.DOWN ),              // South/West       : LEFT/DOWN
    WEST ( Horizontal.LEFT, Vertical.NONE ),                    // West             : LEFT
    NORTH_WEST ( Horizontal.LEFT, Vertical.UP )                 // North/West       : LEFT/UP
    ; 
    
    public static enum Horizontal { NONE, RIGHT, LEFT };
    public static enum Vertical { NONE, UP, DOWN };
    
    public final Horizontal xDir;
    public final Vertical yDir;
    
    private Direction( Horizontal xDir, Vertical yDir ) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

}
