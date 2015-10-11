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

import com.inari.commons.lang.aspect.AspectBitSet;

/** An Event with an Aspect to verify interested listeners.
 *  Use this to notify AspectedEventListener listening to specified Event and Aspect.
 *  
 *  @author andreas hefti
 *
 *  @param <L> The AspectedEventListener implementation type
 */
public abstract class AspectedEvent<L extends AspectedEventListener> extends Event<L> {
    
    /** Get the Aspect for this event. Only AspectedEventListener that are listening to the specified Event and Aspect are 
     *  going to be notified on this event.
     *  
     *  @return The Aspect of this event
     */
    public abstract AspectBitSet getAspect();
    
}
