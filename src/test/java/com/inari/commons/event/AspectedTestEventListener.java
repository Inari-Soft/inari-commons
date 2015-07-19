package com.inari.commons.event;

import com.inari.commons.lang.aspect.IndexedAspect;

public class AspectedTestEventListener implements AspectedEventListener {
    
    private final IndexedAspect aspect;
    private AspectedEvent<?> lastCall;

    public AspectedTestEventListener( IndexedAspect aspect ) {
        this.aspect = aspect;
    }

    @Override
    public boolean match( IndexedAspect aspect ) {
        return aspect.include( this.aspect );
    }



    public void notifyAspectedEvent( AspectedEvent<? extends AspectedEventListener> event ) {
        lastCall = event;
    }

    @Override
    public String toString() {
        return "AspectedTestEventListener [aspect=" + aspect + ", lastCall="
                + lastCall + "]";
    }

}
