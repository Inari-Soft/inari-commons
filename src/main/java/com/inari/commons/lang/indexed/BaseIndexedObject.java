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
package com.inari.commons.lang.indexed;

import com.inari.commons.lang.Disposable;

public abstract class BaseIndexedObject implements IndexedObject, Disposable {
    
    protected int index = -1;
    
    protected BaseIndexedObject() {
        this( -1 );
    }
    
    protected BaseIndexedObject( int indexedId ) {
        setIndex( indexedId );
        if ( indexedId >= 0 ) {
            Indexer.setIndexedObjectIndex( this );
        }
    }
    
    protected BaseIndexedObject( boolean skipAutoInit ) {
        if ( skipAutoInit ) {
            index = -1;
        } else {
            setIndex( -1 );
        }
    }
    
    protected final void setIndex( int indexedId ) {
        if ( this.index >= 0 ) {
            Indexer.disposeIndexedObject( this );
        }
        
        if ( indexedId < 0 ) {
            this.index = Indexer.nextObjectIndex( indexedObjectType() );
        } else {
            this.index = indexedId;
        }
    }

    @Override
    public final int index() {
        return index;
    }

    @Override
    public void dispose() {
        if ( index < 0 ) {
            return;
        }
        Indexer.disposeIndexedObject( this );
        index = -1;
    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + indexedObjectType().hashCode();
        return result;
    }

    @Override
    public final boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        BaseIndexedObject other = (BaseIndexedObject) obj;
        if ( index != other.index )
            return false;
        if ( indexedObjectType() == null ) {
            if ( other.indexedObjectType() != null )
                return false;
        } else if ( indexedObjectType() != other.indexedObjectType() )
            return false;
        return true;
    }
}
