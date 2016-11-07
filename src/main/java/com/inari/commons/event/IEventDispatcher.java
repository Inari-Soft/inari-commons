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

/** An generic Event Dispatcher to register/unregister generic Listeners to listen to matching {@link Event}.
 *  A Listener interface can be created for specified needs to working with a {@link Event}<Listener> implementation.
 *  <p>
 *  Implementation and usage example:
 *  
 *  <pre>
 *  public interface TheEventListener extends EventListener {
 *  
 *      void onTheEvent( TheEvent event );
 *      
 *  }
 *  
 *  public class TheEvent extends Event<TheEventListener> {
 *  
 *      public final String anEventAttribute;
 *      
 *      public TheEvent( String anEventAttribute ) {
 *          super();
 *          this.anEventAttribute = anEventAttribute;
 *      }
 *      
 *      public final void notify( TheEventListener listener ) {
 *          listener.onTheEvent( this );
 *      }
 *  }
 *  
 *  public class MyTheEventListener implements TheEventListener {
 *  
 *      public void onTheEvent( TheEvent event ) {
 *          ... code that process the event for this listener ...
 *      }
 *  }
 *  
 *  MyTheEventListener myListener1 = new MyTheEventListener();
 *  MyTheEventListener myListener2 = new MyTheEventListener();
 *  ...
 *  eventDispatcher.register( TheEvent.class, myListener1 );
 *  eventDispatcher.register( TheEvent.class, myListener2 );
 *  ...
 *  eventDispatcher.notify( new TheEvent( "Hello Moon" );
 *  ...
 *  </pre>
 *  
 *  @author andreas hefti
 *
 */
public interface IEventDispatcher {
    
    /** Use this to register a EventPool for specified type of Event(s).
     *  Once a EventPool is registered for a type of Event, the events of that types gets pooled.
     *  
     * @param eventType the type of the Event
     * @param pool the EventPool instance that handles the pooling
     */
    void registerEventPool( final EventTypeKey eventType, final EventPool<?> pool );
    
    /** use this to stop pooling of Event of a specified type 
     * 
     * @param eventType the type of the Event
     */
    void unregisterEventPool( final EventTypeKey eventType );
    
    /** Use this to get a registered EventPool for a specified type of Event.
     * 
     * @param eventType the type of the Event
     * @return The registered instance of EventPool for specified type of Event. Or null of there is none registered.
     */
    <E extends Event<?>> EventPool<E> getEventPool( final EventTypeKey eventType );

    /** Register a Listener L to listen to specified type of {@link Event}.
     * 
     *  @param eventType The class type of the {@link Event} to listen at
     *  @param listener The listener to register and that gets informed by specified {@link Event}
     */
    <L> void register( EventTypeKey eventType, L listener );

    /** Unregister a specified Listener for a specified {@link Event} type.
     * 
     * @param eventType The {@link Event} (class) type 
     * @param listener the listener to unregister.
     * @return true if the specified listener was unregistered or false if there was no such listener registered
     */
    <L> boolean unregister( EventTypeKey eventType, L listener );

    /** Notifies all listeners that are interested on the specific type of Event within the specified {@link Event}.
     * 
     *  @param event The Event to send to the listeners.
     */
    <L> void notify( Event<L> event );

    /** Notifies all listeners that are interested on the specific type of Event with the specified {@link Aspect}
     *  within the specified Event.
     * 
     *  @param event The {@link AspectedEvent} to send to the {@link AspectedEventListener}.
     */
    <L extends AspectedEventListener> void notify( AspectedEvent<L> event );

    /** Notifies all {@link PredicatedEventListener} that are interested on the specific type of Event and that matches
     *  the specified {@link PredicatedEvent}, within the specified {@link Predicate}.
     * 
     *  @param event The {@link PredicatedEvent} to send to the {@link PredicatedEventListener}.
     */
    <L extends PredicatedEventListener> void notify( PredicatedEvent<L> event );

}
