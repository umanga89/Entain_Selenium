package pageObjects;

import io.cucumber.java.eo.Se;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class SearchResultsPage {

    public WebDriver driver;

    public SearchResultsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons


    //Labels
    @FindBy(xpath = "//h1[contains(@class,'page-heading')]//span[@class='lighter']")
    WebElement label_searched_results_topic;

    //Textboxes


    public boolean isSearchResultsPageDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_searched_results_topic, 10)) {
            return label_searched_results_topic.isDisplayed();
        }else{
            return false;
        }
    }

    public String getSearchedTextCriteiraQuantity(){
        return label_searched_results_topic.getAttribute("innerHTML");
    }

    public String getNameOfNthSearchResults(int searchResultsItem){
        return driver.findElement(By.xpath("(//div[@class='product-container'])["+searchResultsItem+"]//div[@class='right-block']//a[@class='product-name']")).getText();
    }

    public String getPriceOfNthSearchResults(int searchResultsItem){
        return driver.findElement(By.xpath("(//div[@class='product-container'])["+searchResultsItem+"]//div[@class='right-block']//span[@itemprop='price']")).getText();
    }

    public void addNthProductToCartFromSearchResults(int searchResultsItem){
        WebElement firstRecord = driver.findElement(By.xpath("(//div[@class='product-container'])["+searchResultsItem+"]//div[@class='right-block']"));
        SeleniumUtil.ScrollToElementUsingJavaScript(driver, firstRecord);
        SeleniumUtil.MouseOverAnElement(driver, firstRecord);
        WebElement addToCartButton = driver.findElement(By.xpath("(//div[@class='product-container'])["+searchResultsItem+"]//div[@class='right-block']//a[contains(@class,'add_to_cart_button')]"));
        addToCartButton.click();
    }

}
