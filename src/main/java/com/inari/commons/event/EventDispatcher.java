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
package com.inari.commons.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inari.commons.lang.functional.Predicate;
import com.inari.commons.lang.indexed.Indexer;
import com.inari.commons.lang.list.DynArray;

/** A simple, synchronous and none thread save implementation of the IEventDispatcher interface.
 * 
 * @author andreas hefti
 * @see IEventDispatcher
 */
public final class EventDispatcher implements IEventDispatcher {
    

    private final DynArray<List<?>> listeners = new DynArray<List<?>>();
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#register(java.lang.Class, L)
     */
    @Override
    public final <L> void register( Class<? extends Event<L>> eventType, L listener ) {
        synchronized( listeners ) {
            final List<L> listenersOfType = getListenersOfType( eventType, true );
            if ( !listenersOfType.contains( listener ) ) {
                listenersOfType.add( listener );
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#unregister(java.lang.Class, L)
     */
    @Override
    public final <L> boolean unregister( Class<? extends Event<L>> eventType, L listener ) {
        synchronized( listeners ) {
            final List<L> listenersOfType = getListenersOfType( eventType, false );
            return listenersOfType.remove( listener );
        }
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.Event)
     */
    @Override
    public final <L> void notify( final Event<L> event ) {
        synchronized( listeners ) {
            for ( L listener : this.<L>getListenersOfType( event.index(), false ) ) {
                event.notify( listener );
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.AspectedEvent)
     */
    @Override
    public final <L extends AspectedEventListener> void notify( final AspectedEvent<L> event ) {
        synchronized( listeners ) {
            for ( L listener : this.<L>getListenersOfType( event.index(), false ) ) {
                if ( listener.match( event.getAspect() ) ) {
                    event.notify( listener );
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.PredicatedEvent<L>)
     */
    @Override
    public final <L extends PredicatedEventListener> void notify( final PredicatedEvent<L> event ) {
        synchronized( listeners ) {
            for ( L listener : this.<L>getListenersOfType( event.index(), false ) ) {
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
    }

    @Override
    public String toString() {
        return "EventDispatcher [listeners=" + listeners + "]";
    }

    private final <L> List<L> getListenersOfType( Class<? extends Event<L>> eventType, boolean create ) {
        return getListenersOfType( 
            Indexer.getIndexForType( eventType, Event.class ), create 
        );
    }
    
    @SuppressWarnings( "unchecked" )
    private final <L> List<L> getListenersOfType( int eventIndex, boolean create ) {
        List<L> listenersOfType;
        if ( listeners.contains( eventIndex ) ) {
            listenersOfType = (List<L>) listeners.get( eventIndex );
        } else {
            if ( create ) {
                listenersOfType = new ArrayList<L>();
                listeners.set( eventIndex, listenersOfType );
            } else {
                return Collections.emptyList();
            }
        }

        return listenersOfType;
    }

}
