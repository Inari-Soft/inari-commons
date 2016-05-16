package com.inari.commons.event;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inari.commons.lang.aspect.Aspect;
import com.inari.commons.lang.aspect.AspectGroup;
import com.inari.commons.lang.aspect.Aspects;
import com.inari.commons.lang.indexed.Indexer;

public class EventDispatcherTest {
    
    @Before
    public void init() {
        Indexer.clear();
    }
    
    @Test
    public void testCreation() {
        IEventDispatcher eventDispatcher = new EventDispatcher();
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[], size()=0, capacity()=0]]", 
            eventDispatcher.toString() 
        );
    }
    
    @Test
    public void testNotificationWithSimpleListeners() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        GenericTestEventListener listener11 = new GenericTestEventListener();
        GenericTestEventListener listener12 = new GenericTestEventListener();
        GenericTestEventListener listener13 = new GenericTestEventListener();
        
        GenericTestEventListener listener21 = new GenericTestEventListener();
        GenericTestEventListener listener22 = new GenericTestEventListener();
        
        eventDispatcher.register( SimpleTestEvent1.TYPE_KEY, listener11 );
        eventDispatcher.register( SimpleTestEvent1.TYPE_KEY, listener12 );
        eventDispatcher.register( SimpleTestEvent1.TYPE_KEY, listener13 );
        
        eventDispatcher.register( SimpleTestEvent2.TYPE_KEY, listener21 );
        eventDispatcher.register( SimpleTestEvent2.TYPE_KEY, listener22 );
        
        assertEquals( 
              "EventDispatcher [listeners=DynArray [list=[" +
              "[GenericTestEventListener [lastCall=null], GenericTestEventListener [lastCall=null], GenericTestEventListener [lastCall=null]], " +
              "[GenericTestEventListener [lastCall=null], GenericTestEventListener [lastCall=null]], null, null, null, null, null, null, null, null" +
              "], size()=2, capacity()=10]]",
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new SimpleTestEvent1() );
        
        assertEquals(
            "EventDispatcher [listeners=DynArray [list=[" +
            "[GenericTestEventListener [lastCall=SimpleTestEvent1], GenericTestEventListener [lastCall=SimpleTestEvent1], GenericTestEventListener [lastCall=SimpleTestEvent1]], " +
            "[GenericTestEventListener [lastCall=null], GenericTestEventListener [lastCall=null]], null, null, null, null, null, null, null, null" +
            "], size()=2, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new SimpleTestEvent2() );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[GenericTestEventListener [lastCall=SimpleTestEvent1], GenericTestEventListener [lastCall=SimpleTestEvent1], GenericTestEventListener [lastCall=SimpleTestEvent1]], " +
            "[GenericTestEventListener [lastCall=SimpleTestEvent2], GenericTestEventListener [lastCall=SimpleTestEvent2]], null, null, null, null, null, null, null, null" +
            "], size()=2, capacity()=10]]", 
            eventDispatcher.toString() 
        );
    }
    
    @Test
    public void testNotificationWithCombindeListeners() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        
        CombinedTestEventListener listener1 = new CombinedTestEventListener();
        CombinedTestEventListener listener2 = new CombinedTestEventListener();
        
        eventDispatcher.register( CTestEvent1.TYPE_KEY, listener1 );
        eventDispatcher.register( CTestEvent2.TYPE_KEY, listener1 );
        eventDispatcher.register( CTestEvent2.TYPE_KEY, listener2 );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[CombinedTestEventListener [event1Notified=false, event2Notified=false]], " +
            "[CombinedTestEventListener [event1Notified=false, event2Notified=false], CombinedTestEventListener [event1Notified=false, event2Notified=false]]" +
            ", null, null, null, null, null, null, null, null" +
            "], size()=2, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new CTestEvent1() );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[CombinedTestEventListener [event1Notified=true, event2Notified=false]], " +
            "[CombinedTestEventListener [event1Notified=true, event2Notified=false], CombinedTestEventListener [event1Notified=false, event2Notified=false]]" +
            ", null, null, null, null, null, null, null, null" +
            "], size()=2, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new CTestEvent2() );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[CombinedTestEventListener [event1Notified=true, event2Notified=true]], " +
            "[CombinedTestEventListener [event1Notified=true, event2Notified=true], CombinedTestEventListener [event1Notified=false, event2Notified=true]]" +
            ", null, null, null, null, null, null, null, null" +
            "], size()=2, capacity()=10]]", 
            eventDispatcher.toString() 
        );
    }
    
    @Test
    public void testAspectedEvent() {
        // create AspectGroup and Aspect's for test
        final AspectGroup aspectGroup = new AspectGroup( "TestGroup" );
        List<Aspect> aspects = new ArrayList<Aspect>();
        for ( int i = 0; i < 5; i++ ) {
            aspects.add( aspectGroup.createAspect( "aspect_" + i ) );
        }
        
        EventDispatcher eventDispatcher = new EventDispatcher();
        
        Aspects filterAspect = aspectGroup.createAspects( aspects.get( 1 ), aspects.get( 3 ) );
        Aspects noMatchAspect1 = aspectGroup.createAspects( aspects.get( 1 ), aspects.get( 2 ) );
        Aspects noMatchAspect2 = aspectGroup.createAspects( aspects.get( 1 ), aspects.get( 4 ) );
        Aspects matchAspect1 = aspectGroup.createAspects( aspects.get( 1 ), aspects.get( 3 ) );
        Aspects matchAspect2 = aspectGroup.createAspects( aspects.get( 1 ), aspects.get( 2 ), aspects.get( 3 ) );
        
        AspectedTestEventListener listener = new AspectedTestEventListener( filterAspect );
        
        eventDispatcher.register( TestAspectedEvent.TYPE_KEY, listener );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspects [group=TestGroup {aspect_1, aspect_3}], lastCall=null]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( noMatchAspect1 ) );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspects [group=TestGroup {aspect_1, aspect_3}], lastCall=null]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]",
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( noMatchAspect2 ) );
        
        assertEquals( 
             "EventDispatcher [listeners=DynArray [list=[" +
             "[AspectedTestEventListener [aspect=Aspects [group=TestGroup {aspect_1, aspect_3}], lastCall=null]], null, null, null, null, null, null, null, null, null" +
             "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( matchAspect1 ) );
        
        assertEquals(
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspects [group=TestGroup {aspect_1, aspect_3}], lastCall=AspectedEvent [aspect=Aspects [group=TestGroup {aspect_1, aspect_3}]]]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( matchAspect2 ) );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspects [group=TestGroup {aspect_1, aspect_3}], lastCall=AspectedEvent [aspect=Aspects [group=TestGroup {aspect_1, aspect_2, aspect_3}]]]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
    }

}
