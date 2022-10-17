package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

import java.util.List;

public class CheckoutAddressesPage {

    public WebDriver driver;

    public CheckoutAddressesPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(name = "processAddress")
    WebElement button_proceed_to_checkout;

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_address_page_title;

    public boolean isAddressesTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_address_page_title, 10)) {
            return label_address_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public void clickProceedToCheckoutButton(){
        button_proceed_to_checkout.click();
    }
}
