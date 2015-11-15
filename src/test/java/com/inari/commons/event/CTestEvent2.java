package com.inari.commons.event;

public class CTestEvent2 extends Event<CombinedTestEventListener> {

    public static final EventTypeKey TYPE_KEY = createTypeKey( CTestEvent2.class );

    protected CTestEvent2() {
        super( TYPE_KEY );
    }
    
    @Override
    public void notify( CombinedTestEventListener listener ) {
        listener.notifyEvent( this );
    }

}
