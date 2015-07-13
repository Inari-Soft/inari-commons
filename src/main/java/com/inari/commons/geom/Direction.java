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

/** Enum for discrete direction with eight directions and a NONE constant
 *  containing also a Horizontal and Vertical enum value that divides the
 *  Direction into a horizontal and vertical part.
 *
 *  Use this if you have a discrete direction with eight different directions,
 *  for example, for input, move, or other things.
 */
public enum Direction {

    /** No Direction with also Horizontal.NONE and Vertical.NONE */
    NONE ( Horizontal.NONE, Vertical.NONE ),                    // No Direction     : NONE
    /** North Direction with Horizontal.NONE and Vertical.UP */
    NORTH ( Horizontal.NONE, Vertical.UP ),                     // North            : UP
    /** North East Direction with Horizontal.RIGHT and Vertical.UP */
    NORTH_EAST ( Horizontal.RIGHT, Vertical.UP ),               // North/East       : RIGHT/UP
    /** East Direction with Horizontal.RIGHT and Vertical.NONE */
    EAST ( Horizontal.RIGHT, Vertical.NONE ),                   // East             : RIGHT
    /** South East Direction with Horizontal.RIGHT and Vertical.DOWN */
    SOUTH_EAST ( Horizontal.RIGHT, Vertical.DOWN ),             // South/East       : RIGHT/DOWN
    /** South Direction with Horizontal.NONE and Vertical.DOWN */
    SOUTH ( Horizontal.NONE, Vertical.DOWN ),                   // South            : DOWN
    /** South West Direction with Horizontal.LEFT and Vertical.DOWN */
    SOUTH_WEST ( Horizontal.LEFT, Vertical.DOWN ),              // South/West       : LEFT/DOWN
    /** West Direction with Horizontal.LEFT and Vertical.NONE */
    WEST ( Horizontal.LEFT, Vertical.NONE ),                    // West             : LEFT
    /** North West Direction with Horizontal.LEFT and Vertical.UP */
    NORTH_WEST ( Horizontal.LEFT, Vertical.UP )                 // North/West       : LEFT/UP
    ; 


    public enum Horizontal { NONE, RIGHT, LEFT };
    public enum Vertical { NONE, UP, DOWN };

    /** The horizontal direction NONE/RIGHT/LEFT */
    public final Horizontal xDir;
    /** The vertical direction NONE/UP/DOWN */
    public final Vertical yDir;
    
    Direction( Horizontal xDir, Vertical yDir ) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

}
