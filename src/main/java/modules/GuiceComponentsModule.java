package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import components.BlockMainMenu;
import org.openqa.selenium.WebDriver;

public class GuiceComponentsModule extends AbstractModule {
  private WebDriver driver;

  public GuiceComponentsModule(WebDriver driver) {
    this.driver = driver;
  }

  @Provides
  @Singleton
  public BlockMainMenu getBlockMainMenu() {
    return new BlockMainMenu(driver);
  }

}
