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
package com.inari.commons.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inari.commons.lang.functional.Matcher;
import com.inari.commons.lang.indexed.Indexer;
import com.inari.commons.lang.list.DynArray;

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
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.IEvent)
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
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.IAspectedEvent)
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
     * @see com.inari.commons.event.IEventDispatcher#notify(com.inari.commons.event.IMatchedEvent)
     */
    @Override
    public final <L extends MatchedEventListener> void notify( final MatchedEvent<L> event ) {
        synchronized( listeners ) {
            for ( L listener : this.<L>getListenersOfType( event.index(), false ) ) {
                Matcher<MatchedEvent<L>> matcherForEvent = listener.getMatcherForEvent( event );
                if ( matcherForEvent != null && matcherForEvent.match( event ) ) {
                    event.notify( listener );
                } else {
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
