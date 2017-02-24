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
package com.inari.commons.event;

import com.inari.commons.event.Event.EventTypeKey;
import com.inari.commons.lang.Predicate;
import com.inari.commons.lang.indexed.Indexed;
import com.inari.commons.lang.list.DynArray;
import com.inari.commons.lang.list.StaticList;

/** A simple, synchronous and none thread save implementation of the {@link IEventDispatcher} interface.
 * 
 * @author andreas hefti
 * @see com.inari.commons.event.IEventDispatcher
 */
public final class EventDispatcher implements IEventDispatcher {
    
    private final IEventLog eventLog;
    
    private final DynArray<StaticList<?>> listeners = DynArray.createTyped( StaticList.class, 10, 10 );
    
    public EventDispatcher() {
        eventLog = null;
    }
    
    public EventDispatcher( IEventLog eventLog ) {
        this.eventLog = eventLog;
    }

    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#register(java.lang.Class, L)
     */
    @Override
    public final <L> void register( final EventTypeKey eventType, final L listener ) {
        final StaticList<L> listenersOfType = getListenersOfType( eventType, true );
        if ( !listenersOfType.contains( listener ) ) {
            listenersOfType.add( listener );
        }
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#unregister(java.lang.Class, L)
     */
    @Override
    public final <L> boolean unregister( final EventTypeKey eventType, final L listener ) {
        final StaticList<L> listenersOfType = getListenersOfType( eventType, false );
        return ( listenersOfType != null && listenersOfType.remove( listener ) >= 0 );
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.Event)
     */
    @Override
    public final <L> void notify( final Event<L> event ) {
        if ( eventLog != null ) {
            eventLog.log( event );
        }
        StaticList<L> listenersOfType = this.<L>getListenersOfType( event, false );
        if ( listenersOfType != null ) {
            for ( int i = 0; i < listenersOfType.size(); i++ ) {
                L listener = listenersOfType.get( i );
                if ( listener == null ) {
                    continue;
                }
                event.notify( listener );
            }
        }
        
        event.restore();
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.AspectedEvent)
     */
    @Override
    public final <L extends AspectedEventListener> void notify( final AspectedEvent<L> event ) {
        if ( eventLog != null ) {
            eventLog.log( event );
        }
        StaticList<L> listenersOfType = this.<L>getListenersOfType( event.indexedTypeKey(), false );
        if ( listenersOfType != null ) {
            for ( int i = 0; i < listenersOfType.size(); i++ ) {
                L listener = listenersOfType.get( i );
                if ( listener != null && listener.match( event.getAspects() ) ) {
                    event.notify( listener );
                }
            }
        }
        
        event.restore();
    }

    
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.PredicatedEvent<L>)
     */
    @Override
    public final <L extends PredicatedEventListener> void notify( final PredicatedEvent<L> event ) {
        if ( eventLog != null ) {
            eventLog.log( event );
        }
        StaticList<L> listenersOfType = this.<L>getListenersOfType( event, false );
        if ( listenersOfType != null ) {
            for ( int i = 0; i < listenersOfType.size(); i++ ) {
                L listener = listenersOfType.get( i );
                if ( listener == null ) {
                    continue;
                }
                Predicate<PredicatedEvent<L>> matcher = listener.getMatcher();
                if( matcher == null ) {
                    event.notify( listener );
                    return;
                }
                
                if ( matcher.apply( event ) ) {
                    event.notify( listener );
                } 
            }
        }
        
        event.restore();
    }

    @Override
    public String toString() {
        return "EventDispatcher [listeners=" + listeners + "]";
    }

    @SuppressWarnings( "unchecked" )
    private final <L> StaticList<L> getListenersOfType( final Indexed indexed, final boolean create ) {
        int eventIndex = indexed.index();
        StaticList<L> listenersOfType = null;
        if ( listeners.contains( eventIndex ) ) {
            listenersOfType = (StaticList<L>) listeners.get( eventIndex );
        } else {
            if ( create ) {
                listenersOfType = new StaticList<L>( 10, 10 );
                listeners.set( eventIndex, listenersOfType );
            }
        }

        return listenersOfType;
    }
}
