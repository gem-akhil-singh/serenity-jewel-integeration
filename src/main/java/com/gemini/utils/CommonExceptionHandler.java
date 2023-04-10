package com.gemini.utils;

import com.gemini.reporting.GemTestReporter;
import com.gemini.reporting.STATUS;

public class CommonExceptionHandler {

    public void setThreadName(String name) {
        Thread.currentThread().setName(name);
    }

    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return (thread, throwable) -> {
            String testcaseName = thread.getName();
            StackTraceElement stackTraceElement = throwable.getStackTrace()[0];
            String errorMessage = throwable.getMessage();
            GemTestReporter.addTestStep("Error Occured : " + errorMessage, stackTraceElement.toString(), STATUS.FAIL);
        };
    }


}
