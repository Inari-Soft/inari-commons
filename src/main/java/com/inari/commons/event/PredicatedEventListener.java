/*******************************************************************************
 * Copyright (c) 2015 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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

import com.inari.commons.lang.functional.Predicate;

/** An EventListener which implements also a specified Matcher for the PredicatedEvent that matches if
 *  the listener is interested on specified PredicatedEvent.
 *  <p>
 *  Use this to listen to an PredicatedEvent that matches the Event on that the listener is interested in.
 *  The listener will only get notifies on a specified PredicatedEvent if the Predicate matches
 *  @author andreas hefti
 *
 */
public interface PredicatedEventListener {
    
    /** Get the Matcher for this listener. Only MatchedEventListener that are listening to the specified Event and matches 
     *  the specified Event are going to be notified.
     *  
     *  @return The Matcher to check if the MatchedEventListener is interested in a specified Event
     */
    public <L extends PredicatedEventListener> Predicate<PredicatedEvent<L>> getMatcher();

}
