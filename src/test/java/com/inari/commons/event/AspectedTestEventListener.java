package com.inari.commons.event;

import com.inari.commons.lang.aspect.Aspect;

public class AspectedTestEventListener implements AspectedEventListener {
    
    private final Aspect aspect;
    private AspectedEvent<?> lastCall;

    public AspectedTestEventListener( Aspect aspect ) {
        this.aspect = aspect;
    }

    @Override
    public boolean match( Aspect aspect ) {
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
