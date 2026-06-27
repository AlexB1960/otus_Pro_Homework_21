package pages;

import annotations.Path;
import io.qameta.allure.Step;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {
  @FindBy(xpath = "//*[text()='Направление']/../following-sibling::div//input")
  private List<WebElement> checkBoxInputs;

  private final By courseSelector = By.cssSelector("a:is([class^='sc-18']>[href^='/lessons/'])");

  public CoursesPage(WebDriver driver) {
    super(driver);
  }

  @Step("Проверить состояние выбора чекбокса {name}")
  public void assertDirection(StringBuilder name, boolean isChecked) {
    waitForElementStaleness(justElement(By.xpath("//label/text()[.='" + name + "']/preceding::input")));
    //Выбираем в elements все чекбоксы начиная сверху и до чекбокса у name включительно. Чекбокс у name - последний !!!
    List<WebElement> elements = justElements(By.xpath("//label/text()[.='" + name + "']/preceding::input"));
    Assertions.assertEquals(isChecked, elements.getLast().isSelected());
  }

  //Поиск и клик на карточке указанного в name курса
  @Step("Найти и кликнуть на карточке {name}")
  public KursPage getCourse(String name) {
    if (waitForElementPresent(courseSelector)) {  // (waitForElementStaleness(getPresentElement(courseSelector)))
      try {
        WebElement element = getPresentElements(courseSelector).stream()
            .filter(course -> course.getText().contains(name + '\n'))
            .findFirst().get();
        String courseHref = element.getAttribute("href")
            .substring(15);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("document.querySelector('[href=\"" + courseHref + "\"]').scrollIntoView();");
        unhide(driver, element);
        element.click();

        return new KursPage(driver);
      } catch (NoSuchElementException e) {
        System.out.println("Не обнаружен курс с названием - " + name);
        throw new RuntimeException();
      }
    } else {
      System.out.println("Ошибка ожидания локатора");
      throw new RuntimeException();
    }
  }

  @Step("Найти курс с {end} датой начала обучения")
  public CoursesPage getMinMaxCourse(String end) {
    WebElement element;
    //Выбираем в element соответствующую карточку (Min или Max)
    if (waitForElementPresent(courseSelector)) { // (waitForElementStaleness(getPresentElement(courseSelector)))
      try {
        if (end.trim().equalsIgnoreCase("min")) {
          element = getPresentElements(courseSelector).stream()
              .reduce((el1, el2) -> getElementDate(el1)
                  .isBefore(getElementDate(el2)) ? el1 : el2)
              .get();
        } else if (end.trim().equalsIgnoreCase("max")) {
          element = getPresentElements(courseSelector).stream()
              .reduce((el1, el2) -> getElementDate(el1)
                  .isAfter(getElementDate(el2)) ? el1 : el2)
              .get();
        } else throw new NoSuchElementException();

        //Берем в links данные по дате Внутри страницы карточки
        String courseHref = element.getAttribute("href");
        assert courseHref != null;
        Document doc = Jsoup.connect(courseHref).get();
        Elements links = doc.select("div[class='sc-3cb1l3-3 jeNzke'] p");

        //Сравниваем даты внутри страницы карточки и на карточке
        Assertions.assertEquals(links.getFirst().text(), getDateFromCard(element)
            .substring(0, getDateFromCard(element).indexOf(',')));

        //Берем в links название курса Внутри страницы карточки
        links = doc.select("div h1");

        //Сравниваем названия курса внутри страницы карточки и на карточке
        Assertions.assertEquals(links.getFirst().text(), getNameFromCard(element));

        return this;
      } catch (NoSuchElementException | IOException e) {
        System.out.println("Ошибка выбора " + end +" даты курсов");
        throw new RuntimeException();
      }
    } else {
      System.out.println("Ошибка ожидания локатора");
      throw new RuntimeException();
    }
  }

  public LocalDate getElementDate(WebElement element) {
    String courseDate = getDateFromCard(element);
    courseDate = courseDate.substring(0, courseDate.indexOf('·')).trim();
    courseDate = courseDate.replace("января,", "01");
    courseDate = courseDate.replace("февраля,", "02");
    courseDate = courseDate.replace("марта,", "03");
    courseDate = courseDate.replace("апреля,", "04");
    courseDate = courseDate.replace("мая,", "05");
    courseDate = courseDate.replace("июня,", "06");
    courseDate = courseDate.replace("июля,", "07");
    courseDate = courseDate.replace("августа,", "08");
    courseDate = courseDate.replace("сентября,", "09");
    courseDate = courseDate.replace("октября,", "10");
    courseDate = courseDate.replace("ноября,", "11");
    courseDate = courseDate.replace("декабря,", "12");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
    return LocalDate.parse(courseDate, formatter);
  }

  public String getDateFromCard(WebElement element) {
    if (element.getText().contains("Скидка") && element.getText().contains("Успеть!")) {
      return element.getText().split("\n")[4];
    } else if (element.getText().contains("Скидка") ^ element.getText().contains("Успеть!")) {
      return element.getText().split("\n")[3];
    } else {
      return element.getText().split("\n")[2];
    }
  }

  public String getNameFromCard(WebElement element) {
    if (element.getText().contains("Скидка") && element.getText().contains("Успеть!")) {
      return element.getText().split("\n")[3];
    } else if (element.getText().contains("Скидка") ^ element.getText().contains("Успеть!")) {
      return element.getText().split("\n")[2];
    } else {
      return element.getText().split("\n")[1];
    }
  }

}
