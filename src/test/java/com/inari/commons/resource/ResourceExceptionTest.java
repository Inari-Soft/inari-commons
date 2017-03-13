package com.inari.commons.resource;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResourceExceptionTest {

    @Test
    public void test() {
        ResourceException exception = new ResourceException( "test" );
        assertEquals( "test", exception.getMessage() );
        assertNull( exception.resource() );

        exception = new ResourceException( new RuntimeException(  "test" ) );
        assertEquals( "java.lang.RuntimeException: test", exception.getMessage() );
        assertNull( exception.resource() );

        exception = new ResourceException( "test", new RuntimeException(  "test" ) );
        assertEquals( "test", exception.getMessage() );
        assertNull( exception.resource() );

        exception = new ResourceException( "test", new RuntimeException(  "test" ) );
        assertEquals( "test", exception.getMessage() );
        assertNull( exception.resource() );

        Resource res = new Resource( "ClassPathResourceLoaderTest.class", new ClassPathResourceLoader() );
        exception = new ResourceException( res, "test" );
        assertEquals( "test", exception.getMessage() );
        assertNotNull( exception.resource() );

        exception = new ResourceException( res, "test", new RuntimeException(  "test" ) );
        assertEquals( "test", exception.getMessage() );
        assertNotNull( exception.resource() );

        exception = new ResourceException( res, new RuntimeException(  "test" ) );
        assertEquals( "java.lang.RuntimeException: test", exception.getMessage() );
        assertNotNull( exception.resource() );
    }
}
