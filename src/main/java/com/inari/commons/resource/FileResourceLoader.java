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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class FileResourceLoader implements IResourceLoader {
    
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
