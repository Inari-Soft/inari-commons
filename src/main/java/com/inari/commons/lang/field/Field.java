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
*/
package com.inari.commons.lang.field;

import com.inari.commons.geom.Direction;
import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;

public class Field<V> {
    
    protected boolean spherical = false;
    protected int width = -1;
    protected int height = -1;
    protected Node<V>[][] fieldArray;
    
    protected Field( Node<V>[][] fieldArray, boolean spherical ) {
        this.fieldArray = fieldArray;
        if ( fieldArray != null ) {
            width = fieldArray.length;
            if ( fieldArray.length > 0 ) {
                height = fieldArray[ 0 ].length;
            } else {
                height = 0;
            }
        }
        this.spherical = spherical;
    }
    
    public final int width() {
        return width;
    }
    
    public final int height() {
        return height;
    }
    
    public final boolean isEmpty() {
        return ( width < 1 ) || ( height < 1 );
    }
    
    public final boolean isSpherical() {
        return spherical;
    }
    
    public final Node<V> getNode( int x, int y ) {
        return fieldArray[ x ][ y ];
    }
    
    public final Node<V> getNode( Position pos ) {
        return fieldArray[ pos.x ][ pos.y ];
    }
    
    public final V getValue( int x, int y ) {
        return fieldArray[ x ][ y ].value;
    }
    
    public final V getValue( Position pos ) {
        return fieldArray[ pos.x ][ pos.y ].value;
    }
    
    public final void setValue( Position pos, V value ) {
        fieldArray[ pos.x ][ pos.y ].value = value;
    }
    
    public final void swapValue( Position pos1, Position pos2 ) {
        V temp = fieldArray[ pos1.x ][ pos1.y ].value;
        fieldArray[ pos1.x ][ pos1.y ].value = fieldArray[ pos2.x ][ pos2.y ].value;
        fieldArray[ pos2.x ][ pos2.y ].value = temp;
    }
    
    public FieldIterator<Node<V>> nodeIterator() {
        return new FieldNodeIterator( new Rectangle( 0, 0, width, height ) );
    }
    
    public FieldIterator<Node<V>> nodeIterator( Rectangle clip ) {
        return new FieldNodeIterator( clip );
    }
    
    public FieldIterator<V> valueIterator() {
        return new FieldValueIterator( new Rectangle( 0, 0, width, height ) );
    }
    
    public FieldIterator<V> valueIterator( Rectangle clip ) {
        return new FieldValueIterator( clip );
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "Field( width:" ).append( width ).append( " height:" ).append( height );
        builder.append( " spherical:" ).append( spherical ).append( " )\n" );
        for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                builder.append( "  [ " ).append( fieldArray[ x ][ y ].value ).append( " ]" );
            }
            builder.append( "\n" );
        }
        return builder.toString();
    }
    
    
    
    public final static class Node<V> {
        
        protected Node<V> north;
        protected Node<V> east;
        protected Node<V> south;
        protected Node<V> west;
        
        protected V value;
        
        public Node() {}
        
        public Node( V value ) {
            this.value = value;
        }
        
        public final V value() {
            return value;
        }

        public final void value( V value ) {
            this.value = value;
        }

        public final Node<V> north() {
            return north;
        }

        public final void north( Node<V> north ) {
            this.north = north;
        }

        public final Node<V> east() {
            return east;
        }

        public final void east( Node<V> east ) {
            this.east = east;
        }

        public final Node<V> south() {
            return south;
        }

        public final void south( Node<V> south ) {
            this.south = south;
        }

        public final Node<V> west() {
            return west;
        }

        public final void west( Node<V> west ) {
            this.west = west;
        }
        
        public final Node<V> get( Direction direction ) {
            switch ( direction ) {
                case NORTH: return north;
                case NORTH_EAST: return ( north == null )? null : north.east;
                case EAST: return east;
                case SOUTH_EAST: return ( south == null )? null : south.east;
                case SOUTH: return south;
                case SOUTH_WEST: return ( south == null )? null : south.west;
                case WEST: return west;
                case NORTH_WEST: return ( north == null )? null : north.west;
                default: return null;
            }
        }
    }
    
    private abstract class AbstractFieldIterator<T> implements FieldIterator<T> {
        
        protected Rectangle clip = new Rectangle( 0, 0, 0, 0 );
        protected Position pos;
        protected int w;
        protected int h;
        
        public AbstractFieldIterator( Rectangle clipOrig ) {
            clip.x = clipOrig.x;
            clip.y = clipOrig.y;
            clip.width = clipOrig.width;
            clip.height = clipOrig.height;
            
            if ( clip.x < 0 ) {
                clip.x = 0;
            }
            if ( clip.y < 0 ) {
                clip.y = 0;
            }
            if ( clip.x + clip.width > width ) {
                clip.width = width - clip.x;
            }
            if ( clip.y + clip.height > height ) {
                clip.height = height - clip.y;
            }
            
            pos = new Position( clip.x - 1, clip.y );
            w = clip.x + clip.width - 1;
            h = clip.y + clip.height - 1;
        }
        
        
        @Override
        public final boolean hasNext() {
            if ( pos.y < h ) {
                return true;
            }
            return pos.x < w;
        }

        @Override
        public final Position getPosition() {
            return pos;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException( "Remove is not supported!" );
        }
        
    }
    
    private final class FieldNodeIterator extends AbstractFieldIterator<Node<V>> {

        public FieldNodeIterator( Rectangle clip ) {
            super( clip );
        }

        @Override
        public final Node<V> next() {
            if ( pos.x < w ) {
                pos.x++;
            } else {
                pos.x = clip.x;
                pos.y++;
            }
            
            return fieldArray[ pos.x ][ pos.y ];
        }
    }
    
    private final class FieldValueIterator extends AbstractFieldIterator<V> {
        
        public FieldValueIterator( Rectangle clip ) {
            super( clip );
        }

        @Override
        public final V next() {
            if ( pos.x < w ) {
                pos.x++;
            } else {
                pos.x = clip.x;
                pos.y++;
            }
            
            return fieldArray[ pos.x ][ pos.y ].value;
        }
    }

}
