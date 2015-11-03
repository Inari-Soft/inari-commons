/*******************************************************************************
 * Copyright (c) 2015 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;


/** Defines utility methods for String manipulation
 *
 *  @author andreas hefti
 */
public abstract class StringUtils {
    
    public static final char VALUE_SEPARATOR = ',';
    public static final String VALUE_SEPARATOR_STRING = ",";
    
    public static final char KEY_VALUE_SEPARATOR = '=';
    public static final String KEY_VALUE_SEPARATOR_STRING = "=";
    
    public static final char LIST_VALUE_SEPARATOR = '|';
    public static final String LIST_VALUE_SEPARATOR_STRING = "|";
    

    /** Indicates if the String s is null or an empty String 
     *  @param s the String
     *  @return true if the String s is null or an empty String 
     */
    public static final boolean isEmpty( String s ) {
        return ( ( s == null ) || ( s.length() < 1 ) );
    }

    /** Indicates if the String s is null, an empty String or a blank String
     *  @param s the String
     *  @return true if the String s is null, an empty String or a blank String
     */
    public static final boolean isBlank( String s ) {
        return ( ( s == null ) || ( s.trim().length() < 1 ) );
    }
    
    
    public final static Collection<String> split( String string, String separator ) {
        if ( StringUtils.isBlank( string ) ) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer( string, separator );
        while ( st.hasMoreTokens() ) {
            list.add( st.nextToken() );
        }
        return list;
    }
    
    public final static String[] splitToArray( String string, String separator ) {
        if ( StringUtils.isBlank( string ) ) {
            return null;
        }
        StringTokenizer st = new StringTokenizer( string, separator );
        String[] tokens = new String[ st.countTokens() ];
        int i = 0;
        while ( st.hasMoreTokens() ) {
            tokens[ i ] = st.nextToken();
            i++;
        }
        return tokens;
    }
    
    public final static Map<String, String> splitToMap( String string, String separator, String keyValueSeparator ) {
        if ( StringUtils.isBlank( string ) ) {
            return null;
        }
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        StringTokenizer stEntry = new StringTokenizer( string, separator );
        while ( stEntry.hasMoreTokens() ) {
            String entry = stEntry.nextToken();
            int index = entry.indexOf( keyValueSeparator );
            if ( index > 0 ) {
                map.put( 
                    entry.substring( 0, index ), 
                    entry.substring( index + 1, entry.length() ) 
                );
            }
        }
        return map;
    }
    
    public static final String fillPrepending( String string, char fill, int length ) {
        if ( string.length() >= length ) {
            return string;
        }
        StringBuilder sb = new StringBuilder( string );
        while ( sb.length() < length ) {
            sb.insert( 0, fill );
        } 
        return sb.toString();
    }
    
    public static final String fillAppending( String string, char fill, int length ) {
        if ( string.length() >= length ) {
            return string;
        }
        StringBuilder sb = new StringBuilder( string );
        while ( sb.length() < length ) {
            sb.append( fill );
        } 
        return sb.toString();
    }
    
    public static final String escapeSeparatorKeys( String value ) {
        // TODO
        return value;
    }

    public static String array2DToString( int[][] grid ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "[" );
        for ( int i = 0; i < grid.length; i++ ) {
            sb.append( Arrays.toString( grid[ i ] ) );
        }
        sb.append( "]" );
        return sb.toString();
    }

    public static String join( Collection<?> collection, String separatorString ) {
        if ( collection == null ) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        
        Iterator<?> iterator = collection.iterator();
        while( iterator.hasNext() ) {
            Object next = iterator.next();
            String strValue = ( next != null )? next.toString(): "";
            // TODO escape separator character
            
            result.append( strValue );
            if ( iterator.hasNext() ) {
                result.append( separatorString );
            }
        }
        
        return result.toString();
    }

}
