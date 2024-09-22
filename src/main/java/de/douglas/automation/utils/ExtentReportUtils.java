package de.douglas.automation.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportUtils {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentSparkReporter sparkReporter;
    private static String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    private static String reportPath = System.getProperty("user.dir") + "/reports/extent-reports/extent-report-" + timestamp + ".html";

    public static ExtentReports createReport() {
        if (extent == null) {
            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Report");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("By", "ARA");
        }
        return extent;
    }

    public static void setTest(ExtentTest testInstance) {
        test = testInstance;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void asserts(boolean condition, String message) {
        ExtentTest currentTest = getTest();
        if (condition) {
            currentTest.pass(message + " - Passed");
        } else {
            currentTest.fail(message + " - Failed");
        }
    }

    public static void log(String message) {
        test.log(Status.INFO, message);
    }

    public static void flushReport() {
        extent.flush();
    }
}
