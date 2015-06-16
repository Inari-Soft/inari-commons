package com.inari.commons.event;

public class CTestEvent2 extends Event<CombinedTestEventListener> {

    @Override
    public void notify( CombinedTestEventListener listener ) {
        listener.notifyEvent( this );
    }

}
