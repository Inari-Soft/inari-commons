package com.inari.commons;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class JavaUtils {
    
    /** Use this to create an unmodifiable Set from the given values in the var-arg.
     *  NOTE: It is the Users concern to care about save varargs on call.
     * @param values
     * @return
     */
    @SafeVarargs
    public static final <T> Set<T> unmodifiableSet( T... values ) {
        Set<T> set = new HashSet<T>();
        set.addAll( Arrays.asList( values ) );
        return Collections.unmodifiableSet( set );
    }

    /** Use this to create an unmodifiable Set from the given Collections in the var-arg.
     *  NOTE: It is the Users concern to care about save varargs on call.
     * @param values
     * @return
     */
    @SafeVarargs
    public static final <T> Set<T> unmodifiableSet( Collection<T>... values ) {
        Set<T> set = new HashSet<T>();
        if ( values != null ) {
            for ( Collection<T> vals : values ) {
                set.addAll( vals );
            }
        }
        return Collections.unmodifiableSet( set );
    }


}
