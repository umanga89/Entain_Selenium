package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class CheckoutOrderConfirmationPage {

    public WebDriver driver;

    public CheckoutOrderConfirmationPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_order_confirmation_page_title;

    @FindBy(xpath = "//div[@class='box']")
    WebElement label_order_confirmation_summary_text;


    public boolean isOrderSummaryTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_order_confirmation_page_title, 10)) {
            return label_order_confirmation_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public String getOrderSummaryText(){
        return label_order_confirmation_summary_text.getText().trim();
    }

}
