package com.inari.commons.geom;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class EasingImplTest {
    
    @Test
    public void testLinearPositivAndNegativ() {
        float startValue = 0f;
        float changeInValue = 5f;
        long duration = 10;
        
        Collection<Float> values = new ArrayList<Float>();
        for ( long time = 0; time < 10; time++ ) {
            values.add( Easing.Type.LINEAR.calc( time, startValue, changeInValue, duration ) );
        }
        
        assertEquals( 
            "[0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5]", 
            values.toString() 
        );
        
        startValue = 5f;
        changeInValue = 0f;
        values.clear();
        
        boolean inverse = changeInValue - startValue < 0;
        for ( long time = 0; time < 10; time++ ) {
            if ( inverse ) {
                values.add( startValue - Easing.Type.LINEAR.calc( time, changeInValue, startValue, duration ) );
            }
        }
        
        assertEquals( 
            "[5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5]", 
            values.toString() 
        );
    }

}
