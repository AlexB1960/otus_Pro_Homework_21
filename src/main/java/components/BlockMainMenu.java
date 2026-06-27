package components;

import annotations.Component;
import components.popups.EducationPopup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Component("xpath://span[@title='Обучение']")
public class BlockMainMenu extends AbsComponent {
  @FindBy(xpath = "//span[@title='Обучение']")
  private WebElement educationMenuButton;

  @FindBy(xpath = "//p[text()='Направления']")
  private WebElement directionsElement;

  public BlockMainMenu(WebDriver driver) {
    super(driver);
  }

  //мышкой наводим на пункт меню Обучение
  public EducationPopup moveToEducationItem() {
    waitForElementStaleness(educationMenuButton);
    actions.moveToElement(educationMenuButton).build().perform();

    return new EducationPopup(driver);
  }

}
