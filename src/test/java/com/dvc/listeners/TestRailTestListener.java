package com.dvc.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.dvc.base.BaseSetup;
import com.dvc.utils.ScreenshotHelper;
import com.qa.testrailmanager.TestRailManager;

public class TestRailTestListener implements ITestListener {
	@Override
    public void onTestSuccess(ITestResult result) {

        BaseSetup base = (BaseSetup) result.getInstance();

        TestRailManager.addResultsForTestCase(
                base.testCaseId,
                TestRailManager.TEST_CASE_PASS_STATUS,
                "Test Passed",
                null
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        BaseSetup base = (BaseSetup) result.getInstance();

        String testName = result.getMethod().getMethodName();

        String screenshotPath = ScreenshotHelper.capture(
                base.getRawDriver(),
                testName
        );

        TestRailManager.addResultsForTestCase(
                base.testCaseId,
                TestRailManager.TEST_CASE_FAIL_STATUS,
                "Test Failed: " + result.getThrowable(),
                screenshotPath
        );
    }

//    @Override
//    public void onTestSkipped(ITestResult result) {
//
//        BaseSetup base = (BaseSetup) result.getInstance();
//
//        TestRailManager.addResult(
//                base.testCaseId,
//                TestRailManager.TEST_SKIPPED,
//                "Test Skipped",
//                null
//        );
//    }
}
