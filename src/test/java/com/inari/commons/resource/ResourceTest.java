package com.inari.commons.resource;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ResourceTest {

    @Test
    public void test() {
        Resource res = new Resource();

        assertEquals( "Resource [name=null, path()=]", res.toString() );

        ClassPathResourceLoader loader = new ClassPathResourceLoader( "com.inari.commons" );
        res = new Resource( "test", loader );
        assertEquals( "Resource [name=test, path()=classpath:/com/inari/commons/test]", res.toString() );
        assertEquals( "classpath:/com/inari/commons/test", res.path() );
        assertFalse( res.exists() );

        loader = new ClassPathResourceLoader( this.getClass() );
        res = new Resource( "ResourceTest.class", loader );
        assertTrue( res.exists() );

        res.configId( "test" );
        assertEquals( "test", res.configId() );
        assertNotNull( res.url() );
        assertNotNull( res.inputStream() );

    }
}
