package com.inari.commons.event;

import com.inari.commons.lang.aspect.AspectBitSet;

public class AspectedTestEventListener implements AspectedEventListener {
    
    private final AspectBitSet aspect;
    private AspectedEvent<?> lastCall;

    public AspectedTestEventListener( AspectBitSet aspect ) {
        this.aspect = aspect;
    }

    @Override
    public boolean match( AspectBitSet aspect ) {
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
