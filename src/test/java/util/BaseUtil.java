package util;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import pojos.Product_Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BaseUtil {

    public WebDriver driver;
    public static String webUrl;
    public static String browser;
    public static String base_directory;
    public static Logger logger;
    public static Properties configProperties;
    public Scenario message;
    public static List<Product_Properties> products = new ArrayList<>();
    public static double total_price;
    public static String order_reference;

    public void takeScreenShot(WebDriver driver,String label) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        message.attach(screenshot, "image/png",label);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(String browserValue) throws Exception {
        browser = browserValue;
        if(browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else {
            throw new Exception("Please check browser value");
        }
        driver.manage().window().maximize();
    }
}
