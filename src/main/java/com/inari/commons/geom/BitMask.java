package com.inari.commons.geom;

import java.util.BitSet;

import com.inari.commons.GeomUtils;
import com.inari.commons.StringUtils;

public final class BitMask {
    
    private final Rectangle region = new Rectangle();
    private final BitSet bits;
    
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
        bits = new BitSet( region.width + region.height );
        tmpBits = new BitSet( region.width + region.height );
    }
    
    public BitMask( Rectangle region ) {
        this.region.setFrom( region );
        bits = new BitSet( region.width + region.height );
        tmpBits = new BitSet( region.width + region.height );
    }
    
    public final int getWidth() {
        return region.width;
    }
    
    public final int getHeight() {
        return region.height;
    }
    
    public final void clearMask() {
        bits.clear();
        tmpBits.clear();
    }
    
    public final void setBit( int x, int y, boolean relativeToOrigin ) {
        if ( relativeToOrigin ) {
            setBit( x - region.x, y - region.y );
        } else {
            setBit( x, y );
        }
    }
    
    public final void setBit( int x, int y ) {
        if ( x < 0 || x >= region.width || y < 0 || y >= region.height ) {
            return;
        }
        
        bits.set( y * region.width + x );
    }
    
    public final void setRegion( final Rectangle region, boolean relativeToOrigin ) {
        setRegion( region.x, region.y, region.width, region.height, relativeToOrigin );
    }
    
    public final void setRegion( int x, int y, int width, int height ) {
        setRegion( x, y, width, height, true );
    }
    
    public final void setRegion( int x, int y, int width, int height, boolean relativeToOrigin ) {
        if ( relativeToOrigin ) {
            setIntersectionRegion( x, y, width, height );
        } else {
            setIntersectionRegion( x + region.x, y + region.y, width, height );
        }
    }
    
    private final void setIntersectionRegion( int xoffset, int yoffset, int width, int height ) {
        tmpRegion.x = xoffset;
        tmpRegion.y = yoffset;
        tmpRegion.width = width;
        tmpRegion.height = height;
        GeomUtils.intersection( region, tmpRegion, intersection );
        if ( intersection.area() <= 0 ) {
            return;
        }
        
        int x1 = intersection.x - region.x;
        int y1 = intersection.y - region.y;
        int width1 = x1 + intersection.width;
        int height1 = y1 + intersection.height;
        
        for ( int y = y1; y < height1; y++ ) {
            for ( int x = x1; x < width1; x++ ) {
                bits.set( y * region.width + x );
            }
        }
    }
    
    public final void and( final BitMask other ) {
        setTmpBits( other, 0, 0 );
        bits.and( tmpBits );
    }
    
    public final void and( final BitMask other, final int xoffset, final int yoffset ) {
        setTmpBits( other, xoffset, yoffset );
        bits.and( tmpBits );
    }
    
    public final void or( final BitMask other ) {
        setTmpBits( other, 0, 0 );
        bits.or( tmpBits );
    }
    
    public final void or( final BitMask other, final int xoffset, final int yoffset ) {
        setTmpBits( other, xoffset, yoffset );
        bits.or( tmpBits );
    }
    
    public static final void createIntersectionMask( final BitMask bitMask1, final Rectangle region, final BitMask result ) {
        createIntersectionMask( bitMask1, region, result, 0, 0 );
    }
    
    public static final void createIntersectionMask( final BitMask bitMask1, final BitMask bitMask2, final BitMask result ) {
        createIntersectionMask( bitMask1, bitMask2, result, 0, 0 );
    }
    
    public static final void createIntersectionMask( final BitMask bitMask1, final Rectangle region, final BitMask result, final int xoffset, final int yoffset ) {
        result.clearMask();
        
        result.region.x = 0;
        result.region.y = 0;
        result.region.width = 0;
        result.region.height = 0;
        result.tmpRegion.x = region.x + xoffset;
        result.tmpRegion.y = region.y + yoffset;
        result.tmpRegion.width = region.width;
        result.tmpRegion.height = region.height;
        
        GeomUtils.intersection( bitMask1.region, result.tmpRegion, result.intersection );
        if ( result.intersection.area() <= 0 ) {
            return;
        }
        
        result.region.setFrom( result.intersection );
        
        int x1 = result.intersection.x - bitMask1.region.x;
        int y1 = result.intersection.y - bitMask1.region.y;
        
        for ( int y = 0; y < result.intersection.height; y++ ) {
            for ( int x = 0; x < result.intersection.width; x++ ) {
                result.bits.set( 
                    y * result.region.width + x, 
                    bitMask1.bits.get( ( y + y1 ) * bitMask1.region.width + ( x + x1 ) )
                );
            }
        }
    }
    
    public static final void createIntersectionMask( final BitMask bitMask1, final BitMask bitMask2, final BitMask result, final int xoffset, final int yoffset ) {
        result.clearMask();
        
        result.region.x = 0;
        result.region.y = 0;
        result.region.width = 0;
        result.region.height = 0;
        result.tmpRegion.x = bitMask2.region.x + xoffset;
        result.tmpRegion.y = bitMask2.region.y + yoffset;
        result.tmpRegion.width = bitMask2.region.width;
        result.tmpRegion.height = bitMask2.region.height;
        
        GeomUtils.intersection( bitMask1.region, result.tmpRegion, result.intersection );
        if ( result.intersection.area() <= 0 ) {
            return;
        }
        
        result.region.setFrom( result.intersection );
        
        int x1 = result.intersection.x - bitMask1.region.x;
        int y1 = result.intersection.y - bitMask1.region.y;
        int x2 = result.intersection.x - ( bitMask2.region.x + xoffset );
        int y2 = result.intersection.y - ( bitMask2.region.y + yoffset );
        
        for ( int y = 0; y < result.intersection.height; y++ ) {
            for ( int x = 0; x < result.intersection.width; x++ ) {
                result.bits.set( 
                    y * result.region.width + x, 
                    bitMask1.bits.get( ( y + y1 ) * bitMask1.region.width + ( x + x1 ) ) &&
                    bitMask2.bits.get( ( y + y2 ) * bitMask2.region.width + ( x + x2 ) ) 
                );
            }
        }
    }

    private void setTmpBits( BitMask other, int xoffset, int yoffset ) {
        tmpRegion.x = other.region.x + xoffset;
        tmpRegion.y = other.region.y + yoffset;
        tmpRegion.width = other.region.width;
        tmpRegion.height = other.region.height;
        GeomUtils.intersection( region, tmpRegion, intersection );
        if ( intersection.area() <= 0 ) {
            return;
        }
        
        tmpBits.clear();
        
        // adjust intersection to origin
        int x1 = intersection.x - region.x;
        int y1 = intersection.y - region.y;
        int x2 = ( intersection.x == 0 )? other.region.width - intersection.width : 0;
        int y2 = ( intersection.y == 0 )? other.region.height - intersection.height : 0;
        
        for ( int y = 0; y < intersection.height; y++ ) {
            for ( int x = 0; x < intersection.width; x++ ) {
                tmpBits.set( ( y + y1 ) * region.width + ( x + x1 ), other.bits.get( ( y + y2 ) * other.region.width + ( x + x2 ) ) );
            }
        }
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

}
