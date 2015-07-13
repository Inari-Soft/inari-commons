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

import com.inari.commons.lang.indexed.Indexed;
import com.inari.commons.lang.indexed.IndexedBaseType;
import com.inari.commons.lang.indexed.IndexedType;
import com.inari.commons.lang.indexed.Indexer;

/** Base implementation of an Event handled by the IEventDispatcher.
 *  <p>
 *  Implement this for a specific Event type bound to a EventListener implementation (L)
 *  For example if a specified Event is needed to notify specified listener(s) creation and 
 *  implementation of the following interfaces/classes are needed:
 *  <p>
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
 *  </pre>
 *  
 *  
 * @author andreas hefti
 *
 * @param <L> The type of EventListener the is interested in the specified Event.
 */
public abstract class Event<L> implements IndexedType {
    
    private final int index;
    
    protected Event() {
        index = Indexer.getIndexForType( this );
    }
    
    @Override
    public final Class<? extends IndexedBaseType> indexedBaseType() {
        return Event.class;
    }

    @Override
    public final Class<? extends IndexedType> indexedType() {
        return this.getClass();
    }

    @Override
    public final int index() {
        return index;
    }
    
    /** Implements the notification of specified listener.
     *  For an implementation example have a look at the class documentation example
     *  
     *  @param listener the listener to notify.
     */
    public abstract void notify( final L listener );

}
