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
package com.inari.commons;

import static com.inari.commons.geom.Direction.EAST;
import static com.inari.commons.geom.Direction.NONE;
import static com.inari.commons.geom.Direction.NORTH;
import static com.inari.commons.geom.Direction.NORTH_EAST;
import static com.inari.commons.geom.Direction.NORTH_WEST;
import static com.inari.commons.geom.Direction.SOUTH;
import static com.inari.commons.geom.Direction.SOUTH_EAST;
import static com.inari.commons.geom.Direction.SOUTH_WEST;
import static com.inari.commons.geom.Direction.WEST;

import com.inari.commons.geom.Direction;
import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.geom.Vector2i;

// TODO write tests
public abstract class GeomUtils {
    
    public static final String NULL_POINT_STRING = "0,0";
    public static final String NULL_RECTANGLE_STRING = "0,0,0,0";
    
    public static final int TOP_SIDE = 1;
    public static final int RIGHT_SIDE = 1 << 2;
    public static final int BOTTOM_SIDE = 1 << 3;
    public static final int LEFT_SIDE = 1 << 4;
    
    
    
    public static final float getDistance( Position p1, Position p2 ) {
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        
        return (float) Math.sqrt( dx * dx + dy * dy );
    }
    
    public final static boolean intersect( Rectangle r1, Rectangle r2 ) {
        int r1Right = r1.x + r1.width;
        int r1Bottom = r1.y + r1.height;
        int r2Right = r2.x + r2.width;
        int r2Bottom = r2.y + r2.height;
        
        return !( r2.x > r1Right || 
                  r2Right < r1.x || 
                  r2.y > r1Bottom ||
                  r2Bottom < r1.y );
    }
    
    public final static int getIntersectionCode( Rectangle r, Rectangle r1 ) {
        int ax1 = r.x;
        int ay1 = r.y;
        int ax2 = r.x + r.width - 1;
        int ay2 = r.y + r.height - 1;
        
        int bx1 = r1.x;
        int by1 = r1.y;
        int bx2 = r1.x + r1.width - 1;
        int by2 = r1.y + r1.height - 1;
        
        int code = 0;
        
        if ( ( bx2 >= ax1 ) && ( bx1 <= ax2 ) ) {
            if ( ( by1 > ay1 ) && ( by1 <= ay2 ) ) {
                code |= TOP_SIDE;
            }
            if ( ( by2 >=  ay1 ) && ( by2 < ay2 ) ) {
                code |= BOTTOM_SIDE;
            }
        }
        
        if ( ( by2 >= ay1 ) && ( by1 <= ay2 ) ) {
            if ( ( bx1 > ax1 ) && ( bx1 <= ax2 ) ) {
                code |= LEFT_SIDE;
            }
            if ( ( bx2 >= ax1 ) && ( bx2 < ax2 ) ) {
                code |= RIGHT_SIDE;
            }
        }
        return code;
    }
    
    public final static Rectangle intersection( Rectangle r, Rectangle r1 ) {
        int x1 = Math.max( r.x, r1.x );
        int y1 = Math.max( r.y, r1.y );
        int x2 = Math.min( r.x + r.width - 1, r1.x + r1.width - 1 );
        int y2 = Math.min( r.y + r.height - 1, r1.y + r1.height - 1 );
        return new Rectangle( x1, y1, Math.max( 0, x2 - x1 + 1 ), Math.max( 0, y2 - y1 + 1 ) );
    }
    
    public final static Rectangle union( Rectangle r, Rectangle r1 ) {
        int x1 = Math.min( r.x, r1.x );
        int y1 = Math.min( r.y, r1.y );
        int x2 = Math.max( r.x + r.width - 1, r1.x + r1.width - 1 );
        int y2 = Math.max( r.y + r.height - 1, r1.y + r1.height - 1) ;
        return new Rectangle( x1, y1, x2 - x1 + 1, y2 - y1 + 1 );
    }
    
    public final static int getBoundary( Rectangle r, int side ) {
        switch ( side ) {
            case LEFT_SIDE: return r.x; 
            case TOP_SIDE: return r.y;
            case RIGHT_SIDE: return r.x + r.width - 1;
            case BOTTOM_SIDE: return r.y + r.height - 1;
            default: return r.x;
        }
    }
    
    public final static boolean contains( Rectangle r, int x, int y ) {
        return (
            ( x >= r.x ) &&
            ( y >= r.y ) && 
            ( x < ( r.x + r.width ) ) && 
            ( y < ( r.y + r.height ) )
        );
    }
    
    public final static boolean contains( Rectangle r, Position p ) {
        return (
            ( p.x >= r.x ) && 
            ( p.y >= r.y ) && 
            ( p.x < ( r.x + r.width ) ) && 
            ( p.y < ( r.y + r.height ) )
        );
    }
    
    public final static boolean contains( Rectangle r, Rectangle r1 ) {
        return (
            ( r1.x >= r.x ) && 
            ( r1.y >= r.y ) && 
            ( ( r1.x + r1.width ) <= ( r.x + r.width ) ) && 
            ( ( r1.y + r1.height ) <= ( r.y + r.height ) )
        );
    }
    
    public final static int getOppositeSide( int side ) {
        return ( ( side << 2 ) & 0xC ) | ( ( side >> 2 ) & 0x3 );
    }
    
    public final static Rectangle setOutsideBoundary( Rectangle r, int side, int boundary ) {
        switch ( side ) {
            case LEFT_SIDE: {
                r.width += r.x - boundary - 1;
                r.x = boundary + 1;
                break;
            }
            case TOP_SIDE: {
                r.height += r.y - boundary - 1;
                r.y = boundary + 1;
                break;
            }
            case RIGHT_SIDE: {
                r.width = boundary - r.x;
                break;
            }
            case BOTTOM_SIDE: {
                r.height = boundary - r.y;
                break;
            }
            default:
                break;
        }
        return r;
    }
    
    public final static Rectangle setBoundary( Rectangle r, int side, int boundary ) {
        switch ( side ) {
            case LEFT_SIDE: {
                r.width += r.x - boundary;
                r.x = boundary;
                break;
            }
            case TOP_SIDE: {
                r.height += r.y - boundary;
                r.y = boundary;
                break;
            }
            case RIGHT_SIDE: {
                r.width = boundary - r.x + 1;
                break;
            }
            case BOTTOM_SIDE: {
                r.height = boundary - r.y + 1;
                break;
            }
            default:
                break;
        }
        return r;
    }
    
    public final static Direction rotateLeft( Direction d ) {
        switch ( d ) {
        case NORTH: return NORTH_EAST;
        case NORTH_EAST: return EAST;
        case EAST: return SOUTH_EAST;
        case SOUTH_EAST: return SOUTH;
        case SOUTH: return SOUTH_WEST;
        case SOUTH_WEST: return WEST;
        case WEST: return NORTH_WEST;
        case NORTH_WEST: return NORTH;
        default: return NONE;
        }
    }
    
    public final static Direction rotateLeft2( Direction d ) {
        switch ( d ) {
            case NORTH: return EAST;
            case NORTH_EAST: return SOUTH_EAST;
            case EAST: return SOUTH;
            case SOUTH_EAST: return SOUTH_WEST;
            case SOUTH: return WEST;
            case SOUTH_WEST: return NORTH_WEST;
            case WEST: return NORTH;
            case NORTH_WEST: return NORTH_EAST;
            default: return NONE;
        }
    }
    
    public final static Direction rotateRight( Direction d ) {
            switch ( d ) {
            case NORTH: return NORTH_WEST;
            case NORTH_WEST: return WEST;
            case WEST: return SOUTH_WEST;
            case SOUTH_WEST: return SOUTH;
            case SOUTH: return SOUTH_EAST;
            case SOUTH_EAST: return EAST;
            case EAST: return NORTH_EAST;
            case NORTH_EAST: return NORTH;
            default: return NONE;
        }
    }
    
    public final static Direction rotateRight2( Direction d ) {
        switch ( d ) {
            case NORTH: return WEST;
            case NORTH_WEST: return SOUTH_WEST;
            case WEST: return SOUTH;
            case SOUTH_WEST: return SOUTH_EAST;
            case SOUTH: return EAST;
            case SOUTH_EAST: return NORTH_EAST;
            case EAST: return NORTH;
            case NORTH_EAST: return NORTH_WEST;
            default: return NONE;
        }
    }
    
    public final static boolean isHorizontal( Direction d ) {
        return ( ( d == EAST ) || ( d == WEST ) );
    }
    
    public final static boolean isVertical( Direction d ) {
        return ( ( d == NORTH ) || ( d == SOUTH ) );
    }
    
    public final static void translateTo( Position p, Position to ) {
        p.x = to.x;
        p.y = to.y;
    }
    
    public final static void translate( Position p, Vector2i d ) {
        p.x += d.dx;
        p.y += d.dy;
    }
    
    public static final int getTranslatedXPos( Position p, Direction d ) {
        return getTranslatedXPos( p, d, 1 );
    }
    
    public static final int getTranslatedXPos( Position p, Direction d, int dx ) {
        switch ( d.xDir ) {
            case LEFT: return p.x - dx;
            case RIGHT: return p.x + dx;
            default : return p.x;
        }
    }
    
    public final static int getTranslatedYPos( Position p, Direction d ) {
        return getTranslatedYPos( p, d, 1 );
    }
    
    public final static int getTranslatedYPos( Position p, Direction d, int dy ) {
        switch ( d.yDir ) {
            case DOWN: return p.y + dy;
            case UP: return p.y - dy;
            default : return p.y;
        }
    }


}
