package de.douglas.automation.utils;

import org.openqa.selenium.*;
import java.time.Duration;

public class CookiesConsentHandler {

    public static void acceptCookies(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            WebElement userRoot=driver.findElement(By.id("usercentrics-root"));
            SearchContext shadowRoot=userRoot.getShadowRoot();
            shadowRoot.findElement(By.cssSelector("button.sc-dcJsrY.eIFzaz")).click();
            ExtentReportUtils.log("Cookies consent overlay handled.");

        }catch (NoSuchElementException e) {
            ExtentReportUtils.log("Cookies consent overlay did not appear.");
        }
    }
}
