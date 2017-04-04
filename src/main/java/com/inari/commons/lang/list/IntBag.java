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
package com.inari.commons.lang.list;

import java.util.Arrays;
import java.util.BitSet;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;
import com.inari.commons.lang.IntIterator;

public final class IntBag implements StringConfigurable {
    
    private int nullValue = Integer.MIN_VALUE;
    private int expand = 10;
    private int[] array;
    private int size = 0;
    
    public IntBag() {
        initArray( expand );
    }
    
    public IntBag( int initSize ) {
        initArray( initSize );
    }
    
    public IntBag( int initSize, int nullValue ) {
        this.nullValue = nullValue;
        initArray( initSize );
    }
    
    public IntBag( int initSize, int nullValue, int expand ) {
        this.nullValue = nullValue;
        this.expand = expand;
        initArray( initSize );
    }

    public IntBag( BitSet bits, int nullValue, int expand ) {
        this.nullValue = nullValue;
        this.expand = expand;
        initArray( bits.cardinality() );
        for ( int i = bits.nextSetBit( 0 ); i >= 0; i = bits.nextSetBit( i+1 ) ) {
            add( i );
        }
    }

    public final int getNullValue() {
        return nullValue;
    }

    public final void setNullValue( int nullValue ) {
        if ( indexOf( nullValue ) >= 0 ) {
            throw new IllegalArgumentException( "The IntBag contains already a not-null value that is equals to the new null-value" );
        }

        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] == this.nullValue ) {
                array[ i ] = nullValue;
            }
        }
        this.nullValue = nullValue;
    }

    public final int getExpand() {
        return expand;
    }

    public final void setExpand( int expand ) {
        this.expand = expand;
    }
    
    public final int size() {
        return  size;
    }
    
    public final int length() {
        return array.length;
    }
    
    public final boolean isEmpty() {
        return size <= 0;
    }
    
    public final void clear() {
        for ( int i = 0; i < array.length; i++ ) {
            array[ i ] = nullValue;
        }
        size = 0;
    }
    
    public final void set( int index, int value ) {
        ensureCapacity( index );
        if ( array[ index ] == nullValue ) {
            size++;
        }
        array[ index ] = value;
    }
    
    public final int add( int value ) {
        int firstEmptyIndex = firstEmptyIndex();
        if ( firstEmptyIndex >= 0 ) {
            array[ firstEmptyIndex ] = value;
            size++;
            return firstEmptyIndex;
        }
        
        int oldLength = array.length;
        expand( 0 );
        array[ oldLength ] = value;
        size++;
        return oldLength;
    }
    
    public final void addAll( IntBag toAdd ) {
        if ( toAdd == null ) {
            return;
        }
        
        for ( int i = 0; i < toAdd.length(); i++ ) {
            if ( toAdd.isEmpty( i ) ) {
                continue;
            }
            add( toAdd.get( i ) );
        }
    }
    
    public final void addAll( int... values ) {
        if ( values == null ) {
            return;
        }
        
        for ( int value : values ) {
            add( value );
        }
    }
    
    public final void addAll( IntIterator intIterator ) {
        if ( intIterator == null ) {
            return;
        }
        
        while( intIterator.hasNext() ) {
            add( intIterator.next() );
        }
    }
    
    public final boolean isEmpty( int index ) {
        return array[ index ] == nullValue;
    }
    
    public final boolean contains( int value ) {
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] == value ) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean remove( int value ) {
        int indexOf = indexOf( value );
        if ( indexOf >= 0 ) {
            array[ indexOf ] = nullValue; 
            size--;
            return true;
        }
        
        return false;
    }
    
    public final int removeAt( int index ) {
        int result = array[ index ];
        array[ index ] = nullValue;
        return result;
    }
    
    public final void swap( int index1, int index2 ) {
        int tmp = array[ index1 ];
        array[ index1 ] = array[ index2 ];
        array[ index2 ] = tmp;
    }
    
    public final int get( int index ) {
        return array[ index ];
    }
    
    public final IntIterator iterator() {
        return new IntBagIterator();
    }
    
    private final int firstEmptyIndex() {
        return indexOf( nullValue );
    }
    
    public final int indexOf( int value ) {
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] == value ) {
                return i;
            }
        }
        
        return -1;
    }
    
    public final void trim() {
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[ i ] == nullValue ) {
                for ( int j = array.length - 1; j > i; j-- ) {
                    if ( array[ j ] != nullValue ) {
                        array[ i ] = array[ j ];
                        array[ j ] = nullValue;
                        break;
                    }
                }
            }
        }
        
        if ( array.length != size ) {
            int[] temp = array;
            initArray( size );
            System.arraycopy( temp, 0, array, 0, size );
        }
    }

    @Override
    public final void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            return;
        }
        
        clear();
        String[] splited = stringValue.split( "," );
        for ( int i = 0; i < splited.length; i++ ) {
            add( Integer.parseInt( splited[ i ] ) );
        }
    }

    @Override
    public final String toConfigString() {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < array.length; i++ ) {
            sb.append( array[ i ] );
            if ( i < array.length - 1 ) {
                sb.append( "," );
            }
        }
        return sb.toString();
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "IntBag [nullValue=" ).append( nullValue )
                .append( ", expand=" ).append( expand ).append( ", size=" ).append( size )
                .append( ", length=" ).append( length() )
                .append( ", array=" ).append( Arrays.toString( array ) ).append( "]" );
        return builder.toString();
    }

    private void ensureCapacity( int size ) {
        if ( array.length <= size ) {
            expand( size - array.length + 1 );
        }
    }

    private void initArray( int size ) {
        array = new int[ size ];
        for ( int i = 0; i < array.length; i++ ) {
            array[ i ] = nullValue;
        }
    }
    
    private void expand( int expandSize ) {
        int[] temp = array;
        initArray( temp.length + expandSize + expand );
        System.arraycopy( temp, 0, array, 0, temp.length );
    }
    
    private final class IntBagIterator implements IntIterator {
        
        private int currentIndex = 0;
        
        IntBagIterator() {
            findNext();
        }

        @Override
        public boolean hasNext() {
            return ( currentIndex < array.length );
        }

        @Override
        public int next() {
            int result = array[ currentIndex ];
            currentIndex++;
            findNext();
            return result;
        }
        
        public void findNext() {
            while( currentIndex < array.length && array[ currentIndex ] == nullValue ) {
                currentIndex++;
            }
        };
    }

}
