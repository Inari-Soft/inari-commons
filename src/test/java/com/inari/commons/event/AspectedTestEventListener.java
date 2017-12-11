package com.inari.commons.event;

import com.inari.commons.lang.aspect.Aspects;
import com.inari.commons.lang.aspect.IAspects;

public class AspectedTestEventListener implements AspectedEventListener {
    
    private final Aspects aspects;
    private AspectedEvent<?> lastCall;

    public AspectedTestEventListener( Aspects aspects ) {
        this.aspects = aspects;
    }

    @Override
    public boolean match( IAspects aspects ) {
        return aspects.include( this.aspects );
    }



    public void notifyAspectedEvent( AspectedEvent<? extends AspectedEventListener> event ) {
        lastCall = event;
    }

    @Override
    public String toString() {
        return "AspectedTestEventListener [aspect=" + aspects + ", lastCall="
                + lastCall + "]";
    }

}
