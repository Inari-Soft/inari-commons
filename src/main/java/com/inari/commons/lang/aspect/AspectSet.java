package com.inari.commons.lang.aspect;

public interface AspectSet<A extends AspectSet<A>> {
    
    public boolean valid();
    
    public A set( Aspect aspect );
    
    public A add( A aspects );
    
    public A remove( A aspects );
    
    public boolean include( A aspects );
    
    public boolean exclude( A aspects );
    
    public boolean intersects( A aspects );
    
    public A getCopy();

}
