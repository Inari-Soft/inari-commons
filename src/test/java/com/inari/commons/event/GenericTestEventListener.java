package com.inari.commons.event;

public class GenericTestEventListener {
    
    private Event<GenericTestEventListener> lastCall;
    
    public void notifyEventCall( Event<GenericTestEventListener> event ) {
        lastCall = event;
    }

    @Override
    public String toString() {
        return "GenericTestEventListener [lastCall=" + lastCall + "]";
    }

}
