package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class AccountPage {

    public WebDriver driver;

    public AccountPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(xpath = "//a[@title='Orders']")
    WebElement button_order_history_and_details;

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_account_page_title;

    public boolean isAccountTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_account_page_title, 10)) {
            return label_account_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public void clickOrderHistoryAndDetailsButton(){
        button_order_history_and_details.click();
    }
}
