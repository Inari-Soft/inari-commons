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

/** An generic Event Dispatcher to register/unregister generic Listeners to listen to matching Events.
 *  A Listener interface can be created for specified needs to working with a IEvent<Listener> implementation
 *  
 *  @author anhefti@yahoo.de
 *
 */
public interface IEventDispatcher {

    /** Register a Listener L to listen to specified type of event that is familiar with the 
     *  the Listener.
     *  @param eventType The class type of the Event's to listen at
     *  @param listener The listener that gets informed by specified events
     */
    <L> void register( Class<? extends Event<L>> eventType, L listener );

    <L> boolean unregister( Class<? extends Event<L>> eventType, L listener );

    <L> void notify( Event<L> event );

    <L extends AspectedEventListener> void notify( AspectedEvent<L> event );

    <L extends MatchedEventListener> void notify( MatchedEvent<L> event );

}
