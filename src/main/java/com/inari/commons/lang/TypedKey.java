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
public final class TypedKey<T> {
    
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

    public final String id() {
        return id;
    }

    public final Class<T> type() {
        return type;
    }
    
    public final T cast( Object obj ) {
        return type.cast( obj );
    }

    @Override
    public final int hashCode() {
        return hash;
    }

    @Override
    public final boolean equals( Object obj ) {
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
    public final String toString() {
        return id + ":" + type.getSimpleName();
    }

}
