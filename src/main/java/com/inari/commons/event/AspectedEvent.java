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

import com.inari.commons.lang.aspect.Aspect;
import com.inari.commons.lang.aspect.IAspects;

/** An {@link Event} with an {@link Aspect} to verify interested listeners.<p>
 *  Use this to notify {@link AspectedEventListener} listening to specified {@link Event} and {@link Aspect}.
 *  
 *  @author andreas hefti
 *
 *  @param <L> The {@link AspectedEventListener} implementation type
 */
public abstract class AspectedEvent<L extends AspectedEventListener> extends Event<L> {
    
    protected AspectedEvent( EventTypeKey indexedTypeKey ) {
        super( indexedTypeKey );
    }

    /** Get the {@link Aspect} for this event. Only {@link AspectedEventListener} that are listening to the specified {@link Event} and {@link Aspect} are 
     *  going to be notified on this event.
     *  
     *  @return The Aspect of this event
     */
    public abstract IAspects getAspects();
    
}
