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
package com.inari.commons.lang.list;


public final class LLNode<T> implements INode<T> {
    
    boolean markerNode = false;
    
    public T value;
    
    LLNode<T> nextNode;
    LLNode<T> prevNode;
    
    public LLNode() {
        this.value = null;
        this.nextNode = null;
        this.prevNode = null;
    }
    
    public LLNode( T value ) {
        this.value = value;
        this.nextNode = null;
        this.prevNode = null;
    }
    
    @Override
    public final T value() {
        return value;
    }

    @Override
    public final INode<T> nextNode() {
        return nextNode;
    }

    @Override
    public final INode<T> previousNode() {
        return prevNode;
    }
    
    @Override
    public final void remove() {
        if ( this.prevNode != null ) {
            this.prevNode.nextNode = this.nextNode;
        }
        if ( this.nextNode != null ) {
            this.nextNode.prevNode = this.prevNode;
        }
        this.nextNode = null;
        this.prevNode = null;
    }

}
