/*******************************************************************************
 * Copyright (c) 2015 - 2016 - 2016, Andreas Hefti, inarisoft@yahoo.de 
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

/** Implements Easing types within static classes implementing the Easing interface */
public abstract class EasingImpls {
    
    static final class CircularIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            return -c * (float) ( Math.sqrt( 1 - t * t ) - 1 ) + b;
        }
    }
    
    static final class CircularInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d/2;
            if (t < 1) return -c/2 * (float) ( Math.sqrt( 1 - t*t ) - 1 ) + b;
            t -= 2;
            return c/2 * (float) ( Math.sqrt( 1 - t*t ) + 1 ) + b;
        }
    }
    
    static final class CircularOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            t--;
            return c * (float)Math.sqrt(1 - t*t) + b;
        }
    }
    
    static final class CubicIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            return c*t*t*t + b;
        }
    }
    
    static final class CubicInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d/2;
            if (t < 1) return c/2*t*t*t + b;
            t -= 2;
            return c/2*(t*t*t + 2) + b;
        }
    }
    
    static final class CubicOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            t--;
            return c*(t*t*t + 1) + b;
        }
    }
    
    static final class ExponentialIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            return c * (float) Math.pow( 2.0f, 10.0f * ( (float) t/d - 1.0f ) ) + b;
        }
    }
    
    static final class ExponentialInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d/2;
            if (t < 1) 
                return c/2 * (float)Math.pow( 2, 10 * ( t - 1 ) ) + b;
            t--;
            return c/2 * (float)( -Math.pow( 2, -10 * t ) + 2 ) + b;
        }
    }
    
    static final class ExponentialOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            return c * (float)( -Math.pow( 2, -10 * t/d ) + 1 ) + b;
        }
    }
    
    static final class Linear implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            return c * t / d + b;
        }
    }
    
    static final class QuadraticIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            return c * t * t + b;
        }
    }
    
    static final class QuarticIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            return c * t * t * t * t + b;
        }
    }
    
    static final class QuadraticInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d/2;
            if ( t < 1 ) 
                return c / 2 * t * t + b;
            t--;
            return -c / 2 * ( t * ( t - 2 ) - 1 ) + b;
        }
    }
    
    static final class QuadraticOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            return -c * t * ( t - 2 ) + b;
        }
    }
    
    static final class QuarticInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d/2;
            if (t < 1) 
                return c/2*t*t*t*t + b;
            t -= 2;
            return -c/2 * (t*t*t*t - 2) + b;
        }
    }
    
    static final class QuinticIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            return c*t*t*t*t*t + b;
        }
    }
    
    static final class QuinticInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d/2;
            if (t < 1) return c/2*t*t*t*t*t + b;
            t -= 2;
            return c/2*(t*t*t*t*t + 2) + b;
        }

    }
    
    static final class QuinticOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            t /= d;
            t--;
            return c*(t*t*t*t*t + 1) + b;
        }
    }
    
    static final class SinusoidalIn implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            return -c * (float)Math.cos(t/d * (Math.PI/2)) + c + b;
        }

    }
    
    static final class SinusoidalInOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            return -c/2 * (float)(Math.cos(Math.PI*t/d) - 1) + b;
        }
    }
    
    static final class SinusoidalOut implements Easing {

        @Override
        public final float calc( long t, float b, float c, long d ) {
            return c * (float)Math.sin(t/d * (Math.PI/2)) + b;
        }
    }

}
