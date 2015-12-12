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

public class LinkedLoop<T> {

    private LLNode<T> pointer = null;
    
    
    public void addFirst( T value ) {
        if ( pointer == null ) {
            addFirstValue( value );
            return;
        }
        LLNode<T> next = pointer.nextNode;
        LLNode<T> newNode = new LLNode<T>( value );
        pointer.nextNode = newNode;
        newNode.prevNode = pointer;
        newNode.nextNode = next;
        next.prevNode = newNode;
    }

    public void addLast( T value ) {
        if ( pointer == null ) {
            addFirstValue( value );
            return;
        }
        LLNode<T> prev = pointer.prevNode;
        LLNode<T> newNode = new LLNode<T>( value );
        pointer.prevNode = newNode;
        newNode.nextNode = pointer;
        newNode.prevNode = prev;
        prev.nextNode = newNode;
    }
    
    private void addFirstValue( T value ) {
        pointer = new LLNode<T>( value );
        pointer.nextNode = pointer;
        pointer.prevNode = pointer;
    }

}
