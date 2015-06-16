package com.inari.commons;


import org.junit.Assert;
import org.junit.Test;


public class StringUtilsTest {
    
    @Test
    public void isEmptyTest() throws Exception {
        Assert.assertTrue( StringUtils.isEmpty( null ) );
        Assert.assertTrue( StringUtils.isEmpty( "" ) );
        Assert.assertFalse( StringUtils.isEmpty( " " ) );
        Assert.assertFalse( StringUtils.isEmpty( "d" ) );
    }
    
    @Test
    public void isBlankTest() throws Exception {
        Assert.assertTrue( StringUtils.isBlank( null ) );
        Assert.assertTrue( StringUtils.isBlank( "" ) );
        Assert.assertTrue( StringUtils.isBlank( " " ) );
        Assert.assertFalse( StringUtils.isBlank( "d" ) );
    }
}
