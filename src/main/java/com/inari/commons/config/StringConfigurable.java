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
package com.inari.commons.config;

/** Defines an object that is configurable within a String value, that can be write to a String phrase 
 *  and thats attributes can be set by a String phrase.
 *  This normaly is used from small data types like Point, Color or Rectangle that are itself used
 *  as a property value in component objects and. Within this interface, they implement a way
 *  to convert the property value from and to String value.
 */
public interface StringConfigurable {
    
    /** Sets the attribute of the object form String phrase.
     *  @param stringValue the String phrase
     */
    public void fromConfigString( String stringValue );
    
    /** Gets the attributes of the object in a String phrase
     *  @return a String representation of the object attributes
     */
    public String toConfigString();

}
