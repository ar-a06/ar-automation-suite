package de.douglas.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import de.douglas.automation.pages.HomePage;
import de.douglas.automation.utils.ExtentReportUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class HomePageTest extends BaseTest {

    private HomePage homePage;
    SoftAssert Assert = new SoftAssert();

    @BeforeMethod
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }

    @Test
    public void testVisibilityOfAllElements() {
        ExtentTest test = ExtentReportUtils.createReport().createTest("testVisibilityOfAllElements");
        ExtentReportUtils.setTest(test);

        ExtentReportUtils.asserts(homePage.areParfumElementsVisible(), "Visibility of Parfum Menu on Home Page");
        Assert.assertAll();
    }

    @Test
    public void testNavigateToParfumPage() {
        ExtentTest test = ExtentReportUtils.createReport().createTest("testNavigateToParfumPage");
        ExtentReportUtils.setTest(test);
        homePage.clickOnParfumPage();

        ExtentReportUtils.asserts(homePage.verifyOnParfumPage(), "Navigation to Parfume Page from Home Page");
        Assert.assertAll();
    }
}

