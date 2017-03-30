package com.inari.commons;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class JavaUtils {
    
    @SafeVarargs
    public static final <T> Set<T> imutableSet( T... values ) {
        Set<T> set = new HashSet<T>();
        set.addAll( Arrays.asList( values ) );
        return Collections.unmodifiableSet( set );
    }

}
