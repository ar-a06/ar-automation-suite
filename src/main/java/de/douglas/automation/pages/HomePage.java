package de.douglas.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By parfumEntryDisplayed=By.xpath("//a[@class='link link--nav-heading navigation-main-entry__link' and (text()='PARFUM' or text()='PERFUME')]");
    private By parfumPageLink=By.xpath("//a[@href='/de/c/parfum/01']");
    private By parfumFromHome=By.xpath("//span[@class='breadcrumb__list']//a[text()='Homepage' or text()='Home'] | //span[@class='breadcrumb__list']//span[@class='breadcrumb__entry' and (text()='Parfum' or text()='Perfume')]");
    private By hoverContent = By.xpath("//div[@data-testid='default-flyout-content' and @class='navigation-main__content']");
    //private By menuOptionsMobileView=By.xpath("//button[@class='button button__with-icon--transparent button__normal header-component__button']");
    //private By parfumOptionInSlideOutMenu=By.xpath("//li[@class='nav-mobile-list__item' and @data-testid='nav-mobile-parent-node']//span[@class='nav-mobile-dynamic__item-title' and (contains(text(), 'Parfum') or contains(text(), 'Perfume'))]");
    //private By parfumSlideOutMenu=By.xpath("//div[@class='nav-mobile__header-title' and (text()='Parfum' or text()='Perfume')]");
    //private By backInParfumeSlideOutMenu=By.xpath("//button[@class='button button__with-icon button__normal nav-mobile__header-button nav-mobile__header-button--home' and @type='button']");
    //private By closeInParfumeSlideOutMenu=By.xpath("//button[@class='button button__with-icon button__normal nav-mobile__header-button nav-mobile__header-button--close button--content-width' and @type='button']");
    //private By parfumOptionsDisplayedInSlideOutMenu=By.xpath("//a[@class='link link--text link link--text nav-mobile-dynamic__item nav-mobile-dynamic__item--highlighted' and (@title='PARFUM' or @title='PERFUME')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        this.actions = new Actions(driver);
    }

    public boolean areParfumElementsVisible() {
        boolean isParfumOptionsDisplayedVisible = driver.findElement(parfumEntryDisplayed).isDisplayed();
        boolean isParfumPageLinkVisible = driver.findElement(parfumPageLink).isDisplayed();

        return isParfumOptionsDisplayedVisible && isParfumPageLinkVisible;
    }

    public void clickOnParfumPage() {
        try{
            driver.findElement(parfumPageLink).click();
            actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(parfumFromHome))).perform();
        }catch(Exception ignored){}
        try {
            WebElement hoverMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(hoverContent));
            if (hoverMenu.isDisplayed()) {
                actions = new Actions(driver);
                actions.moveByOffset(32, 16).perform();
            }
        } catch (TimeoutException ignored) {}
        scrollDownOnce();
    }

    public boolean verifyOnParfumPage() {
        return driver.findElement(parfumFromHome).isDisplayed();
    }

    public void clickElementWhenClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public void scrollDownOnce() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0, window.innerHeight);");
    }

}
