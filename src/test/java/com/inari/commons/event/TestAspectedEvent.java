package com.inari.commons.event;

import com.inari.commons.lang.aspect.IndexedAspect;

public class TestAspectedEvent extends AspectedEvent<AspectedTestEventListener> {
    
    private final IndexedAspect aspect;

    public TestAspectedEvent( IndexedAspect aspect ) {
        super();
        this.aspect = aspect;
    }
    
    @Override
    public IndexedAspect getAspect() {
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
