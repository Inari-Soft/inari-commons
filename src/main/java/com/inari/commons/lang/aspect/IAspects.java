package com.inari.commons.lang.aspect;

public interface IAspects extends Iterable<Aspect>{
    AspectGroup getAspectGroup();
    boolean valid();
    int size();
    boolean isEmpty();
    boolean include( Aspects aspects );
    boolean exclude( Aspects aspects );
    boolean intersects( Aspects aspects );
    boolean contains( Aspect aspect );
}
