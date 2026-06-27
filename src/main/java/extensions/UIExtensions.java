package extensions;

import com.google.inject.Guice;
import factory.WebDriverFactory;
import modules.GuicePagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;

public class UIExtensions implements BeforeEachCallback, AfterEachCallback{ //, AfterTestExecutionCallback
  private WebDriver driver;
  protected String mode = "--start-maximized";
  protected String browser = System.getProperty("browserName").toLowerCase().trim();
  //public static WebDriverManager webDriverManager;

  @Override
  public void beforeEach(ExtensionContext context) throws MalformedURLException {
    driver = new WebDriverFactory().create(browser, mode);
    Guice.createInjector(new GuicePagesModule(driver)).injectMembers(context.getTestInstance().get());
    //Guice.createInjector(new GuiceComponentsModule(driver)).injectMembers(context.getTestInstance().get());
    //Guice.createInjector(new GuicePopupsModule(driver)).injectMembers(context.getTestInstance().get());
  }

  @Override
  public void afterEach(ExtensionContext context) {
    if (driver != null) {
      driver.quit();
      //quit(driver);
    }
  }

  /*@Override
  public void afterTestExecution(ExtensionContext context) {
    String name = "Скриншот по окончании теста"; //context.getTestInstance().get().getClass().getSimpleName();
    byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    Allure.addAttachment(name, "image/png", "png", screenshotBytes);
  }*/

  /*public void quit(WebDriver driver) {
    WebDriver original = ((Decorated<WebDriver>) driver).getOriginal();
    webDriverManager.quit(original);
  }*/

}
