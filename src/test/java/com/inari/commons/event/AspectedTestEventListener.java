package com.inari.commons.event;

import com.inari.commons.lang.aspect.Aspect;

public class AspectedTestEventListener implements IAspectedEventListener {
    
    private final Aspect aspect;
    private IAspectedEvent<?> lastCall;

    public AspectedTestEventListener( Aspect aspect ) {
        this.aspect = aspect;
    }

    @Override
    public boolean match( Aspect aspect ) {
        return aspect.include( this.aspect );
    }



    public void notifyAspectedEvent( IAspectedEvent<? extends IAspectedEventListener> event ) {
        lastCall = event;
    }

    @Override
    public String toString() {
        return "AspectedTestEventListener [aspect=" + aspect + ", lastCall="
                + lastCall + "]";
    }

}
