package com.inari.commons.event;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.inari.commons.lang.aspect.AspectBitSet;
import com.inari.commons.lang.aspect.AspectSetBuilder;
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
        
        eventDispatcher.register( SimpleTestEvent1.class, listener11 );
        eventDispatcher.register( SimpleTestEvent1.class, listener12 );
        eventDispatcher.register( SimpleTestEvent1.class, listener13 );
        
        eventDispatcher.register( SimpleTestEvent2.class, listener21 );
        eventDispatcher.register( SimpleTestEvent2.class, listener22 );
        
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
        
        eventDispatcher.register( CTestEvent1.class, listener1 );
        eventDispatcher.register( CTestEvent2.class, listener1 );
        eventDispatcher.register( CTestEvent2.class, listener2 );
        
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
        EventDispatcher eventDispatcher = new EventDispatcher();
        
        AspectBitSet filterAspect = AspectSetBuilder.create( 1, 3 );
        AspectBitSet noMatchAspect1 = AspectSetBuilder.create( 1, 2 );
        AspectBitSet noMatchAspect2 = AspectSetBuilder.create( 1, 4 );
        AspectBitSet matchAspect1 = AspectSetBuilder.create( 1, 3 );
        AspectBitSet matchAspect2 = AspectSetBuilder.create( 1, 2, 3 );
        
        AspectedTestEventListener listener = new AspectedTestEventListener( filterAspect );
        
        eventDispatcher.register( TestAspectedEvent.class, listener );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspect [ size=64 bitset={1, 3} ], lastCall=null]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( noMatchAspect1 ) );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspect [ size=64 bitset={1, 3} ], lastCall=null]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]",
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( noMatchAspect2 ) );
        
        assertEquals( 
             "EventDispatcher [listeners=DynArray [list=[" +
             "[AspectedTestEventListener [aspect=Aspect [ size=64 bitset={1, 3} ], lastCall=null]], null, null, null, null, null, null, null, null, null" +
             "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( matchAspect1 ) );
        
        assertEquals(
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspect [ size=64 bitset={1, 3} ], lastCall=AspectedEvent [aspect=Aspect [ size=64 bitset={1, 3} ]]]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
        
        eventDispatcher.notify( new TestAspectedEvent( matchAspect2 ) );
        
        assertEquals( 
            "EventDispatcher [listeners=DynArray [list=[" +
            "[AspectedTestEventListener [aspect=Aspect [ size=64 bitset={1, 3} ], lastCall=AspectedEvent [aspect=Aspect [ size=64 bitset={1, 2, 3} ]]]], null, null, null, null, null, null, null, null, null" +
            "], size()=1, capacity()=10]]", 
            eventDispatcher.toString() 
        );
    }

}
