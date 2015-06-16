/**
* Copyright (c) 2009-2013, Andreas Hefti, anhefti@yahoo.de 
* All rights reserved.
*
* This software is licensed to you under the Apache License, Version 2.0
* (the "License"); You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* . Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* . Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* . Neither the name "InariUtils" nor the names of its contributors may be
* used to endorse or promote products derived from this software without
* specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package com.inari.commons.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation for configured fields on a IConfigObject instance.
 *     Expects to a configuration property or reference on ConfigData of the specified IConfigObject instance
 *     The name, if it is set, expects the name of the configuration property in ConfigData.
 *     If the name is not set (empty) the name of the field will be the name of the configuration property.
 *     
 *     @author ahefti
 *
 */
@Retention( RetentionPolicy.RUNTIME )
@Target(  ElementType.FIELD )
public @interface Configured {
    
    /** The name if configuration property. If empty the field name will be the name of configuration property.
     *     @return The name if configuration property
     */
    public String name() default "";
    
    /** Indicates whether the configuration property is mandatory or not
     *     @return if the configuration property is mandatory
     */
    public boolean required() default false ;
    
    /** The type of the Configured field. Used on none instance-able Field typed
     *     E.g. List, Set... to define the specified instance type
     *     @return The type of the Configured field
     */
    public Class<?> type() default Object.class;
    
}
