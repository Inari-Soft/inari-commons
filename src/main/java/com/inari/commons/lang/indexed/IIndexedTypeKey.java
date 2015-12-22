package com.inari.commons.lang.indexed;

public interface IIndexedTypeKey {
    
    <T> Class<T> type();
    
    int typeIndex();

}
