package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BaseTest {

//    public static WebDriver driver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

//        options.setExperimentalOption("excludeSwitches", "enable-automation");

        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-save-password-bubble");

        options.addArguments("--user-data-dir="+ System.getProperty("java.io.tmpdir") +
                "chrome-profile-" + System.currentTimeMillis());

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();

        driver.set(webDriver);
        webDriver.get(ConfigReader.getBaseUrl());
    }

    public static WebDriver getDriver() {
        return driver.get();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if(getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
