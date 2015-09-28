package com.inari.commons.lang.list;

import java.util.Arrays;
import java.util.Collection;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

public final class IntMap implements StringConfigurable {
    
    private final int nullValue;
    private int[] map;
    
    public IntMap( int nullValue ) {
        this.nullValue = nullValue;
        map = new int[ 10 ];
        clear();
    }

    public IntMap( int nullValue, int initialSize ) {
        this.nullValue = nullValue;
        map = new int[ initialSize ];
        clear();
    }
    
    public final void set( int index, int value ) {
        ensureCapacity( index );
        map[ index ] = value;
    }

    public final int get( int index ) {
        if ( index < 0 || index >= map.length ) {
            return nullValue;
        }
        
        return map[ index ];
    }
    
    public final int getFast( int index ) {
        return map[ index ];
    }

    public final int getNullValue() {
        return nullValue;
    }

    private final void clear() {
        for ( int i = 0; i < map.length; i++ ) {
            map[ i ] = nullValue;
        }
    }
    
    @Override
    public final void fromConfigString( String stringValue ) {
        Collection<String> values = StringUtils.split( stringValue, StringUtils.LIST_VALUE_SEPARATOR_STRING );
        map = new int[ values.size() ];
        int index = 0;
        for ( String value : values ) {
            map[ index ] = Integer.valueOf( value );
            index++;
        }
    }

    @Override
    public final String toConfigString() {
        return StringUtils.join( Arrays.asList( map ), StringUtils.LIST_VALUE_SEPARATOR_STRING );
    }
    
    private void ensureCapacity( int index ) {
        if ( index < map.length ) {
            return;
        }
        
        int[] old = map;
        map = new int[ index + 1 ];
        System.arraycopy( old, 0, map, 0, old.length );
    }

}
