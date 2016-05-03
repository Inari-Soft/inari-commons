package com.inari.commons.event;

import com.inari.commons.lang.aspect.Aspects;

public class TestAspectedEvent extends AspectedEvent<AspectedTestEventListener> {
    
    public static final EventTypeKey TYPE_KEY = createTypeKey( TestAspectedEvent.class );
    
    private final Aspects aspects;

    public TestAspectedEvent( Aspects aspects ) {
        super( TYPE_KEY );
        this.aspects = aspects;
    }
    
    @Override
    public Aspects getAspects() {
        return aspects;
    }

    @Override
    protected void notify( AspectedTestEventListener listener ) {
        listener.notifyAspectedEvent( this );
    }

    @Override
    public String toString() {
        return "AspectedEvent [aspect=" + aspects + "]";
    }

}
