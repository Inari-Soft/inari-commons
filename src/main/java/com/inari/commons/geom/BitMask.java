package com.inari.commons.geom;

import java.util.BitSet;

import com.inari.commons.GeomUtils;
import com.inari.commons.StringUtils;

public final class BitMask {
    
    private final Rectangle region = new Rectangle();
    private final BitSet bits;
    
    private final Rectangle origin = new Rectangle();
    private final Rectangle tmpRegion = new Rectangle();
    private final Rectangle intersection = new Rectangle();
    
    private final BitSet tmpBits;
    
    public BitMask( int width, int height ) {
        this( 0, 0, width, height );
    }
    
    public BitMask( int x, int y, int width, int height ) {
        region.x = x;
        region.y = y;
        region.width = width;
        region.height = height;
        origin.width = width;
        origin.height = height;
        bits = new BitSet( region.width + region.height );
        tmpBits = new BitSet( region.width + region.height );
    }
    
    public BitMask( Rectangle region ) {
        this.region.setFrom( region );
        bits = new BitSet( region.width + region.height );
        tmpBits = new BitSet( region.width + region.height );
        origin.width = region.width;
        origin.height = region.height;
    }
    
    public final int getWidth() {
        return region.width;
    }
    
    public final int getHeight() {
        return region.height;
    }
    
    public final void clearMask() {
        bits.clear();
    }
    
    public final void set( int x, int y, boolean relativeToOrigin ) {
        if ( relativeToOrigin ) {
            set( x - region.x, y - region.y );
        } else {
            set( x, y );
        }
    }
    
    public final void set( int x, int y ) {
        if ( x < 0 || x >= region.width || y < 0 || y >= region.height ) {
            return;
        }
        
        bits.set( y * region.width + x );
    }
    
    public final void setAll( final Rectangle region, boolean relativeToOrigin ) {
        setAll( region.x, region.y, region.width, region.height, relativeToOrigin );
    }
    
    public final void setAll( int x, int y, int width, int height, boolean relativeToOrigin ) {
        if ( relativeToOrigin ) {
            setAll( x - region.x, y - region.y, width, height );
        } else {
            setAll( x, y, width, height );
        }
    }
    
    public final void setAll( int x, int y, int width, int height ) {
        tmpRegion.x = x;
        tmpRegion.y = y;
        tmpRegion.width = width;
        tmpRegion.height = height;
        GeomUtils.intersection( origin, tmpRegion, intersection );
        if ( intersection.area() <= 0 ) {
            return;
        }
        
        int width1 = intersection.x + intersection.width;
        int height1 = intersection.y + intersection.height;
        
        for ( int y1 = intersection.y; y1 < height1; y1++ ) {
            for ( int x1 = intersection.x; x1 < width1; x1++ ) {
                bits.set( y1 * region.width + x1 );
            }
        }
    }
    
    public final void and( final BitMask other ) {
        setTmpBits( other, other.region.x, other.region.y );
        bits.and( tmpBits );
    }
    
    public final void and( final BitMask other, final int xoffset, final int yoffset ) {
        setTmpBits( other, other.region.x + xoffset, other.region.y + yoffset );
        bits.and( tmpBits );
    }
    
    public final void or( final BitMask other ) {
        setTmpBits( other, other.region.x, other.region.y );
        bits.or( tmpBits );
    }
    
    public final void or( final BitMask other, final int xoffset, final int yoffset ) {
        setTmpBits( other, xoffset, yoffset );
        bits.or( tmpBits );
    }

    private void setTmpBits( BitMask other, int xoffset, int yoffset ) {
//        int x = ( xoffset >= 0 )? xoffset: 0;
//        int y = ( yoffset >= 0 )? yoffset: 0;
//        int x1 = ( xoffset >= 0 )? 0: -xoffset;
//        int y1 = ( yoffset >= 0 )? 0: -yoffset;
        
        tmpRegion.x = xoffset;
        tmpRegion.y = yoffset;
        tmpRegion.width = other.region.width;
        tmpRegion.height = other.region.height;
        GeomUtils.intersection( origin, tmpRegion, intersection );
        if ( intersection.area() <= 0 ) {
            return;
        }
        
        tmpBits.clear();
        
        int width = intersection.x + intersection.width;
        int height = intersection.y + intersection.height;
        int x1 = intersection.x;
        int y1 = intersection.y;
        
        for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                tmpBits.set( y1 * region.width + x1, other.bits.get( y * other.region.width + x ) );
                x1++;
            }
            y1++;
        }
        
        
        
//        
//        while ( y < height1 ) {
//            while( x < width1 ) {
//                tmpBits.set( y * region.width + x, other.bits.get( y1 * other.region.width + x1 ) );
//                x++; x1++;
//            }
//            y++; y1++;
//        }
//        adjustToRegion( xoffset, yoffset, other.region.width, other.region.height );
//        if ( tmpRegion.area() <= 0 ) {
//            return;
//        }
//        
//        tmpBits.clear();
//        for ( int y1 = tmpRegion.y; y1 < tmpRegion.height; y1++ ) {
//            for ( int x1 = tmpRegion.x; x1 < tmpRegion.width; x1++ ) {
//                tmpBits.set( y1 * tmpRegion.width + x1, other.bits.get( bitIndex ) );
//            }
//        }
    }
    
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "BitMask [region=" );
        builder.append( region );
        builder.append( ", bits=\n" );
        builder.append( StringUtils.bitsetToString( bits, region.width, region.height ) );
        builder.append( "]" );
        return builder.toString();
    }

    private final void adjustToRegion( int x, int y, int width, int height ) {
        tmpRegion.x = ( x < region.x )? region.x : x;
        tmpRegion.y = ( y < region.y )? region.y : y;
        tmpRegion.width = ( tmpRegion.x + width > region.width )? region.width - ( tmpRegion.x - region.x ): width;
        tmpRegion.height = ( tmpRegion.y + height > region.height )? region.height - ( tmpRegion.y - region.y ): height;
    }

    

}
