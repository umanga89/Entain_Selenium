package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class CheckoutPaymentMethodPage {

    public WebDriver driver;

    public CheckoutPaymentMethodPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(xpath = "//a[@class='bankwire']")
    WebElement button_pay_by_bank_wire;

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_payment_page_title;


    public boolean isPaymentTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_payment_page_title, 10)) {
            return label_payment_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public void clickPayByBankWireButton(){
        button_pay_by_bank_wire.click();
    }
}
