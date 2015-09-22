/*******************************************************************************
 * Copyright (c) 2015, Andreas Hefti, inarisoft@yahoo.de 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.inari.commons.geom;

/** Interface to calculate a specified type of Easing */ 
public interface Easing {
    
    /** Defines a Easing repository with different types of Easing implementations */
    public static enum Type {
        LINEAR( new EasingImpls.Linear() ),
        QUAD_IN( new EasingImpls.QuadraticIn() ),
        QUAD_OUT( new EasingImpls.QuadraticOut() ),
        QUAD_IN_OUT( new EasingImpls.QuadraticInOut() ),
        CUBIC_IN( new EasingImpls.CubicIn() ),
        CUBIC_OUT( new EasingImpls.CubicInOut() ),
        CUBIC_IN_OUT( new EasingImpls.CubicInOut() ),
        QRT_IN( new EasingImpls.QuarticIn() ),
        QRT_OUT( new EasingImpls.QuarticInOut() ),
        QRT_IN_OUT( new EasingImpls.QuarticInOut() ),
        QNT_IN( new EasingImpls.QuinticIn() ),
        QNT_OUT( new EasingImpls.QuinticOut() ),
        QNT_IN_OUT( new EasingImpls.QuinticInOut() ),
        EXPO_IN( new EasingImpls.ExponentialIn() ),
        EXPO_OUT( new EasingImpls.ExponentialOut() ),
        EXPO_IN_OUT( new EasingImpls.ExponentialInOut() ),
        SIN_IN( new EasingImpls.SinusoidalIn() ),
        SIN_OUT( new EasingImpls.SinusoidalOut() ),
        SIN_IN_OUT( new EasingImpls.SinusoidalInOut() ),
        CIRC_IN( new EasingImpls.CircularIn() ),
        CIRC_OUT( new EasingImpls.CircularOut() ),
        CIRC_IN_OUT( new EasingImpls.CircularInOut() )
        ;
        
        public final Easing easing;

        private Type( Easing easing ) {
            this.easing = easing;
        }
        
        public final float calc( long time, float startValue, float changeInValue, long duration ) {
            return easing.calc( time, startValue, changeInValue, duration ); 
        }
    }
    
    /** Calculate easing for current time (t) for specified start value (b) and change in value (c) for a given duration of the Easing (d)
     * @param t current time
     * @param b start value
     * @param c change in value
     * @param d duration
     * @return the value for current time
     */
    public float calc( long t, float b, float c, long d );

}
