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
package com.inari.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;


/** Defines utility methods for String manipulation
 *
 *  @author <a href="mailto:anhefti@yahoo.de">Andreas Hefti</a>
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

}
