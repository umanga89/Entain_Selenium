package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

import java.util.List;

public class CheckoutAuthenticationPage {

    public WebDriver driver;

    public CheckoutAuthenticationPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Buttons
    @FindBy(id = "SubmitLogin")
    WebElement button_sign_in;

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_authentication_page_title;

    //Textboxes
    @FindBy(id = "email")
    WebElement textbox_email;

    @FindBy(id = "passwd")
    WebElement textbox_password;


    public boolean isAuthenticationTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_authentication_page_title, 10)) {
            return label_authentication_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public void clickSignInButton(){
        button_sign_in.click();
    }

    public void enterTextToEmailTextbox(String value){
        textbox_email.sendKeys(value);
    }

    public void enterTextToPasswordTextbox(String value){
        textbox_password.sendKeys(value);
    }
}
