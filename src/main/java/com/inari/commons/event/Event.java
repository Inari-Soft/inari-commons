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

import com.inari.commons.lang.aspect.AspectGroup;
import com.inari.commons.lang.indexed.Indexed;
import com.inari.commons.lang.indexed.IndexedType;
import com.inari.commons.lang.indexed.IndexedTypeKey;
import com.inari.commons.lang.indexed.Indexer;

/** Base implementation of an Event handled by the {@link IEventDispatcher}.
 *  <p>
 *  Implement this for a specific Event type bound to a EventListener implementation (L)
 *  For example if a specified Event is needed to notify specified listener(s) creation and 
 *  implementation of the following interfaces/classes are needed:
 *  <p>
 *  <pre>
 *  public interface TheEventListener {
 *  
 *      void onEvent( TheEvent event );
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
 *          listener.onEvent( this );
 *      }
 *  }
 *  </pre>
 *  
 *  
 * @author andreas hefti
 *
 * @param <L> The type of EventListener the is interested in the specified Event.
 */
public abstract class Event<L> implements IndexedType, Indexed {
    
    /** The {@link EventTypeKey} that specified type type of event */
    protected final EventTypeKey indexedTypeKey;
    
    protected Event( EventTypeKey indexedTypeKey ) {
        this.indexedTypeKey = indexedTypeKey;
    }

    /** Use this to get the index/type id of the specified Event */ 
    @Override
    public final int index() {
        return indexedTypeKey.index();
    }

    /** Use this to get the {@link EventTypeKey} that specified type type of event */
    @Override
    public final IndexedTypeKey indexedTypeKey() {
        return indexedTypeKey;
    }

    /** Implements the notification of specified listener.
     *  For an implementation example have a look at the class documentation example
     *  
     *  @param listener the listener to notify.
     */
    protected abstract void notify( final L listener );
    
    /** This is called within the IEventDispatcher after the Event has passed all its listeners and can be restored.
     *  Useful for event pooling. 
     *  This is an empty implementation and does nothing. Override this to get notified on restore
     */
    protected void restore() {
        // This is an empty implementation and does nothing. Override this to get notified on restore
    }
    
    
    /** This is internally used to create index for specified event type */
    protected static final EventTypeKey createTypeKey( Class<? extends Event<?>> type ) {
        return Indexer.createIndexedTypeKey( EventTypeKey.class, type );
    }
    
    /** Use this to get the EventTypeKey for e given Event class type */
    public static final EventTypeKey getTypeKey( Class<? extends Event<?>> type ) {
        return Indexer.getIndexedTypeKey( EventTypeKey.class, type );
    }
    
    /** Defines a type key for a specified event.
     *  This is used for indexing the event types.
     */
    public static final class EventTypeKey extends IndexedTypeKey {
        
        private static final AspectGroup ASPECT_GROUP = new AspectGroup( "EventTypeKey" );
        
        EventTypeKey( Class<? extends Event<?>> indexedType ) {
            super( indexedType );
        }
        
        @Override
        public final Class<?> baseType() {
            return Event.class;
        }

        @Override
        public final AspectGroup aspectGroup() {
            return ASPECT_GROUP;
        }
    }

}
