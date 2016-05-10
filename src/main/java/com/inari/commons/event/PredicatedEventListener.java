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

import com.inari.commons.lang.Predicate;

/** An event listener which implements also a specified matcher for the {@link PredicatedEvent} that matches if
 *  the listener is interested on specified {@link PredicatedEvent}.
 *  <p>
 *  Use this to listen to an {@link PredicatedEvent} that matches the Event on that the listener is interested in.
 *  The listener will only get notifies on a specified {@link PredicatedEvent} if the {@link Predicate} matches
 *  @author andreas hefti
 *
 */
public interface PredicatedEventListener {
    
    /** Get the matcher for this listener. Only listeners that are listening to the specified {@link Event} and matches 
     *  the specified {@link Predicate} are going to be notified.
     *  
     *  @return The Matcher to check if the MatchedEventListener is interested in a specified Event
     */
    public <L extends PredicatedEventListener> Predicate<PredicatedEvent<L>> getMatcher();

}
