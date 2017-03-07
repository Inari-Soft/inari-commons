package com.inari.commons.resource;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.net.URL;
import org.junit.Test;

public class ClassPathResourceLoaderTest {

    @Test
    public void testCreation() {
        ClassPathResourceLoader loader = new ClassPathResourceLoader();
        assertEquals( "classpath:/", loader.toString() );
        assertEquals( "", loader.toConfigString() );
        assertEquals( "", loader.rootPath() );
        assertFalse( loader.exists( null ) );
        assertFalse( loader.exists( new Resource() ) );
        assertFalse( loader.exists( new Resource( "notExisting", loader ) ) );

        loader = new ClassPathResourceLoader( "com.inari.commons" );
        assertEquals( "classpath:/com/inari/commons/", loader.toString() );
        assertEquals( "com/inari/commons/", loader.toConfigString() );
        assertEquals( "com/inari/commons/", loader.rootPath() );

        loader = new ClassPathResourceLoader( this.getClass() );
        Resource res = new Resource( "ClassPathResourceLoaderTest.class", loader );
        assertEquals( "classpath:/com/inari/commons/resource/", loader.toString() );
        assertEquals( "com/inari/commons/resource/", loader.toConfigString() );
        assertEquals( "com/inari/commons/resource/", loader.rootPath() );
        assertTrue( loader.exists( res ) );
        assertNotNull( loader.url( res ) );
        assertNotNull( loader.inputStream( res ) );

        loader = new ClassPathResourceLoader( this.getClass().getPackage() );
        assertEquals( "classpath:/com/inari/commons/resource/", loader.toString() );
        assertEquals( "com/inari/commons/resource/", loader.toConfigString() );
        assertEquals( "com/inari/commons/resource/", loader.rootPath() );
    }

}
