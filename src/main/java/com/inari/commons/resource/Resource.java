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
package com.inari.commons.resource;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import com.inari.commons.config.ConfigObject;
import com.inari.commons.config.Configured;


public class Resource implements ConfigObject {
    
    private String configId;
    
    @Configured( required=true )
    private String name;
    @Configured( required=true )
    private ResourceLoader loader;
    
    public Resource() {
    }
    
    public Resource( String name, ResourceLoader loader ) {
        this.name = name;
        this.loader = loader;
    }

    @Override
    public String configId() {
        return configId;
    }

    @Override
    public void configId( String id ) {
        configId = id;
    }

    public String name() {
        return name;
    }
    
    public String path() {
        return loader.path( this );
    }
    
    public InputStream inputStream() {
        return inputStream( false );
    }
    
    public InputStream inputStream( boolean buffered ) {
        if ( buffered ) {
            InputStream inputStream = loader.inputStream( this );
            if ( inputStream == null ) {
                return null;
            }
            return new BufferedInputStream( inputStream );
        } else {
            return loader.inputStream( this );
        }
    }
    
    public URL url() {
        return loader.url( this );
    }
    
    public boolean exists() {
        return loader.exists( this );
    }
    
    public ResourceLoader loader() {
        return loader;
    }

    @Override
    public String toString() {
        return "Resource [name=" + name + ", path()=" + path() + "]";
    }

}
