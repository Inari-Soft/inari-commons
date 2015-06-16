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
package com.inari.commons.resource;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import com.inari.commons.config.Configured;
import com.inari.commons.config.ConfigObject;


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
