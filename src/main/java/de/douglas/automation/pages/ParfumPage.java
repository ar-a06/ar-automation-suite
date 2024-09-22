package de.douglas.automation.pages;

import de.douglas.automation.utils.ExtentReportUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParfumPage{

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By onParfumPage=By.xpath("//span[@class='breadcrumb__entry' and (text()='Perfume' or text()='Parfum')]");
    private By filtersGrid=By.xpath("//div[@class='ui-grid facet-list']");

    private By price=By.xpath("//div[@data-testid='priceValue']");
    private By fragranceNote=By.xpath("//div[@data-testid='Duftnote neu']");
    private By productFeature=By.xpath("//div[@data-testid='Produktauszeichnung']");
    //row
    private By brand=By.xpath("//div[@data-testid='brand']");
    private By productType=By.xpath("//div[@data-testid='classificationClassName']");
    private By giftFor=By.xpath("//div[@data-testid='Geschenk für']");
    private By forWhom=By.xpath("//div[@data-testid='gender']");
    //column
    private By highlights=By.xpath("//div[@data-testid='flags']");
    private By highlightsCheckbox=By.xpath("//span[@data-testid='check-small']/following-sibling::div[@class='facet-option__label']/div[contains(text(), '{value}')]");

    private By checkbox=By.xpath("//input[@type='checkbox']");
    private By applyFilterBtn = By.xpath("//button[@class='button button__primary button__normal facet__close-button']");
    private By sortByOption=By.xpath("//div[@class='sort-facet']");
    private By sortDropDown=By.xpath("//div[@class='dropdown dropdown--no-borders sort-facet__dropdown']//input");
    private By clearAllFilters=By.xpath("//button[@class='selected-facets__reset']");
    private By allAppliedFilters=By.xpath("//div[@class='selected-facets']/button");
    private By checkedCheckboxLocator=By.xpath("//a[@class='link link--text facet-option' and @role='checkbox' and @aria-checked='true']");
    private By checkIconsWhenFilterApplied=By.xpath("//*[name()='svg' and @data-testid='check-icon']");
    private By overlay=By.xpath("//div[@class='survey-modal__header']//button[@type='button']");


    public ParfumPage(WebDriver driver) {
        this.driver = driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(7));
        this.actions = new Actions(driver);
    }

    public boolean parfumPageAndFilterPresent() {
        WebElement parfumPageElement = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(onParfumPage)));
        WebElement filtersGridElement = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(filtersGrid)));
        //WebElement parfumPageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(onParfumPage));
        //WebElement filtersGridElement = wait.until(ExpectedConditions.visibilityOfElementLocated(filtersGrid));
        actions.moveToElement(filtersGridElement).perform();

        return parfumPageElement.isDisplayed() && filtersGridElement.isDisplayed();
    }


    public By getFilterSectionLocator() {
        return filtersGrid;
    }

    public By locatorForFilterCheckboxesByText(String value){
        By highlightsSelection=By.xpath(String.format("//div[@class='facet-option__label']//div[contains(text(),'%s')]",value));
        return highlightsSelection;
    }

    public WebElement dropDownForFilters(String filter){
        WebElement parfumFilter;
        switch (filter){
            case "Highlights":
                parfumFilter= driver.findElement(highlights);
                break;
            case "Marke":
                parfumFilter=driver.findElement(brand);
                break;
            case "Produktart":
                parfumFilter=driver.findElement(productType);
                break;
            case "Geschenk fur":
                parfumFilter=driver.findElement(giftFor);
                break;
            case "Fur Wen":
                parfumFilter=driver.findElement(forWhom);
                break;
            default:
                throw new IllegalArgumentException("Invalid filter: " + filter);
        }
        return parfumFilter;
    }

    public void expandDropDownForFilters(WebElement filterDropDown){
        WebElement refreshedDropDown = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(filterDropDown)));
        refreshedDropDown.click();
    }

    public List<WebElement> checkboxesCountForTheFilter(String filter){
        expandDropDownForFilters(dropDownForFilters(filter));
        return driver.findElements(checkbox);
    }

    public int[] applyFilter(By filterLocator) {
        int initialAppliedFilterCount = driver.findElements(getAllAppliedFilters()).size();
        int initialCheckIconCount = driver.findElements(getCheckIconsWhenFilterApplied()).size();

        WebElement filterElement = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(filterLocator)));
        filterElement.click();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(getAllAppliedFilters(), initialAppliedFilterCount));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(getCheckIconsWhenFilterApplied(), initialCheckIconCount));

        int newAppliedFilterCount = driver.findElements(getAllAppliedFilters()).size();
        int newCheckIconCount = driver.findElements(getCheckIconsWhenFilterApplied()).size();

        return new int[]{initialAppliedFilterCount, newAppliedFilterCount, initialCheckIconCount, newCheckIconCount};
    }

    public void applyFilterViaButton() {
        WebElement applyButton = driver.findElement(applyFilterBtn);
        applyButton.click();
    }

    public void clearFilters() {
        WebElement clearButton=driver.findElement(clearAllFilters);
        if (clearButton.isDisplayed()) {
            clearButton.click();
        }
    }

    public void uncheckCheckedCheckbox(String filter) {
        try {
            expandDropDownForFilters(dropDownForFilters(filter));
            WebElement checkedCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkedCheckboxLocator));
            checkedCheckbox.click();
            ExtentReportUtils.log("Unchecked the checkbox for: " + filter);
        }catch(TimeoutException e){
            ExtentReportUtils.log("Checkbox is already unchecked.");
        }
        catch (Exception ignored) {}
    }


    public By getCheckIconsWhenFilterApplied() {
        return checkIconsWhenFilterApplied;
    }

    public By getClearAllFilters() {
        return clearAllFilters;
    }

    public By getAllAppliedFilters() {
        return allAppliedFilters;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Actions getActions() {
        return actions;
    }

    public String
    getCheckboxText(int index){
        String checkboxTextLocator = checkbox.toString().replace("By.xpath: ", "(") +"//following::div[@class='facet-option__label']/div)"+ "[" + (index) + "]";
        WebElement checkboxText = driver.findElement(By.xpath(checkboxTextLocator));
        return checkboxText.getText();
    }

    public void handleOverlays() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement overlayCloseButton = wait.until(ExpectedConditions.elementToBeClickable(overlay));
            overlayCloseButton.click();
        } catch (TimeoutException ignored) {}
    }

}
