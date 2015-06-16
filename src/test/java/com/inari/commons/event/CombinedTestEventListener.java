package com.inari.commons.event;

public class CombinedTestEventListener {
    
    private boolean event1Notified = false;
    private boolean event2Notified = false;
    
    public boolean wasNotifiedByEvent1() {
        return event1Notified;
    }
    
    public boolean wasNotifiedByEvent2() {
        return event2Notified;
    }
    
    public void notifyEvent( CTestEvent1 event ) {
        event1Notified = true;
    }
    
    public void notifyEvent( CTestEvent2 event ) {
        event2Notified = true;
    }

    @Override
    public String toString() {
        return "CombinedTestEventListener [event1Notified=" + event1Notified
                + ", event2Notified=" + event2Notified + "]";
    }

}
