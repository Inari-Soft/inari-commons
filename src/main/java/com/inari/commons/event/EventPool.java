package com.inari.commons.event;

import java.util.ArrayDeque;

public abstract class EventPool<E extends Event<?>> {
    
    private final ArrayDeque<E> eventPooling = new ArrayDeque<E>();
    
    public final void populate( int number ) {
        if ( number <= 0 ) {
            return;
        }
        
        for ( int i = 0; i < number; i++ ) {
            eventPooling.add( create() );
        }
    }
    
    public final E get() {
        if ( eventPooling.isEmpty() ) {
            populate( 10 );
        }
        
        return eventPooling.pop();
    }
    
    public final void restore( E event ) {
        clearEvent( event );
        eventPooling.push( event );
    }
    
    public final void clear() {
        eventPooling.clear();
    }
    
    protected abstract E create();
    
    protected abstract void clearEvent( E event );

    

}
