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
package com.inari.commons.lang.field;

import com.inari.commons.lang.field.Field.Node;

public abstract class BaseFieldFactory<V> {
    
    public Field<V> createField( int width, int height, boolean spherical ) {
        
        // creation
        Node<V>[][] fieldArray = createFieldArray( width, height );
        for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                fieldArray[ x ][ y ] = createNode( x, y );
            }
        }
        
        // linking
        int xBound = width - 1;
        int yBound = height - 1;
        for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                // west link
                if ( ( x == 0 ) && spherical ) {
                    fieldArray[ x ][ y ].west = fieldArray[ xBound ][ y ];
                } else if ( x > 0 ) {
                    fieldArray[ x ][ y ].west = fieldArray[ x - 1 ][ y ];
                }
                // north link
                if ( ( y == 0 ) && spherical ) {
                    fieldArray[ x ][ y ].north = fieldArray[ x ][ yBound ];
                } else if ( y > 0 ) {
                    fieldArray[ x ][ y ].north = fieldArray[ x ][ y - 1 ];
                }
                // east link
                if ( ( x == xBound ) && spherical ) {
                    fieldArray[ x ][ y ].east = fieldArray[ 0 ][ y ];
                } else if ( x < xBound ) { 
                    fieldArray[ x ][ y ].east = fieldArray[ x + 1 ][ y ];
                }
                // south link
                if ( ( y == yBound ) && spherical ) {
                    fieldArray[ x ][ y ].south = fieldArray[ x ][ 0 ];
                } else if ( y < yBound ) {
                    fieldArray[ x ][ y ].south = fieldArray[ x ][ y + 1 ];
                }
            }
        }
        
        return new Field<V>( fieldArray, spherical );
    }
    
    @SuppressWarnings("unchecked")
    public Node<V>[][] createFieldArray( int width, int height ) {
        return (Node<V>[][]) new Node<?>[ width ][ height ];
    }
    
    public abstract Node<V> createNode( int x, int y );

}
