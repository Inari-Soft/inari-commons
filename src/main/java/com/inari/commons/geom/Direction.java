/**
* Copyright (c) 2009-2013, Andreas Hefti, anhefti@yahoo.de 
* All rights reserved.
*
* This software is licensed to you under the Apache License, Version 2.0
* (the "License"); You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* . Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* . Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* . Neither the name "InariUtils" nor the names of its contributors may be
* used to endorse or promote products derived from this software without
* specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
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
