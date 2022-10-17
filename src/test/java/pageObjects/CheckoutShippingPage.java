package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class CheckoutShippingPage {

    public WebDriver driver;

    public CheckoutShippingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(name = "processCarrier")
    WebElement button_proceed_to_checkout;

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_shipping_page_title;

    //Checkboxes
    @FindBy(id = "cgv")
    WebElement checkbox_accept_terms_and_conditions;

    public boolean isShippingTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_shipping_page_title, 10)) {
            return label_shipping_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public void clickTermsAndConditionsCheckbox(){
        checkbox_accept_terms_and_conditions.click();
    }

    public void clickProceedToCheckoutButton(){
        button_proceed_to_checkout.click();
    }
}
