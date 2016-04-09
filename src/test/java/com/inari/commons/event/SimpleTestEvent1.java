package com.inari.commons.event;

public class SimpleTestEvent1 extends Event<GenericTestEventListener> {
    
    public static final EventTypeKey TYPE_KEY = createTypeKey( SimpleTestEvent1.class );
    
    public SimpleTestEvent1() {
        super( TYPE_KEY );
    }

    @Override
    protected void notify( GenericTestEventListener listener ) {
        listener.notifyEventCall( this );
    }

    @Override
    public String toString() {
        return "SimpleTestEvent1";
    }

}
