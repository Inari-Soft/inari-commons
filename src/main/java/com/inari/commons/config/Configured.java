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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation for configured fields on a {@link IConfigObject} instance.
 *     
 *   @author andreas hefti
 *
 */
@Retention( RetentionPolicy.RUNTIME )
@Target(  ElementType.FIELD )
public @interface Configured {
    
    /** The name if configuration property. If empty the field name will be the name of configuration property.
     *  @return The name if configuration property
     */
    public String name() default "";
    
    /** Indicates whether the configuration property is mandatory or not
     *  @return if the configuration property is mandatory
     */
    public boolean required() default false ;
    
    /** The type of the configured field. Used on none instance-able Field typed
     *  E.g. List, Set... to define the specified instance type
     *  @return The type of the Configured field
     */
    public Class<?> type() default Object.class;
    
}
