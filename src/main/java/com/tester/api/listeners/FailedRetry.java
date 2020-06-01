package com.tester.api.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class FailedRetry implements IRetryAnalyzer {

    private int count = 1;
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (count <= maxRetryCount) {
            result.setAttribute("RETRY", new Integer(count));
            count++;
            return true;
        }
        return false;
    }
}
