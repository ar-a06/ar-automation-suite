package de.douglas.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import de.douglas.automation.pages.HomePage;
import de.douglas.automation.pages.ParfumPage;
import de.douglas.automation.utils.ExtentReportUtils;
import de.douglas.automation.utils.FilterUtils;
import de.douglas.automation.utils.TestDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class ParfumPageTest extends BaseTest {
    private ParfumPage parfumPage;
    private HomePage homePage;
    private FilterUtils filterUtils;
    SoftAssert Assert = new SoftAssert();

    @BeforeMethod
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
        parfumPage = new ParfumPage(driver);
        filterUtils = new FilterUtils(driver);
    }

    @DataProvider(name = "filterData")
    public Object[][] getFilterData() throws IOException {
        return TestDataProvider.getFilterData();
    }


    @Test(dataProvider = "filterData")
    public void testFiltersWithDataProviderOnePossibleCombination(String criteria, String marke, String produktart, String geschenkFur, String furWen) {
        ExtentTest test = ExtentReportUtils.createReport().createTest("testFiltersWithDataProviderOnePossibleCombination");
        ExtentReportUtils.setTest(test);

        Map<String, String> expectedUrlSegments = null;
        String currentUrl;
        try {
            expectedUrlSegments = TestDataProvider.getExpectedUrlSegments();
        } catch (IOException e) {
            ExtentReportUtils.log("Failed to read filter criteria from Excel.");
        }

        String[] headers = new String[0];
        try {
            headers = TestDataProvider.getCriteria();
        } catch (IOException e) {
            ExtentReportUtils.log("Failed to read filter criteria from Excel.");
        }
        String[] filterValues = {marke, produktart, geschenkFur, furWen};

        homePage.clickOnParfumPage();
        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getFilterSectionLocator()));

        ExtentReportUtils.asserts(parfumPage.parfumPageAndFilterPresent(), "Visibility of Parfum page and filter section");

        parfumPage.expandDropDownForFilters(parfumPage.dropDownForFilters(headers[0]));
        By highlightLocator = parfumPage.locatorForFilterCheckboxesByText(criteria);
        int[] highlightCounts = parfumPage.applyFilter(highlightLocator);

        currentUrl = driver.getCurrentUrl();
        ExtentReportUtils.asserts(currentUrl.contains(expectedUrlSegments.get(criteria)), "Expected segment in the URL for filter: " + criteria);

        ExtentReportUtils.asserts(highlightCounts[1] > highlightCounts[0], "Applied filters count incrementation after applying Highlights filter:" + criteria);
        ExtentReportUtils.asserts(highlightCounts[3] > highlightCounts[2], "Check icons count incrementation after applying Highlights filter:" + criteria);

        for (int i = 1; i < headers.length; i++) {
            if (filterValues[i - 1].equals("?")) {
                try {
                    int initialAppliedFilterCount = driver.findElements(parfumPage.getAllAppliedFilters()).size();
                    int initialCheckIconCount = driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size();
                    boolean isCheckBoxClicked = filterUtils.handleRandomSelection(parfumPage, headers[i], filterValues[i - 1]);
                    parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getFilterSectionLocator()));
                    if (isCheckBoxClicked) {

                        currentUrl = driver.getCurrentUrl();
                        ExtentReportUtils.asserts(currentUrl.contains(expectedUrlSegments.get(headers[0])), "Expected segment in the URL for filter: " + headers[i]);

                        int newAppliedFilterCount = driver.findElements(parfumPage.getAllAppliedFilters()).size();
                        int newCheckIconCount = driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size();

                        ExtentReportUtils.asserts(newAppliedFilterCount > initialAppliedFilterCount, "Applied filters count incrementation after applying filter: " + headers[i]);
                        ExtentReportUtils.asserts(newCheckIconCount > initialCheckIconCount, "Check icons count incrementation after applying filter: " + headers[i]);

                    }
                } catch (NoSuchElementException e) {
                    ExtentReportUtils.log(headers[i - 1] + " filter not found.");
                }
                int initialAppliedFilterCount = driver.findElements(parfumPage.getAllAppliedFilters()).size();
                int initialCheckIconCount = driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size();

                parfumPage.uncheckCheckedCheckbox(headers[i]);
                parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getFilterSectionLocator()));

                int newAppliedFilterCount = driver.findElements(parfumPage.getAllAppliedFilters()).size();
                int newCheckIconCount = driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size();

                ExtentReportUtils.asserts(newAppliedFilterCount < initialAppliedFilterCount, "Applied filters count decrementation after removing filter: " + headers[i]);
                ExtentReportUtils.asserts(newCheckIconCount < initialCheckIconCount, "Check icons count decrementation after removing filter: " + headers[i]);

            } else {
                ExtentReportUtils.log("'-' Means " +filterValues[i-1]+"criteria is not applicable for: "+headers[i]);
            }
        }
        parfumPage.clearFilters();

        parfumPage.getWait().until(ExpectedConditions.invisibilityOfElementLocated(parfumPage.getClearAllFilters()));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getClearAllFilters()).size() == 0, "Invisibility of 'Clear All Filters' button after clearing filters.");
        parfumPage.getWait().until(ExpectedConditions.numberOfElementsToBeLessThan(parfumPage.getAllAppliedFilters(), 1));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getAllAppliedFilters()).isEmpty(), "Invisibility of Filters after clearing filters.");

        Assert.assertAll();
    }


    @Test(dataProvider = "filterData")
    public void testFiltersWithDataProviderAllPossibleCombination(String criteria, String marke, String produktart, String geschenkFur, String furWen) {
        ExtentTest test = ExtentReportUtils.createReport().createTest("testFiltersWithDataProviderAllPossibleCombination");
        ExtentReportUtils.setTest(test);

        Map<String, String> expectedUrlSegments = null;
        String currentUrl;
        try {
            expectedUrlSegments = TestDataProvider.getExpectedUrlSegments();
        } catch (IOException e) {
            ExtentReportUtils.log("Failed to read filter criteria from Excel.");
        }

        String[] headers = new String[0];
        try {
            headers = TestDataProvider.getCriteria();
        } catch (IOException e) {
            ExtentReportUtils.log("Failed to read filter criteria from Excel.");
        }
        String[] filterValues = {marke, produktart, geschenkFur, furWen};

        homePage.clickOnParfumPage();
        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getFilterSectionLocator()));

        ExtentReportUtils.asserts(parfumPage.parfumPageAndFilterPresent(), "Visibility of Parfum page and filter section");

        parfumPage.expandDropDownForFilters(parfumPage.dropDownForFilters(headers[0]));
        By highlightLocator = parfumPage.locatorForFilterCheckboxesByText(criteria);
        int[] highlightCounts = parfumPage.applyFilter(highlightLocator);

        currentUrl = driver.getCurrentUrl();
        ExtentReportUtils.asserts(currentUrl.contains(expectedUrlSegments.get(headers[0])), "Expected segment in the URL for filter: " + criteria);


        ExtentReportUtils.asserts(highlightCounts[1] > highlightCounts[0], "Applied filters count incrementation after applying Highlights filter:"+criteria);
        ExtentReportUtils.asserts(highlightCounts[3] > highlightCounts[2], "Check icons count incrementation after applying Highlights filter:"+criteria);

        for (int i = 1; i < headers.length; i++) {
            if (filterValues[i - 1].equals("?")) {
                try {
                    int initialAppliedFilterCount = driver.findElements(parfumPage.getAllAppliedFilters()).size();
                    int initialCheckIconCount = driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size();

                    boolean isCheckBoxClicked=filterUtils.handleRandomSelection(parfumPage, headers[i], filterValues[i-1]);
                    if(isCheckBoxClicked){

                        currentUrl = driver.getCurrentUrl();
                        ExtentReportUtils.asserts(currentUrl.contains(expectedUrlSegments.get(headers[i])), "Expected segment in the URL for filter: " + headers[i]);

                        int newAppliedFilterCount = driver.findElements(parfumPage.getAllAppliedFilters()).size();
                        int newCheckIconCount = driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size();

                        ExtentReportUtils.asserts(newAppliedFilterCount > initialAppliedFilterCount, "Applied filters count incrementation after applying filter: "+headers[i]);
                        ExtentReportUtils.asserts(newCheckIconCount > initialCheckIconCount, "Check icons count incrementation after applying filter: "+headers[i]);

                    }else {
                    ExtentReportUtils.log("No matching filter checkbox available to select for: " + headers[i]);
                    }
                } catch (NoSuchElementException e) {
                    ExtentReportUtils.log(headers[i-1] + " filter not found.");
                }
            }
        }

        parfumPage.clearFilters();

        parfumPage.getWait().until(ExpectedConditions.invisibilityOfElementLocated(parfumPage.getClearAllFilters()));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getClearAllFilters()).size() == 0, "Invisibility of 'Clear All Filters' button after clearing filters.");
        parfumPage.getWait().until(ExpectedConditions.numberOfElementsToBeLessThan(parfumPage.getAllAppliedFilters(), 1));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getAllAppliedFilters()).isEmpty(), "Invisibility of Filters after clearing filters.");

        Assert.assertAll();

    }

    //Other Test Cases...

    @DataProvider(name = "filterDataFromHighlight")
    public Object[][] TestDataProvider() throws IOException {
        return TestDataProvider.getFilterDataForHighlight();
    }


    @Test(dataProvider = "filterDataFromHighlight")
    public void testFilterOnHighlights(String highlight) {
        ExtentTest test = ExtentReportUtils.createReport().createTest("filterDataFromHighlight");
        ExtentReportUtils.setTest(test);

        Map<String, String> expectedUrlSegments = null;
        String currentUrl;
        try {
            expectedUrlSegments = TestDataProvider.getExpectedUrlSegments();
        } catch (IOException e) {
            ExtentReportUtils.log("Failed to read filter criteria from Excel.");
        }

        homePage.clickOnParfumPage();
        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getFilterSectionLocator()));
        ExtentReportUtils.asserts(parfumPage.parfumPageAndFilterPresent(), "Visibility of Filter section on Parfum Page");

        parfumPage.expandDropDownForFilters(parfumPage.dropDownForFilters("Highlights"));

        By highlightLocator = parfumPage.locatorForFilterCheckboxesByText(highlight);
        WebElement highlightElement = parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(highlightLocator));
        highlightElement.click();

        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getCheckIconsWhenFilterApplied()));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size() > 0, "Visibility of Check icon after applying filter for: " + highlight);

        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getClearAllFilters()));
        ExtentReportUtils.asserts(driver.findElement(parfumPage.getClearAllFilters()).isDisplayed(), "Visibility of 'Clear All Filters' button after applying filter: " + highlight);

        parfumPage.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(parfumPage.getAllAppliedFilters(), 0));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getAllAppliedFilters()).size() > 0, "Visibility of applied filters after applying filter: " + highlight);

        currentUrl = driver.getCurrentUrl();
        ExtentReportUtils.asserts(currentUrl.contains(expectedUrlSegments.get(highlight)), "Expected segment in the URL for filter: " + highlight);

        parfumPage.clearFilters();
        Assert.assertAll();
    }


    @Test
    public void testFilterReset() throws IOException {
        ExtentTest test = ExtentReportUtils.createReport().createTest("testFilterReset");
        ExtentReportUtils.setTest(test);

        String[] filters = TestDataProvider.getFilterOptions();
        Random random = new Random();
        String randomFilter = filters[random.nextInt(filters.length)];

        homePage.clickOnParfumPage();

        WebElement filterSection = parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getFilterSectionLocator()));
        parfumPage.getActions().moveToElement(filterSection).perform();

        ExtentReportUtils.asserts(filterSection.isDisplayed(), "Visibility of Filter section on Parfum Page.");

        parfumPage.checkboxesCountForTheFilter("Highlights");
        By highlightLocator = parfumPage.locatorForFilterCheckboxesByText(randomFilter);

        WebElement highlightElement = parfumPage.getWait().until(ExpectedConditions.elementToBeClickable(highlightLocator));
        highlightElement.click();

        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getCheckIconsWhenFilterApplied()));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getCheckIconsWhenFilterApplied()).size() > 0, "Visibility of check icon after selection of Filter: " + randomFilter);

        parfumPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(parfumPage.getClearAllFilters()));
        ExtentReportUtils.asserts(driver.findElement(parfumPage.getClearAllFilters()).isDisplayed(), "Visibility of 'Clear All Filters' button after applying filter: " + randomFilter);

        parfumPage.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(parfumPage.getAllAppliedFilters(), 0));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getAllAppliedFilters()).size() > 0, "Visibility of Applied filters displayed after applying filter: " + randomFilter);

        parfumPage.clearFilters();

        parfumPage.getWait().until(ExpectedConditions.invisibilityOfElementLocated(parfumPage.getClearAllFilters()));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getClearAllFilters()).size() == 0, "Invisibility of 'Clear All Filters' button after clearing filters.");
        parfumPage.getWait().until(ExpectedConditions.numberOfElementsToBeLessThan(parfumPage.getAllAppliedFilters(), 1));
        ExtentReportUtils.asserts(driver.findElements(parfumPage.getAllAppliedFilters()).isEmpty(), "Invisibility of Filters after clearing filters.");

        Assert.assertAll();
    }

}
