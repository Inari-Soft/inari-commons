package com.inari.commons.lang.functional;

public final class Tuple<L, R> {
    
    final L left;
    final R right;
    
    private final int hash;
    
    public Tuple( L left, R right ) {
        this.left = left;
        this.right = right;
        
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( left == null ) ? 0 : left.hashCode() );
        result = prime * result + ( ( right == null ) ? 0 : right.hashCode() );
        hash = result;
    }

    public final L getLeft() {
        return left;
    }

    public final R getRight() {
        return right;
    }

    @Override
    public final int hashCode() {
        return hash;
    }

    @Override
    public final boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Tuple<?,?> other = (Tuple<?,?>) obj;
        if ( left == null ) {
            if ( other.left != null )
                return false;
        } else if ( !left.equals( other.left ) )
            return false;
        if ( right == null ) {
            if ( other.right != null )
                return false;
        } else if ( !right.equals( other.right ) )
            return false;
        return true;
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "Tuple [left=" );
        builder.append( left );
        builder.append( ", right=" );
        builder.append( right );
        builder.append( "]" );
        return builder.toString();
    }

}
