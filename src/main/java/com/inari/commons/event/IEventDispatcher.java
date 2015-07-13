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
 *  A Listener interface can be created for specified needs to working with a IEvent<Listener> implementation.
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

    /** Register a Listener L to listen to specified type of Event(s).
     * 
     *  @param eventType The class type of the Event(s) to listen at
     *  @param listener The listener to register and that gets informed by specified Event(s)
     */
    <L> void register( Class<? extends Event<L>> eventType, L listener );

    /** Unregister a specified Listener for a specified Event type.
     * 
     * @param eventType The Event (class) type 
     * @param listener the listener to unregister.
     * @return true if the specified listener was unregistered or false if there was no such listener registered
     */
    <L> boolean unregister( Class<? extends Event<L>> eventType, L listener );

    /** Notifies all listeners that are interested on the specific type of Event within the specified Event.
     * 
     *  @param event The Event to send to the listeners.
     */
    <L> void notify( Event<L> event );

    /** Notifies all listeners that are interested on the specific type of Event with the specified Aspect
     *  within the specified Event.
     * 
     *  @param event The AspectedEvent to send to the AspectedEventListener(s).
     */
    <L extends AspectedEventListener> void notify( AspectedEvent<L> event );

    /** Notifies all PredicatedEventListener that are interested on the specific type of Event and that matches
     *  the specified PredicatedEvent, within the specified Predicate.
     * 
     *  @param event The AspectedEvent to send to the AspectedEventListener(s).
     */
    <L extends PredicatedEventListener> void notify( PredicatedEvent<L> event );

}
