package com.ullink.method.selector.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ullink.medhod.selector.manager.MethodFilterManager;
import com.ullink.medhod.selector.manager.file.CSVFilterManagerBuilder;

public class TestDefaultMethodFilterManager
{
    private MethodFilterManager methodFilterManager;

    @Before
    public void setUp() throws FileNotFoundException, IOException
    {
        URL csvURl = TestDefaultMethodFilterManager.class.getResource("resources/rules.csv");
        this.methodFilterManager = CSVFilterManagerBuilder.fromFileName(csvURl.getFile()).build();
    }

    @Test
    public void accceptPackages()
    {
        Assert.assertTrue(this.methodFilterManager.isPackageAllowed("com.ullink.performance-trends"));
        Assert.assertTrue(this.methodFilterManager.isPackageAllowed("com.ullink.performance-trends.visible"));
        Assert.assertFalse(this.methodFilterManager.isPackageAllowed("com.ullink.performance-trends.hidden"));
    }

    @Test
    public void accceptSubPackages()
    {
        Assert.assertTrue(this.methodFilterManager.isPackageAllowed("com.ullink.performance-trends.visible.random"));
        Assert.assertFalse(this.methodFilterManager.isPackageAllowed("com.ullink.performance-trends.hidden.random"));
    }
    
    @Test
    public void classesWithHiddenPackage_areHidden()
    {
        Assert.assertFalse(this.methodFilterManager.isClasssAllowed("com.ullink.performance-trends.hidden", "RandomClass1"));
        Assert.assertFalse(this.methodFilterManager.isClasssAllowed("com.ullink.performance-trends.hidden", "RandomClass2"));
    }
    
    @Test
    public void classesWithVisiblePackageAndNotVisible_areHidden()
    {
        Assert.assertTrue(this.methodFilterManager.isClasssAllowed("com.ullink.performance-trends.visible", "RandomClass"));
        Assert.assertFalse(this.methodFilterManager.isClasssAllowed("com.ullink.performance-trends.hidden", "HiddenClass"));
    }

    public void acceptClasses()
    {

    }
}