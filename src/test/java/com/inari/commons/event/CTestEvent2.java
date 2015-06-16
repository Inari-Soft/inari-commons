package com.inari.commons.event;

public class CTestEvent2 extends IEvent<CombinedTestEventListener> {

    @Override
    public void notify( CombinedTestEventListener listener ) {
        listener.notifyEvent( this );
    }

}
