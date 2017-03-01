package com.inari.commons.event;

public class TestPredicatedEvent extends PredicatedEvent<PredicatedTestEventListener> {

    public static final EventTypeKey TYPE_KEY = createTypeKey( TestPredicatedEvent.class );

    public final String name;

    public TestPredicatedEvent( String name ) {
        super( TYPE_KEY );
        this.name = name;
    }

    @Override
    protected void notify( PredicatedTestEventListener listener ) {
        listener.onNotify( this );
    }
}
