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
package com.inari.commons.lang.list;

import java.util.Iterator;

public class LinkedList <T extends Object> implements Iterable<T> {

    private LLNode<T> firstMark;
    private LLNode<T> lastMark;
    
    public LinkedList() {
        this.firstMark = new LLNode<T>();
        this.firstMark.markerNode = true;
        this.lastMark = new LLNode<T>();
        this.lastMark.markerNode = true;
        this.firstMark.nextNode = this.lastMark;
        this.lastMark.prevNode = this.firstMark;
    }
    
    public void addFirst( T value ) {
        this.addFirst( new LLNode<T>( value ) );
    }
    
    public void addFirst( LLNode<T> node ) {
        LLNode<T> tmp = this.firstMark.nextNode;
        this.firstMark.nextNode = node;
        node.prevNode = this.firstMark;
        node.nextNode = tmp;
        tmp.prevNode = node;
    }
    
    public void addFirst( LinkedList<T> list ) {
        this.addFirst( list, true );
    }
    
    public void addFirst( LinkedList<T> list, boolean check ) {
        if ( check ) {
            this.listAddValid( list );
        }
        LLNode<T> tmp = this.firstMark.nextNode;
        list.firstMark.prevNode = this.firstMark;
        this.firstMark.nextNode = list.firstMark;
        list.lastMark.nextNode = tmp;
        tmp.prevNode = list.lastMark;
    }
    
    public void addLast( T value ) {
        this.addLast( new LLNode<T>( value ) );
    }
    
    public void addLast( LLNode<T> node ) {
        LLNode<T> tmp = this.lastMark.prevNode;
        node.nextNode = this.lastMark;
        this.lastMark.prevNode = node;
        tmp.nextNode = node;
        node.prevNode = tmp;
    }
    
    public void addLast( LinkedList<T> list ) {
        this.addLast( list, true );
    }
    
    public void addLast( LinkedList<T> list, boolean check ) {
        if ( check ) {
            this.listAddValid( list );
        }
        LLNode<T> tmp = this.lastMark.prevNode;
        list.lastMark.nextNode = this.lastMark;
        this.lastMark.prevNode = list.lastMark;
        tmp.nextNode = list.firstMark;
        list.firstMark.prevNode = tmp;
    }
    
    public void insert( int index, T value ) {
        this.insert( index, new LLNode<T>( value ) );
    }
    
    public void insert( int index, LLNode<T> node ) {
        LLNode<T> next = ( index < 0 )? this.firstMark.nextNode: this.getNoteAt( index );
        if ( next == null ) {
            next = this.lastMark;
        }
        next.prevNode.nextNode = node;
        node.prevNode = next.prevNode;
        node.nextNode = next;
        next.prevNode = node;
    }
    
    public void insert( int index, LinkedList<T> list ) {
        this.insert( index, list, true );
    }
    
    public void insert( int index, LinkedList<T> list, boolean check ) {
        if ( check ) {
            this.listAddValid( list );
        }
        LLNode<T> next = ( index < 0 )? this.firstMark.nextNode: this.getNoteAt( index );
        if ( next == null ) {
            next = this.lastMark;
        }
        LLNode<T> prev = next.prevNode;
        list.lastMark.nextNode = next;
        next.prevNode = list.lastMark;
        prev.nextNode = list.firstMark;
        list.firstMark.prevNode = prev;
    }
    
    public T getAt( int index ) {
        LLNode<T> nodeAtIndex = this.getNoteAt( index );
        if ( nodeAtIndex == null ) {
            return null;
        }
        return nodeAtIndex.value;
    }
    
    public LLNode<T> getNoteAt( int index ) {
        if ( index < 0 ) {
            return null;
        }
        int count = -1;
        LLNode<T> current = this.firstMark;
        while ( ( current != null ) && ( count < index ) ) {
            current = current.nextNode;
            if ( ( current != null ) && !current.markerNode ) {
                count++;
            }            
        }
        return current;
    }
    
    public void remove() {
        if ( this.firstMark.prevNode != null ) {
            this.firstMark.prevNode.nextNode = this.lastMark.nextNode;
        }
        if ( this.lastMark.nextNode != null ) {
            this.lastMark.nextNode.prevNode = this.firstMark.prevNode;
        }
        this.firstMark.prevNode = null;
        this.lastMark.nextNode = null;
    }
    
    public int size() {
        int size = 0;
        LLNode<T> current = this.firstMark;
        while ( current != this.lastMark ) {
            if ( !current.markerNode ) {
                size++;
            }
            current = current.nextNode;
        }
        return size;
    }
    
    public boolean isEmpty() {
        return ( this.firstMark.nextNode == this.lastMark );
    }

    @Override
    public Iterator<T> iterator() {
        return new LListIterator<T>( this.firstMark, this.lastMark );
    }
    
    @Override
    public String toString() {
        return this.toString( false );
    }
    
    public String toString( boolean test ) {
        StringBuilder string = new StringBuilder();
        if ( !test ) {
            string.append( super.toString() ).append( ": " );
        }
        int size = 0;
        LLNode<T> current = this.firstMark;
        while ( current != this.lastMark ) {
            if ( !current.markerNode ) {
                string.append( "|" ).append( String.valueOf( current.value ) );
            } else if ( !test ) {
                string.append( "|" ).append( "MARKER" );
            }
            current = current.nextNode;
        }
        if ( !test ) {
            string.append( "|" ).append( "MARKER" );
        }
        string.append( "|" );
        if ( !test ) {
            string.append( " size: " ).append( size );
        }
        return string.toString();
    }
    
    private void listAddValid( LinkedList<T> list ) {
        if ( list == this ) {
            throw new IllegalArgumentException( "LList cannot add to Itself!" );
        }
        if ( ( list.firstMark.prevNode != null ) || ( list.lastMark.nextNode != null ) ) {
            throw new IllegalArgumentException( "LList is already added to another LList!" );
        }
        LLNode<T> current = list.firstMark;
        while ( current != list.lastMark ) {
            if ( current == this.firstMark ) {
                throw new IllegalArgumentException( "LList contains already this LList" );
            }
            current = current.nextNode;
        }
    }
    

    
    private static class LListIterator <T extends Object> implements Iterator<T> {
        
        private LLNode<T> currentNode;
        private LLNode<T> lastNode;
        
        public LListIterator( LLNode<T> startNode, LLNode<T> lastNode ) {
            this.currentNode = startNode;
            this.lastNode = lastNode;
        }

        @Override
        public boolean hasNext() {
            return ( this.findNextNode() != this.lastNode );
        }

        @Override
        public T next() {
            this.currentNode = this.findNextNode();
            return this.currentNode.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        private LLNode<T> findNextNode() {
            LLNode<T> nextNode = this.currentNode.nextNode;
            while ( nextNode.markerNode && ( nextNode != this.lastNode ) ) {
                nextNode = nextNode.nextNode;
            }
            return nextNode;
        }
        
    }
}
