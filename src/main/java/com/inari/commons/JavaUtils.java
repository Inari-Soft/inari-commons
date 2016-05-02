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
package com.inari.commons;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class JavaUtils {
    
    public static final Random random = new Random();
    
    public static final void gc() {
        Object obj = new Object();
        WeakReference<Object> ref = new WeakReference<Object>( obj );
        obj = null;
        while ( ref.get() != null ) {
            System.gc();
        }
    }

    @SuppressWarnings( "unchecked" )
    public static final <T> Set<T> createHashSet( final Object... values ) {
        Set<T> result = new HashSet<T>();
        for ( Object value : values ) {
            result.add( (T) value );
        }
        return result;
    }

}
