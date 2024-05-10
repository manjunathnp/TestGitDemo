package com.tests;

import com.base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

public class LaunchTest extends TestBase {

    @Test
    public void launchApp(){
        driver.get(properties.getProperty("appURL"));
        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), properties.getProperty("homePageTitle"));
    }
}
