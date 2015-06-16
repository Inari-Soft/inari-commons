package com.inari.commons.event;

public class CTestEvent1 extends Event<CombinedTestEventListener> {

    @Override
    public void notify( CombinedTestEventListener listener ) {
        listener.notifyEvent( this );
    }

}
