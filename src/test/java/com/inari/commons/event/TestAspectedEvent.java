package com.inari.commons.event;

import com.inari.commons.lang.aspect.AspectBitSet;

public class TestAspectedEvent extends AspectedEvent<AspectedTestEventListener> {
    
    public static final EventTypeKey TYPE_KEY = createTypeKey( TestAspectedEvent.class );
    
    private final AspectBitSet aspect;

    public TestAspectedEvent( AspectBitSet aspect ) {
        super( TYPE_KEY );
        this.aspect = aspect;
    }
    
    @Override
    public AspectBitSet getAspect() {
        return aspect;
    }

    @Override
    protected void notify( AspectedTestEventListener listener ) {
        listener.notifyAspectedEvent( this );
    }

    @Override
    public String toString() {
        return "AspectedEvent [aspect=" + aspect + "]";
    }

}
