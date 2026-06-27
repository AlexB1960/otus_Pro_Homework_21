package pages;

import annotations.Path;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Path("/lessons/razrabotchik-php")
public class KursPage extends AbsBasePage<KursPage> {
  private final By courseNameSelector = By.cssSelector("div h1");
  /*@FindBy(css = "div h1") //tagName
  private WebElement courseNameSelector;*/

  public KursPage(WebDriver driver) {
    super(driver);
  }

  @Step("Проверить соответствие названия выбранного курса {name} с курсом карточки")
  public KursPage assertCourseName(String name) {
    if (waitForElementPresent(courseNameSelector)) {
      Assertions.assertEquals(getElement(courseNameSelector).getText(), name);
      return this;
    } else {
      System.out.println("К сожалению, courseNameSelector is stale");
      throw new RuntimeException();
    }
  }

}
