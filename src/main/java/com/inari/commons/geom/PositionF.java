package com.inari.commons.geom;

import java.util.StringTokenizer;

import com.inari.commons.StringUtils;
import com.inari.commons.config.StringConfigurable;

/** A simple position in a 2D Cartesian coordinate system with float precision. */
public class PositionF implements StringConfigurable {
    
    /** The x axis value of the position */
    public float x;
    /** The y axis value of the position */
    public float y;

    /** Use this to create a new Position with 0/0 initialization. */
    public PositionF() {
        x = 0;
        y = 0;
    }

    /** Use this to create a new Position with specified initialization
     *
     * @param x The x axis value of the position
     * @param y The y axis value of the position
     */
    public PositionF( int x, int y ) {
        this.x = x;
        this.y = y;
    }
    
    /** Use this to create a new Position with specified initialization
     *
     * @param x The x axis value of the position
     * @param y The y axis value of the position
     */
    public PositionF( float x, float y ) {
        this.x = x;
        this.y = y;
    }

    /** Use this to create a new Position form configuration String value.
     *
     * @param positionString configuration String value. See also fromConfigString method documentation
     */
    public PositionF( String positionString ) {
        fromConfigString( positionString );
    }

    /** Use this as a copy constructor */
    public PositionF( PositionF loc ) {
        setFrom( loc );
    }

    /** Use this to set the x/y axis values from specified Position p
     *  @param p the Position to get/take the attributes from
     */
    public final void setFrom( Position p ) {
        x = p.x;
        y = p.y;
    }
    
    /** Use this to set the x/y axis values from specified PositionF p
     *  @param p the PositionF to get/take the attributes from
     */
    public final void setFrom( PositionF p ) {
        x = p.x;
        y = p.y;
    }

    /** Use this to set  the x/y axis values from specified configuration String value with the
     *  format: [xValue],[yValue].
     *
     * @param stringValue the configuration String value
     * @throws IllegalArgumentException If the String value as a invalid format
     * @throws NumberFormatException if the x/y values from the String value aren't numbers
     */
    @Override
    public void fromConfigString( String stringValue ) {
        if ( StringUtils.isBlank( stringValue ) ) {
            x = 0; y = 0;
            return;
        }

        if ( !stringValue.contains( StringUtils.VALUE_SEPARATOR_STRING ) ) {
            throw new IllegalArgumentException( "The stringValue as invalid format: " + stringValue );
        }
        
        StringTokenizer st = new StringTokenizer( stringValue, StringUtils.VALUE_SEPARATOR_STRING );
        x = Integer.valueOf( st.nextToken() ).intValue();
        y = Integer.valueOf( st.nextToken() ).intValue();
    }

    /** Use this to get a configuration String value that represents this Position
     *  and can be used to reset the attributes of a Position by using fromConfigString
     *  The format is: [xValue],[yValue].
     *  @return A configuration String value that represents this Position
     */
    @Override
    public String toConfigString() {
        return x + StringUtils.VALUE_SEPARATOR_STRING + y;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "[x=" );
        builder.append( x );
        builder.append( ",y=" );
        builder.append( y );
        builder.append( "]" );
        return builder.toString();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        PositionF other = (PositionF) obj;
        if ( Float.floatToIntBits( x ) != Float.floatToIntBits( other.x ) )
            return false;
        if ( Float.floatToIntBits( y ) != Float.floatToIntBits( other.y ) )
            return false;
        return true;
    }

}
