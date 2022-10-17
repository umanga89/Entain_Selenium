package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.SeleniumUtil;

public class OrderHistoryPage {

    public WebDriver driver;

    public OrderHistoryPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Labels
    @FindBy(xpath = "//h1")
    WebElement label_order_history_page_title;

    public boolean isOrderHistoryTitleDisplayed(){
        if(SeleniumUtil.WaitForElementToBeDisplayed(driver,label_order_history_page_title, 10)) {
            return label_order_history_page_title.isDisplayed();
        }else{
            return false;
        }
    }

    public String getOrderReferenceOfNthItem(int itemNo){
        return driver.findElement(By.xpath("((//table[@id='order-list']//tr)["+itemNo+"]//a)[1]")).getText().trim();
    }
}
