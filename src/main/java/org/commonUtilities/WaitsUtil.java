package org.commonUtilities;

import org.BaseTestLayer.BaseTestClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class WaitsUtil extends BaseTestClass {

    private WebDriver driver;
    private static final int DEFAULT_TIMEOUT    = 15;
    private static final int LONG_TIMEOUT       = 30;
    private static final int SHORT_TIMEOUT      = 5;
    private static final int POLLING_INTERVAL   = 2;

    // ============================
    // CONSTRUCTOR
    // ============================
    public WaitsUtil(WebDriver driver) {

        this.driver = driver;
    }

    // ============================
    // PRIVATE HELPER — reusable wait builder
    // ============================
    private WebDriverWait getWait(int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    // ============================
    // 1. WAIT FOR ELEMENT VISIBLE
    // ============================
    public WebElement waitForVisible(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForVisible(By locator, int timeout) {
        return getWait(timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ============================
    // 2. WAIT FOR ELEMENT CLICKABLE
    // ============================
    public WebElement waitForClickable(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForClickable(By locator, int timeout) {
        return getWait(timeout)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ============================
    // 3. WAIT FOR ELEMENT PRESENT IN DOM
    // ============================
    public WebElement waitForPresence(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public WebElement waitforPresence(By locator, int timeout)
    {
        return getWait(timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ============================
    // 4. WAIT FOR ELEMENT TO DISAPPEAR
    // ============================
    public boolean waitForInvisible(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ============================
    // 5. WAIT FOR PAGE TITLE
    // ============================
    public boolean waitForTitle(String title) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.titleContains(title));
    }

    // ============================
    // 6. WAIT FOR URL TO CONTAIN
    // ============================
    public boolean waitForUrlContains(String urlFragment) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlContains(urlFragment));
    }

    // ============================
    // 7. WAIT FOR TEXT IN ELEMENT
    // ============================
    public boolean waitForTextPresent(By locator, String text) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ============================
    // 8. WAIT FOR ATTRIBUTE VALUE
    // ============================
    public boolean waitForAttributeContains(By locator, String attribute, String value) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

    // ============================
    // 9. WAIT FOR LIST OF ELEMENTS
    // ============================
    public List<WebElement> waitForAllVisible(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ============================
    // 10. WAIT FOR ALERT
    // ============================
    public Alert waitForAlert() {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.alertIsPresent());
    }

    // ============================
    // 11. WAIT FOR FRAME & SWITCH
    // ============================
    public WebDriver waitForFrameAndSwitch(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public WebDriver waitForFrameAndSwitch(int frameIndex) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    // ============================
    // 12. FLUENT WAIT — for dynamic/slow elements
    // ============================
    public WebElement fluentWait(By locator) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(LONG_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_INTERVAL))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> d.findElement(locator));
    }

    public WebElement fluentWait(By locator, int timeout, int polling) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> d.findElement(locator));
    }

    // ============================
    // 13. WAIT FOR STALE ELEMENT TO REFRESH
    // ============================
    public boolean waitForStaleness(WebElement element) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.stalenessOf(element));
    }

    // ============================
    // 14. WAIT FOR JAVASCRIPT / PAGE LOAD
    // ============================
    public void waitForPageLoad() {
        getWait(LONG_TIMEOUT).until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    public void waitForJQueryLoad() {
        getWait(LONG_TIMEOUT).until(driver ->
                (Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return jQuery.active == 0")
        );
    }

    // ============================
    // 15. HARD WAIT — use ONLY when absolutely needed
    // ============================
    public void hardWait(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}