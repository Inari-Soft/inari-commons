package com.inari.commons.event;

public class GenericTestEventListener {
    
    private IEvent<GenericTestEventListener> lastCall;
    
    public void notifyEventCall( IEvent<GenericTestEventListener> event ) {
        lastCall = event;
    }

    @Override
    public String toString() {
        return "GenericTestEventListener [lastCall=" + lastCall + "]";
    }

}
