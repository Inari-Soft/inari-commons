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

        public boolean hasNext() {
            return ( this.findNextNode() != this.lastNode );
        }

        public T next() {
            this.currentNode = this.findNextNode();
            return this.currentNode.value;
        }

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
    
    public interface INode<T> {
    
        T value();
        
        INode<T> nextNode();
        
        INode<T> previousNode();
        
        void remove();
    
    }
    
    public static final class LLNode<T> implements INode<T> {
    
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
        
        public final T value() {
            return value;
        }
    
        public final INode<T> nextNode() {
            return nextNode;
        }
    
        public final INode<T> previousNode() {
            return prevNode;
        }
        
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

}
