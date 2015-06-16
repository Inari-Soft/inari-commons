package com.inari.commons.event;

public class CTestEvent1 extends IEvent<CombinedTestEventListener> {

    @Override
    public void notify( CombinedTestEventListener listener ) {
        listener.notifyEvent( this );
    }

}
