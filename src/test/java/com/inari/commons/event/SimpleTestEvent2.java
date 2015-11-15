package com.inari.commons.event;

public class SimpleTestEvent2 extends Event<GenericTestEventListener> {
    
    public static final EventTypeKey TYPE_KEY = createTypeKey( SimpleTestEvent2.class );
    
    public SimpleTestEvent2() {
        super( TYPE_KEY );
    }

    @Override
    public void notify( GenericTestEventListener listener ) {
        listener.notifyEventCall( this );
    }

    @Override
    public String toString() {
        return "SimpleTestEvent2";
    }

}
