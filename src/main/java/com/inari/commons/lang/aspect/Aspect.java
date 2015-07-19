package com.inari.commons.lang.aspect;

public interface Aspect<A extends Aspect<A>> {
    
    public boolean valid();
    
    public A add( A aspect );
    
    public A remove( A aspect );
    
    public boolean include( A aspect );
    
    public boolean exclude( A aspect );
    
    public boolean intersects( A aspect );
    
    public A getCopy();

}
