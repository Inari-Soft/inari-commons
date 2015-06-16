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
