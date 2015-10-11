package com.inari.commons.event;

import com.inari.commons.lang.aspect.AspectBitSet;

public class TestAspectedEvent extends AspectedEvent<AspectedTestEventListener> {
    
    private final AspectBitSet aspect;

    public TestAspectedEvent( AspectBitSet aspect ) {
        super();
        this.aspect = aspect;
    }
    
    @Override
    public AspectBitSet getAspect() {
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
