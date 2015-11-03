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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class FileResourceLoader implements ResourceLoader {
    
    private String configId;
    private File baseDir;
    
    public FileResourceLoader() {
        baseDir = new File( "/" );
    }
    
    public FileResourceLoader( File baseDir ) {
        setBaseDir( baseDir );
    }

    @Override
    public String configId() {
        return configId;
    }

    @Override
    public void configId( String id ) {
        configId = id;
    }

    public void setBaseDir( File baseDir ) {
        this.baseDir = baseDir.getAbsoluteFile();
    }
    
    public File getBaseDir() {
        return baseDir;
    }

    @Override
    public URL url( Resource resource ) {
        File file = toFile( resource );
        try {
            return file.toURI().toURL();
        } catch( MalformedURLException e ) {
            throw new ResourceException( resource, "Can't create URL for " + resource + " from " + file, e );
        }
    }

    @Override
    public String path( Resource resource ) {
        return url( resource ).toString();
    }
    
    protected File toFile( Resource resource ) {
        return new File( baseDir, resource.name() );
    }

    @Override
    public boolean exists( Resource resource ) {
        return toFile( resource ).exists();
    }

    @Override
    public InputStream inputStream( Resource resource ) {
        File file = toFile( resource );
        try {
            return new FileInputStream( file );
        } catch( FileNotFoundException e ) {
            throw new ResourceException( resource, "Can't load resource " + resource + " from " + file, e );
        }
    }

    @Override
    public void fromConfigString( String stringValue ) {
        baseDir = new File ( stringValue );
    }

    @Override
    public String toConfigString() {
        if ( baseDir == null ) {
            return( "/" );
        }
        return baseDir.getPath();
    }
    
    @Override
    public String toString() {
        return "file:" + baseDir;
    }

    @Override
    public String rootPath() {
        return String.valueOf( baseDir );
    }

}
