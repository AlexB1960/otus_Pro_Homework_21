package factory.settings;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class EdgeDriverSettings implements  ISettings {

  @Override
  public AbstractDriverOptions settings(String... userArgs) {

    WebDriverManager.edgedriver().setup();
    EdgeOptions edgeOptions = new EdgeOptions();
    switch (userArgs[0].toLowerCase().trim()) {
      case "headless": {
        edgeOptions.addArguments("--headless");
        break;
      }
      case "fullscreen": {
        edgeOptions.addArguments("--fullscreen");
        break;
      }
      case "maximize": {
        edgeOptions.addArguments("--start-maximized");
        break;
      }
      case null, default: {
        edgeOptions.addArguments("--start-maximized");
      }
    }
    //edgeOptions.merge(desiredCapabilities);
    return edgeOptions;
  }
}
