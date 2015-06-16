/**
* Copyright (c) 2015, Andreas Hefti, anhefti@yahoo.de 
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
package com.inari.commons.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.Map;

public class ColorConverter extends RGBImageFilter implements IImageColorTransform {
    
    private int[][] colorMap;
    
    public ColorConverter( Map<Color,Color> colorMap ) {
        this.colorMap = new int[ colorMap.size() ][ 2 ];
        int index = 0;
        for ( Map.Entry<Color,Color> entry : colorMap.entrySet() ) {
            this.colorMap[ index ][ 0 ] = entry.getKey().getRGB();
            this.colorMap[ index ][ 1 ] = entry.getValue().getRGB();
            index++;
        }
    }

    @Override
    public BufferedImage transform( BufferedImage image ) {
        ImageProducer ip = new FilteredImageSource(image.getSource(), this );
        Image transformedImage = Toolkit.getDefaultToolkit().createImage( ip );
        
        BufferedImage result = new BufferedImage(
            image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = result.createGraphics();
        g2.drawImage( transformedImage, 0, 0, null );
        g2.dispose();
        
        return result;
    }

    @Override
    public final int filterRGB( int x, int y, int rgb ) {
        for ( int i = 0; i < colorMap.length; i++ ) {
            if ( rgb == colorMap[ i ][ 0 ] ) {
                return colorMap[ i ][ 1 ];
            }
        }
        return rgb;
    }

}
