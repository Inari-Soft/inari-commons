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

import java.io.InputStream;
import java.net.URL;


public class ClassPathResourceLoader implements ResourceLoader {
    
    private String configId;
    private String rootPath;
    private ClassLoader classLoader = getClass().getClassLoader();
    
    public ClassPathResourceLoader() {
        rootPath = "";
    }
    
    public ClassPathResourceLoader( String packageName ) {
        this.rootPath = packageName.replace( '.', '/' ) + '/';
    }

    @Override
    public String configId() {
        return configId;
    }

    @Override
    public void configId( String id ) {
        this.configId = id;
    }
    
    @Override
    public void fromConfigString( String stringValue ) {
        rootPath = stringValue;
    }

    @Override
    public String toConfigString() {
        return rootPath;
    }
    
    public ClassPathResourceLoader( Class<?> clazz ) {
        this( clazz.getPackage() );
    }
        
    public ClassPathResourceLoader( Package pkg ) {
        this( pkg.getName() );
    }
    
    
    public ClassPathResourceLoader classLoader( ClassLoader classLoader ) {
        this.classLoader = classLoader;
        return this;
    }
    
    public ClassLoader classLoader() {
        return classLoader;
    }

    @Override
    public String rootPath() {
        return rootPath;
    }

    @Override
    public boolean exists( Resource resource ) {
        return url( resource ) != null;
    }

    @Override
    public InputStream inputStream( Resource resource ) {
        return classLoader().getResourceAsStream( rootPath + resource.name() );
    }

    @Override
    public URL url( Resource resource ) {
        URL url = classLoader().getResource( rootPath + resource.name() );
        return url;
    }

    @Override    
    public String path( Resource resource ) {
        return "classpath:/" + rootPath + resource.name();
    }
    
    @Override
    public String toString() {
        return "classpath:/" + rootPath;
    }

}
