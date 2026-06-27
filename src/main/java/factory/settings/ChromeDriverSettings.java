package factory.settings;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import java.util.ArrayList;
import java.util.HashMap;
//import static extensions.UIExtensions.webDriverManager;

public class ChromeDriverSettings implements ISettings {

  @Override
  public AbstractDriverOptions settings(String... userArgs) {

    //webDriverManager.chromedriver().setup();
    WebDriverManager.chromedriver().setup();
    ChromeOptions chromeOptions = new ChromeOptions();
    switch (userArgs[0].toLowerCase().trim()) {
      case "headless": {
        chromeOptions.addArguments("--headless");
        break;
      }
      case "fullscreen": {
        chromeOptions.addArguments("--fullscreen");
        break;
      }
      case "maximize": {
        chromeOptions.addArguments("--start-maximized");
        break;
      }
      case null, default: {
        chromeOptions.addArguments("--start-maximized");
      }
        /*DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("119.0");

        capabilities.setCapability("sessionTimeout", "15m");
        capabilities.setCapability("enableVnc", true);
        capabilities.setCapability("enableNetwork", true); // To enable network logs
        //capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("enableVideo", false); // To enable video recording

        chromeOptions.merge(capabilities);*/
        chromeOptions.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "15m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
              }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
              }});

            put("browserName", "chrome");
            put("browserVersion", "119.0");
            put("enableNetwork", true);
            put("enableVnc", true);
            /* How to enable video recording */
            //put("enableVideo", true);
          }});
    }
    //chromeOptions.merge(desiredCapabilities);
    return chromeOptions;
  }

}
