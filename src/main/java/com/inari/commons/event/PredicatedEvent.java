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

/** This is just a marker interface and used to bind PredicatedEventListener(s) to PredicatedEventListener.
 * 
 * @author andreas hefti
 *
 * @param <L> The type of PredicatedEventListener to which the PredicatedEvent type is bound to.
 */
public abstract class PredicatedEvent<L extends PredicatedEventListener> extends Event<L> {
    // Just a marker class
}
