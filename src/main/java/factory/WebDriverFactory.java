package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeDriverSettings;
import factory.settings.EdgeDriverSettings;
import factory.settings.FirefoxDriverSettings;
import factory.settings.ISettings;
import listeners.StyleUpdateListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverFactory {
  //private String browser = System.getProperty("browser").trim().toLowerCase();
  String remoteServer = System.getProperty("webdriver.remote.server");
  URL remoteUrl = new URL(remoteServer);
  RemoteWebDriver driver;

  public WebDriverFactory() throws MalformedURLException {
  }

  public WebDriver create(String browser, String mode) {

    switch(browser) {
      case "chrome": {
        ISettings set = new ChromeDriverSettings();
        driver = new RemoteWebDriver(remoteUrl, set.settings(mode));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
        //driver = new ChromeDriver((ChromeOptions) set.settings(mode));
        break;
      }
      case "firefox": {
        ISettings set = new FirefoxDriverSettings();
        driver = new FirefoxDriver((FirefoxOptions) set.settings(mode));
        break;
      }
      case "edge": {
        ISettings set = new EdgeDriverSettings();
        driver = new EdgeDriver((EdgeOptions) set.settings(mode));
        break;
      }
      case null, default: {
        throw new BrowserNotSupportedException(browser);
      }
    }
    StyleUpdateListener styleUpdateListener = new StyleUpdateListener(driver);
    return new EventFiringDecorator<>(styleUpdateListener).decorate(driver);
  }

}
