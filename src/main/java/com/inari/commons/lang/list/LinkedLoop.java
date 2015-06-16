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
