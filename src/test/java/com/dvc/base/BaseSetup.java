package com.dvc.base;

import com.epam.healenium.SelfHealingDriver;
import com.qa.testrailmanager.TestRailManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseSetup {
    protected SelfHealingDriver driver;
    public String testCaseId;
    protected StringBuilder testRailLog;

    public SelfHealingDriver getDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        WebDriver delegate = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver = SelfHealingDriver.create(delegate);
        return driver;
    }
    
    @BeforeSuite
    public void setup() {
        TestRailManager.createTestRun();
    }
    
    public WebDriver getRawDriver() {
        return driver.getDelegate();
    }
    
    @AfterMethod
    public void addResultsToTestRail(ITestResult result) {
//        if(result.getStatus() == ITestResult.SUCCESS) {
//            TestRailManager.addResultsForTestCase(testCaseId, TestRailManager.TEST_CASE_PASS_STATUS, 
//            		testRailLog.toString());
//        } else if(result.getStatus() == ITestResult.FAILURE) {
//            TestRailManager.addResultsForTestCase(testCaseId, TestRailManager.TEST_CASE_FAIL_STATUS,
//            		testRailLog.toString());
//        }
//        
        if (driver != null) {
            driver.quit();
        }
    }
}