package com.inari.commons.event;

import java.util.Stack;

import com.inari.commons.event.Event.EventTypeKey;
import com.inari.commons.lang.list.DynArray;

public final class EventPool {
    
    private final DynArray<Stack<Event<?>>> eventPooling = new DynArray<Stack<Event<?>>>();
    
    public final void populate( EventBuilder eventBuilder, int number ) {
        if ( number <= 0 ) {
            return;
        }
        
        for ( int i = 0; i < number; i++ ) {
            restoreEvent( eventBuilder.createEvent() );
        }
    }
    
    final void restoreEvent( Event<?> event ) {
        Stack<Event<?>> eventStack = eventPooling.get( event.index() );
        if ( eventStack == null ) {
            eventStack = new Stack<Event<?>>();
            eventPooling.set( event.index(), eventStack );
        }
        
        eventStack.push( event );
    }
    
    public final Event<?> getEvent( EventTypeKey type ) {
        Stack<Event<?>> eventStack = eventPooling.get( type.index() );
        if ( eventStack.isEmpty() ) {
            throw new IllegalStateException( "No Event of type: " + type + " available" );
        }
        
        return eventStack.pop();
    }
    
    public final Event<?> getEvent( EventTypeKey type, EventBuilder eventBuilder ) {
        Stack<Event<?>> eventStack = eventPooling.get( type.index() );
        if ( eventStack.isEmpty() ) {
            populate( eventBuilder, 10 );
        }
        
        return eventStack.pop();
    }

    public static interface EventBuilder {
        Event<?> createEvent();
    }

}
