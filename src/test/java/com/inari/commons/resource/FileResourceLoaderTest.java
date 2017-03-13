package com.inari.commons.resource;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FileResourceLoaderTest {

    @Test
    public void testCreation() {
        FileResourceLoader loader = new FileResourceLoader();
        assertEquals( "file:\\", loader.toString() );
        assertEquals( "\\", loader.toConfigString() );
        assertEquals( "\\", loader.rootPath() );
        assertFalse( loader.exists( null ) );
        assertFalse( loader.exists( new Resource() ) );
        assertFalse( loader.exists( new Resource( "notExisting", loader ) ) );


    }
}
