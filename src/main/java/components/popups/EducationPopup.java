package components.popups;

import common.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.CoursesPage;

public class EducationPopup extends AbsCommon implements IPopup<EducationPopup> {
  @FindBy(xpath = "//p[text()='Направления']")
  private WebElement directionsElement;

  @FindBy(xpath = "//span[@title='Обучение']")
  private WebElement educationMenuButton;

  public EducationPopup(WebDriver driver) {
    super(driver);

    //Бага - ???
    //popupShouldNotBeVisible();
  }

  @Override
  public EducationPopup popupShouldNotBeVisible() {
    if (waitForCondition(ExpectedConditions.invisibilityOf(directionsElement))) {
      return this;
    }
    System.out.println("Меню Образование - открыто без вызова");
    throw new RuntimeException();
  }

  @Override
  public EducationPopup popupShouldBeVisible() {
    if (isVisible(directionsElement)) {
      return this;
    }
    System.out.println("Меню Образование - не было открыто при вызове");
    throw new RuntimeException();
  }

  //клик по выбранному направлению direction во всплывающем меню
  public CoursesPage clickOnDirection(StringBuilder direction) {
    directionsElement.findElement(By.xpath(String.format("//a[text()='%s']", direction))).click();

    return new CoursesPage(driver);
  }

}
