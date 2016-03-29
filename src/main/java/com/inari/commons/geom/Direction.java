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

/** Eight directions, build of four Orientations, and a NONE constant
 *  containing also a Horizontal and Vertical enum value that divides the
 *  Direction into a horizontal and vertical part.
 *
 *  Use this if you have a discrete direction with eight different directions,
 *  for example, for input, move, or other things.
 */
public enum Direction {

    /** No Direction with also Horizontal.NONE and Vertical.NONE */
    NONE ( Orientation.NONE, Orientation.NONE ),
    /** North Direction with Horizontal.NONE and Vertical.UP */
    NORTH ( Orientation.NONE, Orientation.NORTH ),
    /** North East Direction with Horizontal.RIGHT and Vertical.UP */
    NORTH_EAST ( Orientation.EAST, Orientation.NORTH ),
    /** East Direction with Horizontal.RIGHT and Vertical.NONE */
    EAST ( Orientation.EAST, Orientation.NONE ),
    /** South East Direction with Horizontal.RIGHT and Vertical.DOWN */
    SOUTH_EAST ( Orientation.EAST, Orientation.SOUTH ),
    /** South Direction with Horizontal.NONE and Vertical.DOWN */
    SOUTH ( Orientation.NONE, Orientation.SOUTH ),
    /** South West Direction with Horizontal.LEFT and Vertical.DOWN */
    SOUTH_WEST ( Orientation.WEST, Orientation.SOUTH ),
    /** West Direction with Horizontal.LEFT and Vertical.NONE */
    WEST ( Orientation.WEST, Orientation.NONE ),
    /** North West Direction with Horizontal.LEFT and Vertical.UP */
    NORTH_WEST ( Orientation.WEST, Orientation.NORTH )
    ; 
    
    /** The horizontal orientation NONE/EAST/WEST */
    public final Orientation horizontal;
    /** The vertical orientation NONE/NORTH/SOUTH */
    public final Orientation vertical;
    
    /** Use this to create a new Direction with specified horizontal and vertical Orientation.
     * @param horizontal horizontal Orientation of the new Direction
     * @param vertical vertical Orientation of the new Direction
     */
    Direction( Orientation horizontal, Orientation vertical ) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

}
