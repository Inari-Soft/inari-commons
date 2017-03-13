package com.inari.commons.lang;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ImmutablePairTest {

    @Test
    public void test() {
        ImmutablePair<String, String> pair1 = new ImmutablePair( "one", "two" );
        ImmutablePair<String, String> pair2 = new ImmutablePair( "one", "tree" );
        ImmutablePair<String, String> pair3 = new ImmutablePair( "one", "two" );

        assertEquals( "3531879", String.valueOf( pair1.hashCode() ) );
        assertEquals( "6985145", String.valueOf( pair2.hashCode() ) );
        assertEquals( "3531879", String.valueOf( pair3.hashCode() ) );
        assertEquals( pair1, pair3 );
        assertFalse( pair1.equals( pair2 ) );
        assertFalse( pair1.equals( null ) );
        assertFalse( pair1.equals( "gsgs" ) );
    }
}
