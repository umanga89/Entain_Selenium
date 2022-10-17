package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class CheckoutOrderSummaryPage {

    public WebDriver driver;

    public CheckoutOrderSummaryPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(xpath = "//button/span[text()='I confirm my order']")
    WebElement button_i_confirm_order;

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_order_summary_page_title;


    public boolean isOrderSummaryTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_order_summary_page_title, 10)) {
            return label_order_summary_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public void clickIConfirmOrderButton(){
        button_i_confirm_order.click();
    }
}
