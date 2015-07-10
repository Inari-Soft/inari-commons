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

import java.io.InputStream;
import java.net.URL;

import com.inari.commons.config.ConfigObject;
import com.inari.commons.config.StringConfigurable;


public interface ResourceLoader extends ConfigObject, StringConfigurable {
    
    public String rootPath();
    
    public boolean exists( Resource resource );
    
    public InputStream inputStream( Resource resource );
    
    public String path( Resource resource );
    
    public URL url( Resource resource );
    
    

}
