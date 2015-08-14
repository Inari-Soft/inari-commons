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
package com.inari.commons.lang.indexed;

import com.inari.commons.lang.functional.Disposable;

public abstract class BaseIndexedObject implements IndexedObject, Disposable {
    
    protected int indexedId;
    
    protected BaseIndexedObject() {
        setIndex( -1 );
    }
    
    protected BaseIndexedObject( int indexedId ) {
        setIndex( indexedId );
    }
    
    protected BaseIndexedObject( boolean skipAutoInit ) {
        if ( skipAutoInit ) {
            indexedId = -1;
        } else {
            setIndex( -1 );
        }
    }
    
    protected final void setIndex( int indexedId ) {
        if ( this.indexedId >= 0 ) {
            Indexer.disposeIndexedObject( this );
        }
        
        if ( indexedId < 0 ) {
            this.indexedId = Indexer.nextObjectIndex( indexedObjectType() );
        } else {
            this.indexedId = indexedId;
            Indexer.setIndexedObjectIndex( this );
        }
    }

    @Override
    public final int index() {
        return indexedId;
    }

    @Override
    public void dispose() {
        if ( indexedId < 0 ) {
            return;
        }
        Indexer.disposeIndexedObject( this );
        indexedId = -1;
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
        result = prime * result + indexedId;
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
        if ( indexedId != other.indexedId )
            return false;
        if ( indexedObjectType() == null ) {
            if ( other.indexedObjectType() != null )
                return false;
        } else if ( !indexedObjectType().equals( other.indexedObjectType() ) )
            return false;
        return true;
    }
}
