package util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtil {
    public static boolean WaitForElementToBeDisplayed(WebDriver driver, WebElement element, int seconds)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,seconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch(NoSuchElementException ex)
        {
            throw ex;
        }
    }

    public static boolean WaitForElementToBePresent(WebDriver driver, String xpath , int seconds)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,seconds);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            return true;
        }
        catch(NoSuchElementException ex)
        {
            throw ex;
        }
    }

    public static boolean WaitForElementToBeInvisible(WebDriver driver, String xpath , int seconds)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,seconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
            return true;
        }
        catch(NoSuchElementException ex)
        {
            throw ex;
        }
    }

    public static void MouseOverAnElement(WebDriver driver, WebElement element){
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        }catch (Exception e){
            throw e;
        }
    }

    public static void ScrollToElementUsingJavaScript(WebDriver driver, WebElement element)
    {
        try
        {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0]. scrollIntoView(true);", element);
        }
        catch (NoSuchElementException e)
        {
            throw e;
        }
    }
}
