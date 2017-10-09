package com.inari.commons.lang.list;

import com.inari.commons.lang.IntIterator;

public interface IntBagRO {
    
    int getNullValue();
    int getExpand();
    int size();
    int length();
    boolean isEmpty();
    boolean isEmpty( int index );
    boolean contains( int value );
    int get( int index );
    int indexOf( int value );
    IntIterator iterator();
    

}
