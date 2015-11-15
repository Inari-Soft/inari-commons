package com.inari.commons.event;

public class CTestEvent1 extends Event<CombinedTestEventListener> {
    
    public static final EventTypeKey TYPE_KEY = createTypeKey( CTestEvent1.class );

    protected CTestEvent1() {
        super( TYPE_KEY );
    }

    @Override
    public void notify( CombinedTestEventListener listener ) {
        listener.notifyEvent( this );
    }

}
