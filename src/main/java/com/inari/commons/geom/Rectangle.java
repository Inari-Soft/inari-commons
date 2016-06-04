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
package com.inari.commons.geom;

import java.util.StringTokenizer;

import com.inari.commons.StringUtils;

/** A simple Rectangle with integer precision */
public final class Rectangle extends Position {

    /** The width of the Rectangle */
    public int width;
    /** The height of the Rectangle */
    public int height;

    /** Use this ti create a Rectangle with default attributes: x=0,y=0,width=0,height=0 */
    public Rectangle() {
        super( 0, 0 );
        width = 0;
        height = 0;
    }

    /** Use this as a copy constructor */
    public Rectangle ( Rectangle source ) {
        super( source.x, source.y );
        this.width = source.width;
        this.height = source.height;
    }

    /** Use this to create a Rectangle on specified Position with specified width and height
     *
     * @param pos the Position of the new Rectangle
     * @param width the width of the new Rectangle
     * @param height the height of the new Rectangle
     */
    public Rectangle( Position pos, int width, int height ) {
        super( pos );
        this.width = width;
        this.height = height;
    }

    /** Use this to create a new Rectangle with specified attributes.
     *
     * @param xpos the x axis position of the new Rectangle
     * @param ypos the y axis position of the new Rectangle
     * @param width the width of the new Rectangle
     * @param height the height of the new Rectangle
     */
    public Rectangle( int xpos, int ypos, int width, int height ) {
        super( xpos, ypos );
        this.width = width;
        this.height = height;
    }

    /** Use this to create a new Rectangle form configuration String value.
     *
     * @param rectString configuration String value. See also fromConfigString method documentation
     */
    public Rectangle( String rectString ) {
        fromConfigString( rectString );
    }

    /** Use this to get the area value (width * height) form this Rectangle.
     * @return the area value (width * height) form this Rectangle.
     */
    public final int area() {
        return width * height;
    }
    
    public final void set( int x, int y, int w, int h ) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    /** Use this to set the attributes of this Rectangle by another Rectangle.
     * @param other the other Rectangle to get/take the attributes from
     */
    public void setFrom( Rectangle other ) {
        super.setFrom( other );
        width = other.width;
        height = other.height;
    }
    
    public final void setFrom( Rectangle other, int x, int y ) {
        super.setFrom( other );
        this.x += x;
        this.y += y;
        width = other.width;
        height = other.height;
    }
    
    public final void clear() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    /** Use this to set  the Rectangle attributes from specified configuration String value with the
     *  format: [x],[y],[width],[height].
     *
     * @param stringValue the configuration String value
     * @throws IllegalArgumentException If the String value as a invalid format
     * @throws NumberFormatException if the x/y/width/height values from the String value aren't numbers
     */
    @Override
    public void fromConfigString( String stringValue ) {
        if ( !StringUtils.isBlank( stringValue ) ) {
            StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
            x = Integer.valueOf( st.nextToken() ).intValue();
            y = Integer.valueOf( st.nextToken() ).intValue();
            width = Integer.valueOf( st.nextToken() ).intValue();
            height = Integer.valueOf( st.nextToken() ).intValue();
        } 
    }

    /** Use this to get a configuration String value that represents this Rectangle
     *  and can be used to reset the attributes of a Rectangle by using fromConfigString
     *  The format is: [x],[y],[width],[height].
     *  @return A configuration String value that represents this Rectangle
     */
    @Override
    public String toConfigString() {
        StringBuilder sb = new StringBuilder();
        sb.append( x ).append( StringUtils.VALUE_SEPARATOR ).append( y ).append( StringUtils.VALUE_SEPARATOR );
        sb.append( width ).append( StringUtils.VALUE_SEPARATOR ).append( height );
        return sb.toString();
    }

    @Override
    public final int hashCode() {
        int result = 1;
        result = 37 * result + x;
        result = 37 * result + y;
        result = 37 * result + width;
        result = 37 * result + height;
        return result;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj;
            return ( 
                ( this.x == r.x ) && 
                ( this.y == r.y ) &&
                ( this.width == r.width ) &&
                ( this.height == r.height ) 
            );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "[x=" );
        builder.append( x );
        builder.append( ",y=" );
        builder.append( y );
        builder.append( ",width=" );
        builder.append( width );
        builder.append( ",height=" );
        builder.append( height );
        builder.append( "]" );
        return builder.toString();
    }

    


}
