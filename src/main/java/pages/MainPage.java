package pages;

import annotations.Path;
import components.BlockMainMenu;
import io.qameta.allure.Step;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {
  @FindBy(css = "[href='https://otus.ru/catalog/courses']")
  private WebElement buttonCourses;

  private final By notificationButton = By.xpath("//*[contains(text(), 'Посещая наш сайт')]/following-sibling::div//button");

  @FindBy(xpath = "//span[@title='Обучение']")
  private WebElement educationMenuButton;

  @FindBy(xpath = "//p[text()='Направления']")
  private WebElement directionsElement;

  public MainPage(WebDriver driver) {
    super(driver);
  }

  @Step("Принять политику использования cookie-файлов")
  public MainPage start() {
    headerShouldBeSameAs();
    clickOnCookieMessage();
    return this;
  }

  @Step("Прокрутить страницу до появления кнопки Все курсы")
  public CoursesPage openCoursesPageByClick() {
    //прокручиваем до кнопки Все курсы
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("document.querySelector('[href=\""
        + buttonCourses.getAttribute("href") + "\"]').scrollIntoView();");
    unhide(driver, buttonCourses);

    buttonCourses.click();
    return new CoursesPage(driver);
  }

  @Step("Выбрать случайное направление курсов обучения")
  public BlockMainMenu getRandomDirection(StringBuilder direction) {
    //считываем названия направлений всплывающего меню в directionNames
    Document doc = null;
    try {
      doc = Jsoup.connect("https://otus.ru").get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    List<Element> directionNames = null;
    Elements links = doc.select("a[href^='/categories/']");
    directionNames = links.stream().filter(el -> el.text().contains("(")).toList();

    //выбираем случайное направление
    Random rand = new Random();
    String randomDirection = directionNames.get(rand.nextInt(directionNames.size() - 1)).text();
    direction.append(randomDirection.substring(0, randomDirection.indexOf('(')).trim());

    return new BlockMainMenu(driver);
  }

  public void clickOnCookieMessage() {
    if (isVisible(getPresentElement(notificationButton))) {
      unhide(driver, getElement(notificationButton));
      clickElement(notificationButton);
    } else {
      /*unhide(driver, getPresentElement(notificationButton));
      getElement(notificationButton).click();*/
    }
  }

}
