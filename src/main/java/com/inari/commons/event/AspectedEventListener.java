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

/** An event listener definition which defines also a specified matching within an {@link Aspect} to verify if
 *  the listener is interested on a certain {@link AspectedEvent} or not.
 *  <p>
 *  Use this to listen to an {@link ApsectedEvent} that matches the {@link Aspect} on that the listener is interested in.
 *  The listener will only get notified on a specified {@link AspectedEvent} if it match the {@link Aspect}
 *  @author andreas hefti
 *
 */
public interface AspectedEventListener {
    
    /** Use this to implement the {@link Aspect} matching.
     * 
     * @param aspect the {@link Aspect} form {@link AspectedEvent} to test the matching
     * @return true if the listener is interested on a AspectedEvent with specified Aspect.
     */
    boolean match( IAspects aspects );

}
