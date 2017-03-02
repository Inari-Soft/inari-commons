package com.inari.commons.event;

import java.util.ArrayList;
import java.util.List;
import com.inari.commons.lang.Predicate;

public class PredicatedTestEventListener implements PredicatedEventListener {

    private List<TestPredicatedEvent> notifiedEvents = new ArrayList<TestPredicatedEvent>();
    private Predicate<TestPredicatedEvent> predicate;

    public PredicatedTestEventListener( Predicate<TestPredicatedEvent> predicate ) {
        this.predicate = predicate;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public Predicate<TestPredicatedEvent> getMatcher() {
        return predicate;
    }

    public void onNotify( TestPredicatedEvent event ) {
        notifiedEvents.add( event );
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer( "" );
        sb.append( "notifiedEvents=" ).append( notifiedEvents.size() );
        return sb.toString();
    }
}
