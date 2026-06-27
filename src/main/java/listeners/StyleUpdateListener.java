package listeners;

import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

@Singleton
public class StyleUpdateListener implements WebDriverListener {
  WebDriver driver;
  public StyleUpdateListener(WebDriver driver) {
    this.driver = driver;
  }

  @SneakyThrows
  @Override
  public void beforeClick(WebElement element) {
    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].setAttribute(\"style\", \"border:5px solid red\");", element);
  }

}
