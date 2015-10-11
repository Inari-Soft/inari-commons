package com.inari.commons.lang.aspect;

public interface AspectSet<A extends AspectSet<A>> {
    
    public boolean valid();
    
    public A add( A aspect );
    
    public A remove( A aspect );
    
    public boolean include( A aspect );
    
    public boolean exclude( A aspect );
    
    public boolean intersects( A aspect );
    
    public A getCopy();

}
