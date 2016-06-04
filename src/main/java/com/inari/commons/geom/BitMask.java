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
    
    public final Rectangle region() {
        return region;
    }
    
    public final boolean isEmpty() {
        return bits.isEmpty();
    }
    
    public final BitMask fill() {
        clearMask();
        for ( int i = 0; i < region.width * region.height; i++ ) {
            bits.set( i );
        }
        return this;
    }
    
    public final void reset( Rectangle region ) {
        reset( region.x, region.y, region.width, region.height );
    }
    
    public final void reset( int x, int y, int width, int height ) {
        region.x = x;
        region.y = y;
        region.width = width;
        region.height = height;
        bits.clear();
        tmpBits.clear();
    }
    
    public final void clearMask() {
        bits.clear();
        tmpBits.clear();
    }
    
    public final void setBit( int index ) {
        bits.set( index );
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
    
    public boolean getBit( int x, int y ) {
        return bits.get( y * region.width + x );
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
    
    public final boolean hasIntersection( Rectangle region ) {
        GeomUtils.intersection( this.region, region, intersection );
        if ( intersection.area() <= 0 ) {
            return false;
        }
        
        int width = intersection.x + intersection.width;
        int height = intersection.y + intersection.height;
        for ( int y = intersection.y; y < height; y++ ) {
            for ( int x = intersection.x; x < width; x++ ) {
                if ( bits.get( y * this.region.width + x ) ) {
                    return true;
                }
            }
        }
        
        return false;
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
    
    

    
    public static final boolean createIntersectionMask( final Rectangle region, final BitMask bitmask, final BitMask result, final int xoffset, final int yoffset, final boolean adjustResult ) {
        bitmask.region.x += xoffset;
        bitmask.region.y += yoffset;
        
       boolean intersection = createIntersectionMask( bitmask, region, result );
        
        bitmask.region.x -= xoffset;
        bitmask.region.y -= yoffset;
        
        if ( adjustResult ) {
            result.region.x -= region.x;
            result.region.y -= region.y;
        }
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask, final Rectangle region, final BitMask result, final int xoffset, final int yoffset, final boolean adjustResult ) {
        result.tmpRegion.x = region.x + xoffset;
        result.tmpRegion.y = region.y + yoffset;
        result.tmpRegion.width = region.width;
        result.tmpRegion.height = region.height;
        
        boolean intersection = createIntersectionMask( bitmask, result.tmpRegion, result );
        
        if ( adjustResult ) {
            result.region.x -= bitmask.region.x;
            result.region.y -= bitmask.region.y;
        }
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask, final Rectangle region, final BitMask result, final boolean adjustResult ) {
        boolean intersection = createIntersectionMask( bitmask, region, result );
        
        if ( adjustResult ) {
            result.region.x -= bitmask.region.x;
            result.region.y -= bitmask.region.y;
        }
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final Rectangle region, final BitMask bitmask, final BitMask result, final boolean adjustResult ) {
        boolean intersection = createIntersectionMask( bitmask, region, result );
        
        if ( adjustResult ) {
            result.region.x -= region.x;
            result.region.y -= region.y;
        }
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask, final Rectangle region, final BitMask result ) {
        result.clearMask();
        
        GeomUtils.intersection( bitmask.region, region, result.region );
        if ( result.region.area() <= 0 ) {
            return false;
        }

        int x1 = result.region.x - bitmask.region.x;
        int y1 = result.region.y - bitmask.region.y;
        
        for ( int y = 0; y < result.region.height; y++ ) {
            for ( int x = 0; x < result.region.width; x++ ) {
                result.bits.set( 
                    y * result.region.width + x, 
                    bitmask.bits.get( ( y + y1 ) * bitmask.region.width + ( x + x1 ) )
                );
            }
        }
        
        return !result.bits.isEmpty();
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask1, final BitMask bitmask2, final BitMask result, final int xoffset, final int yoffset, boolean adjustResult ) {
        bitmask2.region.x += xoffset;
        bitmask2.region.y += yoffset;
        
        boolean intersection = createIntersectionMask( bitmask1, bitmask2, result );
        
        bitmask2.region.x -= xoffset;
        bitmask2.region.y -= yoffset;
        
        if ( adjustResult ) {
            result.region.x -= bitmask1.region.x;
            result.region.y -= bitmask1.region.y;
        }
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask1, final BitMask bitmask2, final BitMask result, boolean adjustResult ) {
        boolean intersection = createIntersectionMask( bitmask1, bitmask2, result );
        
        if ( adjustResult ) {
            result.region.x -= bitmask1.region.x;
            result.region.y -= bitmask1.region.y;
        }
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask1, final BitMask bitmask2, final BitMask result, final int xoffset, final int yoffset ) {
        bitmask2.region.x += xoffset;
        bitmask2.region.y += yoffset;
        
        boolean intersection = createIntersectionMask( bitmask1, bitmask2, result );
        
        bitmask2.region.x -= xoffset;
        bitmask2.region.y -= yoffset;
        
        return intersection;
    }
    
    public static final boolean createIntersectionMask( final BitMask bitmask1, final BitMask bitmask2, final BitMask result ) {
        result.clearMask();
        
        
        GeomUtils.intersection( bitmask1.region, bitmask2.region, result.region );
        if ( result.region.area() <= 0 ) {
            return false;
        }

        int x1 = result.region.x - bitmask1.region.x;
        int y1 = result.region.y - bitmask1.region.y;
        int x2 = result.region.x - bitmask2.region.x;
        int y2 = result.region.y - bitmask2.region.y;
        
        for ( int y = 0; y < result.region.height; y++ ) {
            for ( int x = 0; x < result.region.width; x++ ) {
                result.bits.set( 
                    y * result.region.width + x, 
                    bitmask1.bits.get( ( y + y1 ) * bitmask1.region.width + ( x + x1 ) ) &&
                    bitmask2.bits.get( ( y + y2 ) * bitmask2.region.width + ( x + x2 ) ) 
                );
            }
        }
        
        return !result.bits.isEmpty();
    }

    

}
