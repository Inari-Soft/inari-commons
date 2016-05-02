package com.inari.commons.lang.functional;

public interface ArrayFunction<IN, OUT> {
    
    public OUT f( @SuppressWarnings( "unchecked" ) IN... values );

}
