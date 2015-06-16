package com.inari.commons.event;

public class SimpleTestEvent1 extends Event<GenericTestEventListener> {

    @Override
    public void notify( GenericTestEventListener listener ) {
        listener.notifyEventCall( this );
    }

    @Override
    public String toString() {
        return "SimpleTestEvent1";
    }

}
