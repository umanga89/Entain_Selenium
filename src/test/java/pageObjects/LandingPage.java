package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

import java.util.List;

public class LandingPage {

    public WebDriver driver;

    public LandingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(xpath = "//img[@alt='My Store']")
    @CacheLookup
    WebElement button_website_logo;

    @FindBy(xpath = "//a[@class='login']")
    WebElement button_sign_in;

    @FindBy(name = "submit_search")
    WebElement button_submit_search;

    @FindBy(xpath = "//span[@class='cross']")
    WebElement button_product_added_to_cart_close;

    @FindBy(xpath = "//a[@class='account']")
    WebElement button_account;

    @FindBy(xpath = "//a[@class='logout']")
    WebElement button_sign_out;

    //Labels
    @FindBy(xpath = "//a[@title='View my shopping cart']/span[contains(@class,'ajax_cart_quantity')]")
    WebElement label_shopping_cart_quantity;

    @FindBy(xpath = "//div[contains(@class,'layer_cart_product')]//h2")
    WebElement label_product_successfully_added_to_cart;

    @FindBy(xpath = "//dt[contains(@data-id,'cart_block_product')]")
    List<WebElement> label_items_in_the_cart;

    @FindBy(xpath = "//span[contains(@class,'price cart_block_total')]")
    WebElement label_total_price_in_cart;

    @FindBy(xpath = "//span[contains(@class,'price cart_block_shipping_cost')]")
    WebElement label_shipping_price_in_cart;

    //Textboxes
    @FindBy(id = "search_query_top")
    WebElement textbox_search_bar;


    public boolean isCompanyLogoDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,button_website_logo, 10)) {
            return button_website_logo.isDisplayed();
        }else{
            return false;
        }
    }

    public String getShoppingCardQuantity(){
        return label_shopping_cart_quantity.getAttribute("innerHTML");
    }

    public boolean isSignInButtonDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,button_sign_in, 10)) {
            return button_sign_in.isDisplayed();
        }else{
            return false;
        }
    }

    public boolean isProductSuccessfullyAddedToCartPopUpDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_product_successfully_added_to_cart, 10)) {
            return label_product_successfully_added_to_cart.isDisplayed();
        }else{
            return false;
        }
    }

    public void enterTextToSearchBar(String text){
        textbox_search_bar.clear();
        textbox_search_bar.sendKeys(text);
    }

    public void clickSubmitSearchButton(){
        button_submit_search.click();
    }

    public void clickProductAddedToCartCloseButton(){
        button_product_added_to_cart_close.click();
    }

    public void clickOnShoppingCartIconToNavigateToShoppingCart(){
        label_shopping_cart_quantity.click();
    }

    public void clickOnAccountButton(){
        button_account.click();
    }

    public void clickOnSignOutButton(){
        button_sign_out.click();
    }

    public void hoverOverShoppingCart(){
        SeleniumUtil.MouseOverAnElement(driver,label_shopping_cart_quantity);
    }

    public int getNumberOfItemsInTheCart(){
        return label_items_in_the_cart.size();
    }

    public String getQuantityOfNthItemsInShoppingCard(int searchResultsItem){
        return driver.findElement(By.xpath("(//dt[contains(@data-id,'cart_block_product')])["+searchResultsItem+"]//span[@class='quantity']")).getText();
    }

    public String getNameOfNthItemsInShoppingCard(int searchResultsItem){
        return driver.findElement(By.xpath("(//dt[contains(@data-id,'cart_block_product')])["+searchResultsItem+"]//a[@class='cart_block_product_name']")).getAttribute("title");
    }

    public String getPriceOfNthItemsInShoppingCard(int searchResultsItem){
        return driver.findElement(By.xpath("(//dt[contains(@data-id,'cart_block_product')])["+searchResultsItem+"]//span[@class='price']")).getText();
    }

    public String getTotalPriceInShoppingCard(){
        return label_total_price_in_cart.getText();
    }

    public String getShippingPriceInShoppingCard(){
        return label_shipping_price_in_cart.getText();
    }
}
