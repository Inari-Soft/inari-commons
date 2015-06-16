/**
* Copyright (c) 2015, Andreas Hefti, anhefti@yahoo.de 
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
package com.inari.commons.lang;


/** A key with an String id and a type. This Key can be used to define the type of a value in a mapping within the key itself
 *  and can provide a cast to a value type by key type.
 *  
 *  Map<TypedKey<?>, Object> mapping = new HashMap<TypedKey<?>, Object>();
 *  
 *  public <T> getValue( TypedKey<T> key ) {
 *      Object value = mapping.get( key );
 *      if ( value == null ) {
 *          return null;
 *      }
 *      
 *      return key.type().cast( value );
 *  }
 *  
 * @author anhefti@yahoo.de
 *
 * @param <T> The value type of the key
 */
public class TypedKey<T> {
    
    private final String id;
    private final Class<T> type;
    private final int hash;
    
    protected TypedKey( String id, Class<T> type ) {
        this.id = id;
        this.type = type;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        result = prime * result + type.hashCode();
        hash = result;
    }

    public String id() {
        return id;
    }

    public Class<T> type() {
        return type;
    }
    
    public final T cast( Object obj ) {
        return type.cast( obj );
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        TypedKey<?> other = (TypedKey<?>) obj;
        if ( id == null ) {
            if ( other.id != null )
                return false;
        } else if ( !id.equals( other.id ) )
            return false;
        if ( type == null ) {
            if ( other.type != null )
                return false;
        } else if ( !type.equals( other.type ) )
            return false;
        return true;
    }

    public static final <T> TypedKey<T> create( String id, Class<T> type ) {
        if ( id == null ) {
            throw new IllegalArgumentException( "id is null" );
        }
        if ( type == null ) {
            throw new IllegalArgumentException( "type is null" );
        }
        return new TypedKey<T>( id, type );
    }

    @Override
    public String toString() {
        return id + ":" + type.getSimpleName();
    }

}
