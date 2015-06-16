package com.inari.commons.event;

import com.inari.commons.lang.aspect.Aspect;

public class TestAspectedEvent extends AspectedEvent<AspectedTestEventListener> {
    
    private final Aspect aspect;

    public TestAspectedEvent( Aspect aspect ) {
        super();
        this.aspect = aspect;
    }
    
    @Override
    public Aspect getAspect() {
        return aspect;
    }

    @Override
    public void notify( AspectedTestEventListener listener ) {
        listener.notifyAspectedEvent( this );
    }

    @Override
    public String toString() {
        return "AspectedEvent [aspect=" + aspect + "]";
    }

    

}
