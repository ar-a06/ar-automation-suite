package de.douglas.automation.utils;

import de.douglas.automation.pages.ParfumPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;


public class FilterUtils {

    private WebDriver driver;
    private WebDriverWait wait;
    private ParfumPage parfumPage;

    public FilterUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.parfumPage = new ParfumPage(driver);
    }

    public boolean handleRandomSelection(ParfumPage parfumPage, String filterName, String value) {
        ExtentReportUtils.log("Checking filter: " + filterName + " with value: " + value);

        try {
            parfumPage.handleOverlays();
            parfumPage.getWait().until(ExpectedConditions.visibilityOf(parfumPage.dropDownForFilters(filterName)));
            parfumPage.getWait().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(parfumPage.dropDownForFilters(filterName))));

            List<WebElement> checkboxes = parfumPage.checkboxesCountForTheFilter(filterName);
            if (checkboxes.size() > 0) {
                int strategy = new Random().nextInt(3); // 0 = BVA, 1 = EP, 2 = Random

                switch (strategy) {
                    case 0:
                        ExtentReportUtils.log("Using BVA strategy");
                        performBVASelection(checkboxes);
                        break;
                    case 1:
                        ExtentReportUtils.log("Using EP strategy");
                        performEPSelection(checkboxes);
                        break;
                    case 2:
                        ExtentReportUtils.log("Using Random strategy");
                        performRandomSelection(checkboxes);
                        break;
                }
                return true;
            }
            return true;
        } catch (NoSuchElementException e) {
            ExtentReportUtils.log("NoSuchElementException: Unable to locate the checkboxes for " + filterName);
            return false;
        } catch (StaleElementReferenceException e) {
            return true;
        } catch (Exception ignored) {return true;}
    }

    // BVA: Select first and last checkboxes
    public void performBVASelection(List<WebElement> checkboxes) {
        if (checkboxes.size() == 1) {
            clickAndWait(checkboxes.get(0),1);
        }else if (checkboxes.size() >= 2) {
            WebElement firstCheckbox = checkboxes.get(0);
            WebElement lastCheckbox = checkboxes.get(checkboxes.size() - 1);
            WebElement[] checkbox = {firstCheckbox,lastCheckbox};
            Random random = new Random();
            WebElement selectedCheckbox=checkbox[random.nextInt(checkbox.length)];
            int index = selectedCheckbox.equals(firstCheckbox) ? 0 : (checkboxes.size() - 1);
            clickAndWait(selectedCheckbox, index + 1);

        }
    }

    // EP: Select middle checkbox
    public void performEPSelection(List<WebElement> checkboxes) {
        if (checkboxes.size() == 1) {
            clickAndWait(checkboxes.get(0),1);
        }else if (checkboxes.size() >= 2) {
            int middleIndex = checkboxes.size() / 2;
            WebElement middleCheckbox = checkboxes.get(middleIndex);
            clickAndWait(middleCheckbox,middleIndex+1);
        }
    }

    // Random selection
    public void performRandomSelection(List<WebElement> checkboxes) {
        if (checkboxes.size() == 1) {
            clickAndWait(checkboxes.get(0),1);
        }else{
            int checkboxindex = new Random().nextInt(checkboxes.size());
            WebElement checkbox = checkboxes.get(checkboxindex);
            clickAndWait(checkbox,checkboxindex+1);
        }
    }

    private void clickAndWait(WebElement checkbox, int index) {
        if (!checkbox.isSelected()) {
            ExtentReportUtils.log("Checkbox for the filter selected: "+parfumPage.getCheckboxText(index));
            checkbox.click();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(checkbox)));
            wait.until(ExpectedConditions.stalenessOf(checkbox));
        }
    }

}
