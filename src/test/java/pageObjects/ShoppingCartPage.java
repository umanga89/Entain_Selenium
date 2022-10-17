package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

import java.util.List;

public class ShoppingCartPage {

    public WebDriver driver;

    public ShoppingCartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(xpath = "//p//a[@title='Proceed to checkout']")
    WebElement button_proceed_to_checkout;

    //Labels
    @FindBy(id = "cart_title")
    WebElement label_shopping_cart_title;

    @FindBy(xpath = "//tr[contains(@class,'cart_item')]")
    List<WebElement> label_items_in_shopping_cart;

    @FindBy(id = "total_product")
    WebElement label_total_of_products_in_cart;

    @FindBy(id = "total_shipping")
    WebElement label_total_of_shipping_in_cart;

    @FindBy(id = "total_price_without_tax")
    WebElement label_total_without_tax_in_cart;

    @FindBy(id = "total_tax")
    WebElement label_total_tax_in_cart;

    @FindBy(id = "total_price")
    WebElement label_total_price_in_cart;



    public boolean isShoppingCartTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_shopping_cart_title, 10)) {
            return label_shopping_cart_title.isDisplayed();
        }else{
            return false;
        }
    }

    public int getNumberOfItemsInShoppingCart(){
        return label_items_in_shopping_cart.size();
    }

    public String getProductNameOfNthItemInShoppingCart(int itemNo){
        return driver.findElement(By.xpath("(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//p/a")).getText().trim();
    }

    public String getProductQuantityOfNthItemInShoppingCart(int itemNo){
        return driver.findElement(By.xpath("(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//input[@type='hidden']")).getAttribute("value");
    }

    public String getUnitPriceOfNthItemsInShoppingCard(int itemNo){
        return driver.findElement(By.xpath("(//tr[contains(@class,'cart_item')])["+itemNo+"]//span[contains(@id,'product_price')]/span")).getText().trim();
    }

    public String getProductItemTotalOfNthItemInShoppingCart(int itemNo){
        return driver.findElement(By.xpath("(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//td[@data-title='Total']/span")).getText().trim();
    }

    public String getTotalOfProductsInCart(){
        return label_total_of_products_in_cart.getText().trim();
    }

    public String getTotalOfShippingInCart(){
        return label_total_of_shipping_in_cart.getText().trim();
    }

    public String getTotalWithShippingInCart(){
        return label_total_without_tax_in_cart.getText().trim();
    }

    public String getTotalTaxInCart(){
        return label_total_tax_in_cart.getText().trim();
    }

    public String getTotalPriceInCart(){
        return label_total_price_in_cart.getText().trim();
    }

    public void clickQtyDownButtonOfNthItemInShoppingCart(int itemNo){
        driver.findElement(By.xpath("(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//a[contains(@class,'cart_quantity_down')]")).click();
    }

    public void clickQtyUpButtonOfNthItemInShoppingCart(int itemNo){
        driver.findElement(By.xpath("(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//a[contains(@class,'cart_quantity_up')]")).click();
    }

    public void clickRemoveButtonOfNthItemInShoppingCart(int itemNo){
        driver.findElement(By.xpath("(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//a[@title='Delete']")).click();
    }

    public boolean waitUntilQuantityIfProductIsUpdatedInShoppingCart(int itemNo, int quantity){
        String xpath = "(//table[@id='cart_summary']//tr[contains(@id,'product')])["+itemNo+"]//input[@type='hidden' and @value='"+quantity+"']";
        return SeleniumUtil.WaitForElementToBePresent(driver, xpath, 10);
    }

    public boolean waitUntilDeletedItemIsRemovedFromShoppingCart(String itemName){
        String xpath = "//table[@id='cart_summary']//tr[contains(@id,'product')]//p/a[text()='"+itemName+"']";
        return SeleniumUtil.WaitForElementToBeInvisible(driver, xpath, 10);
    }

    public void clickProceedToCheckoutButton(){
        button_proceed_to_checkout.click();
    }
}
