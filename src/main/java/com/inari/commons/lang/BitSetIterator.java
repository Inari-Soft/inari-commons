package com.inari.commons.lang;

import java.util.BitSet;

public final class BitSetIterator implements IntIterator {
    
    private final BitSet bitset;
    private int next = -1;

    public BitSetIterator( BitSet bitset ) {
        this.bitset = bitset;
        getNext();
    }

    public final boolean hasNext() {
        return next >= 0;
    }

    public final int next() {
        int result = next;
        getNext();
        return result;
    }
    
    private void getNext() {
        next = bitset.nextSetBit( next + 1 );
    }

}
