package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import components.popups.EducationPopup;
import org.openqa.selenium.WebDriver;

public class GuicePopupsModule extends AbstractModule {
  private WebDriver driver;

  public GuicePopupsModule(WebDriver driver) {
    this.driver = driver;
  }

  @Provides
  @Singleton
  EducationPopup getEducationPopup() {
    return new EducationPopup(driver);
  }

}
