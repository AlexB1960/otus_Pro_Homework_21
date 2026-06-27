package common;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public abstract class AbsCommon {
  protected WebDriver driver;
  protected Actions actions;
  protected WebDriverWait wait;

  public AbsCommon(WebDriver driver) {
    this.driver = driver;
    this.actions = new Actions(driver);
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(17));

    PageFactory.initElements(driver, this);
  }

  protected WebElement justElement(By locator) {
    return driver.findElement(locator);
  }

  protected List<WebElement> justElements(By locator) {
    return driver.findElements(locator);
  }

  protected WebElement getElement(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected WebElement getPresentElement(By locator) {
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    //return driver.findElement(locator);
  }

  protected List<WebElement> getElements(By locator) {
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  protected List<WebElement> getPresentElements(By locator) {
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    //return driver.findElements(locator);
  }

  protected void clickElement(By locator) {
    wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
  }

  protected boolean isAvailable(By element) {
    boolean elementStatus;
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(element));
      elementStatus = true;
    } catch (TimeoutException e) {
      System.out.println(e.getMessage() + " Locator " + element + " is not available!");
      elementStatus = false;
    }
    return elementStatus;
  }

  protected boolean notAvailable(By element) {
    boolean elementStatus;
    try {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
      elementStatus = true;
    } catch (TimeoutException e) {
      System.out.println(e.getMessage() + " Locator " + element + " is yet available!");
      elementStatus = false;
    }
    return elementStatus;
  }

  protected boolean isVisible(WebElement element) {
    boolean elementStatus;
    try {
      wait.until(ExpectedConditions.visibilityOf(element));
      elementStatus = true;
    } catch (TimeoutException e) {
      System.out.println(e.getMessage() + " Locator " + element + " is not visible!");
      elementStatus = false;
    }
    return elementStatus;
  }

  public boolean waitForCondition(ExpectedCondition<?> condition) {
    WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(13));
    try {
      waiter.until(condition);
      return true;
    } catch (TimeoutException ignored) {
      return false;
    }
  }

  public boolean waitForElementPresent(By locator) {
    return waitForCondition(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public boolean waitForElementStaleness(WebElement element) {
    return waitForCondition(ExpectedConditions.stalenessOf(element));
  }

  protected void unhide(WebDriver driver, WebElement element) {
    String script = "arguments[0].style.opacity=1;"
        + "arguments[0].style['transform']='translate(0px, 0px) scale(1)';"
        + "arguments[0].style['MozTransform']='translate(0px, 0px) scale(1)';"
        + "arguments[0].style['WebkitTransform']='translate(0px, 0px) scale(1)';"
        + "arguments[0].style['msTransform']='translate(0px, 0px) scale(1)';"
        + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"
        + "return true;";
    ((JavascriptExecutor) driver).executeScript(script, element);
  }

}
