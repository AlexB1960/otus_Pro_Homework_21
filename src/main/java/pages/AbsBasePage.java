package pages;

import annotations.Path;
import common.AbsCommon;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbsBasePage<T> extends AbsCommon {
  //private String remoteServer = System.getProperty("webdriver.remote.server");
  //public URL remoteUrl = new URL(remoteServer);
  private String baseURL = System.getProperty("baseUrl");

  @FindBy(css = "h1") //tagName
  private WebElement header;

  public AbsBasePage(WebDriver driver) {
    super(driver);
  }

  private String getPath() {
    Class clazz = this.getClass();

    if (clazz.isAnnotationPresent(Path.class)) {
      Path path = (Path) clazz.getDeclaredAnnotation(Path.class);
      return path.value();
    }
    return "";
  }

  private String getPathFromTemplate(String... data) {
    Class clazz = this.getClass();

    if (clazz.isAnnotationPresent(Path.class)) {
      Path path = (Path) clazz.getDeclaredAnnotation(Path.class);

      String value = path.value();
      for (int i =0; i < data.length; i++) {
        value = value.replace("$" + i + 1, data[i]);
      }
      return value;
    }
    return "";
  }

  public T open() {
    Allure.step("Открыть страницу " + getPath(), () -> driver.get(baseURL + getPath()));
    return (T) this;
  }

  public T open(String... data) {
    driver.get(baseURL + getPath() + getPathFromTemplate(data));
    return (T) this;
  }

  public T headerShouldBeSameAs() {
    waitForElementStaleness(header);
    Assertions.assertEquals(this.header.getText(), header.getText());
    return (T) this;
  }

}
