package com.inari.commons.lang.aspect;

/** This defines a single aspect within a integer id.
 */ 
public interface Aspect {
    
    AspectGroup aspectGroup();
    
    /** Gets the integer id of the aspect */
    int index();
    
    String name();

}
