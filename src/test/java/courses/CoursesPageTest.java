package courses;

import com.google.inject.Inject;
import extensions.UIExtensions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.MainPage;

@ExtendWith(UIExtensions.class)
@Epic("Тесты онлайн-курсов на сайте otus.ru")
@Story("UI тесты")
@DisplayName("UI тесты онлайн-курсов на сайте otus.ru")
public class CoursesPageTest {

  @Inject
  private MainPage mainPage;

  /*@Inject
  private BlockMainMenu blockMainMenu;

  @Inject
  private EducationPopup educationPopup;*/

  //Сценарий 1 - проверка выбранного курса
  @Test
  @DisplayName("Сценарий 1 - проверка выбранного курса")
  @Feature("Карточка выбранного курса")
  public void getCorrectCourse() {
    //Здесь можно изменить выбранный курс обучения
    String currentCourse = "Архитектура и шаблоны проектирования"; //"Python Developer"
    mainPage.open()
        .start()
        .openCoursesPageByClick()
        .getCourse(currentCourse)
        .assertCourseName(currentCourse);
  }

  //Сценарий 2.1 - Самый ранний курс
  @Test
  @DisplayName("Сценарий 2.1 - Выбор самого раннего курса")
  @Feature("Карточка самого раннего курса")
  public void getFirstCourse() {
    mainPage.open()
        .start()
        .openCoursesPageByClick()
        .getMinMaxCourse("Min");
  }

  //Сценарий 2.2 - Самый поздний курс
  @Test
  @DisplayName("Сценарий 2.2 - Выбор самого позднего курса")
  @Feature("Карточка самого позднего курса")
  public void getLastCourse() {
    mainPage.open()
        .start()
        .openCoursesPageByClick()
        .getMinMaxCourse("Max");
  }

  //Сценарий 3 - Случайный выбор и проверка направления
  @Test
  @DisplayName("Сценарий 3 - Случайный выбор и проверка направления")
  @Feature("Случайный выбор направления обучения")
  public void getCorrectCategory() {
    StringBuilder direction = new StringBuilder();
    mainPage.open()
        .start()
        .getRandomDirection(direction)
        .moveToEducationItem()
        .popupShouldBeVisible()
        .clickOnDirection(direction)
        .assertDirection(direction, true);
  }

}
