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
package com.inari.commons.resource;


public class ResourceException extends RuntimeException {
    
    private static final long serialVersionUID = 1917473536906395060L;
    
    private final Resource resource;

    public ResourceException( String msg ) {
        super( msg );
        resource = null;
    }

    public ResourceException( Throwable rootCause ) {
        super( rootCause );
        resource = null;
    }
    
    public ResourceException( String msg, Throwable rootCause ) {
        super( msg, rootCause );
        resource = null;
    }

    public ResourceException( Resource resource, String msg ) {
        super( msg );
        this.resource = resource;
    }
    
    public ResourceException( Resource resource, Throwable rootCause ) {
        super( rootCause );
        this.resource = resource;
    }
    
    public ResourceException( Resource resource, String msg, Throwable rootCause ) {
        super( msg, rootCause );
        this.resource = resource;
    }
    
    public Resource resource() {
        return resource;
    }

}
