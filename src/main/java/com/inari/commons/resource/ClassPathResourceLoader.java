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
