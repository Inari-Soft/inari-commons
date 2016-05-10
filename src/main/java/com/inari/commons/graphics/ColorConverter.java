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

/** A simple image color converter that uses a int typed color map and BufferedImage.TYPE_INT_ARGB typed color values
 * 
 * @author andreashefti
 *
 */
public class ColorConverter extends RGBImageFilter implements ImageColorTransform {
    
    private int[][] colorMap;
    
    /** Use this to create a new ColorConverter with the spcified color conversion map
     * @param colorMap the {@link Color} conversion map wehere source {@link Color} maps a destionation color
     */
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
