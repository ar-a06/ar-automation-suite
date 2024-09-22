package de.douglas.automation.tests;

import com.aventstack.extentreports.ExtentReports;
import de.douglas.automation.utils.ConfigReader;
import de.douglas.automation.utils.CookiesConsentHandler;
import de.douglas.automation.utils.ExtentReportUtils;
import de.douglas.automation.utils.WebDriverManagerUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    String url = ConfigReader.getProperty("test.url");
    CookiesConsentHandler cookiesConsentHandler;

    @BeforeMethod
    public void setUp() {
        extent = ExtentReportUtils.createReport();
        driver = WebDriverManagerUtils.getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        cookiesConsentHandler.acceptCookies(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            WebDriverManagerUtils.quitDriver();
        }
        ExtentReportUtils.flushReport();
    }

}

