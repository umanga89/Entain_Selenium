package steps;

import io.cucumber.java.en.When;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import pageObjects.*;
import util.BaseUtil;
import util.GeneralUtil;

import java.util.List;

public class CheckoutAuthenticationSteps extends BaseUtil{

    private BaseUtil base;
    CheckoutAuthenticationPage checkoutAuthenticationPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutAddressesPage checkoutAddressesPage;
    CheckoutShippingPage checkoutShippingPage;
    CheckoutPaymentMethodPage checkoutPaymentMethodPage;
    CheckoutOrderSummaryPage checkoutOrderSummaryPage;
    CheckoutOrderConfirmationPage checkoutOrderConfirmationPage;

    public CheckoutAuthenticationSteps(BaseUtil base){
        this.base = base;
        checkoutAuthenticationPage = new CheckoutAuthenticationPage(this.base.driver);
        shoppingCartPage = new ShoppingCartPage(this.base.driver);
        checkoutAddressesPage = new CheckoutAddressesPage(this.base.driver);
        checkoutShippingPage = new CheckoutShippingPage(this.base.driver);
        checkoutPaymentMethodPage = new CheckoutPaymentMethodPage(this.base.driver);
        checkoutOrderSummaryPage = new CheckoutOrderSummaryPage(this.base.driver);
        checkoutOrderConfirmationPage = new CheckoutOrderConfirmationPage(this.base.driver);
        BaseUtil.logger = LogManager.getLogger(CheckoutAuthenticationSteps.class.getName());
    }

    @When("User is able to sign in and complete checkout process")
    public void user_is_able_to_sign_in_and_complete_checkout_process(List<String> testDataFile) throws Exception {
        try{
            shoppingCartPage.clickProceedToCheckoutButton();
            List<String> testDataFileName = testDataFile;
            Assert.assertTrue(checkoutAuthenticationPage.isAuthenticationTitleDisplayed());
            signInInCheckoutAuthenticationScreen(testDataFileName.get(1), "credentials");

            Assert.assertTrue(checkoutAddressesPage.isAddressesTitleDisplayed());
            checkoutAddressesPage.clickProceedToCheckoutButton();

            Assert.assertTrue(checkoutShippingPage.isShippingTitleDisplayed());
            checkoutShippingPage.clickTermsAndConditionsCheckbox();
            checkoutShippingPage.clickProceedToCheckoutButton();

            Assert.assertTrue(checkoutPaymentMethodPage.isPaymentTitleDisplayed());
            checkoutPaymentMethodPage.clickPayByBankWireButton();

            Assert.assertTrue(checkoutOrderSummaryPage.isOrderSummaryTitleDisplayed());
            checkoutOrderSummaryPage.clickIConfirmOrderButton();

            Assert.assertTrue(checkoutOrderConfirmationPage.isOrderSummaryTitleDisplayed());
            this.base.order_reference = GeneralUtil.extractOrderReferenceFromText(checkoutOrderConfirmationPage.getOrderSummaryText());

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    //Creating a reusable method to use for sign in in checkout authentication screen
    public void signInInCheckoutAuthenticationScreen(String testDataFile, String jsonObject) {
        try{
            checkoutAuthenticationPage.enterTextToEmailTextbox(GeneralUtil.getJsonObjectFromFile(testDataFile,jsonObject,"username"));
            checkoutAuthenticationPage.enterTextToPasswordTextbox(GeneralUtil.getJsonObjectFromFile(testDataFile,jsonObject,"password"));
            checkoutAuthenticationPage.clickSignInButton();
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }
}
