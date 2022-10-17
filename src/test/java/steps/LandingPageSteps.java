package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import pageObjects.AccountPage;
import pageObjects.LandingPage;
import pageObjects.OrderHistoryPage;
import pageObjects.SearchResultsPage;
import pojos.Product_Properties;
import util.BaseUtil;
import util.GeneralUtil;

import java.text.DecimalFormat;
import java.util.List;

import static util.GeneralUtil.formatPriceToTwoDecimalPlaces;

public class LandingPageSteps extends BaseUtil {

    private BaseUtil base;
    LandingPage landingPage;
    SearchResultsPage searchResultsPage;
    AccountPage accountPage;
    OrderHistoryPage orderHistoryPage;

    public LandingPageSteps(BaseUtil base){
        this.base = base;
        landingPage = new LandingPage(this.base.driver);
        searchResultsPage = new SearchResultsPage(this.base.driver);
        accountPage = new AccountPage(this.base.driver);
        orderHistoryPage = new OrderHistoryPage(this.base.driver);
        BaseUtil.logger = LogManager.getLogger(LandingPageSteps.class.getName());
    }

    @Given("User is in landing page of MyStore website")
    public void user_is_in_landing_page_of_my_store_website() throws Exception {
        try{
            Assert.assertTrue(landingPage.isCompanyLogoDisplayed(),"User is not in landing page");

            //verify that shopping cart is empty and user has not logged in
            Assert.assertEquals(landingPage.getShoppingCardQuantity(), "0");
            Assert.assertTrue(landingPage.isSignInButtonDisplayed(),"Sign in button is not displayed");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("User searches and adds single product from each criteria to shopping card")
    public void user_searches_and_adds_following_products_to_shopping_card(List<String> searchCriteria) throws Exception{
        try{
            List<String> listOfData = searchCriteria;

            //verify user is in search results page for each search criteria and extract product name and price from first record in search results page
            for(int i=1;i<searchCriteria.size();i++) {
                landingPage.enterTextToSearchBar(listOfData.get(i));
                landingPage.clickSubmitSearchButton();

                Assert.assertTrue(searchResultsPage.isSearchResultsPageDisplayed(), "User is not in search results page");

                Assert.assertEquals(searchResultsPage.getSearchedTextCriteiraQuantity().trim(), "\"" + listOfData.get(i) + "\"");

                //Create a properties pojo object with product name, price and quantity
                Product_Properties product_properties = new Product_Properties();
                product_properties.setProductName(searchResultsPage.getNameOfNthSearchResults(1).trim());
                product_properties.setProductPrice(Double.parseDouble(GeneralUtil.removeCurrencySymbol(searchResultsPage.getPriceOfNthSearchResults(1).trim())));
                this.base.total_price = total_price + Double.parseDouble(GeneralUtil.removeCurrencySymbol(searchResultsPage.getPriceOfNthSearchResults(1).trim()));
                product_properties.setProductQty(1);
                //Setting total price in the base class so that it can be shared across classes
                this.base.products.add(product_properties);

                searchResultsPage.addNthProductToCartFromSearchResults(1);

                Assert.assertTrue(landingPage.isProductSuccessfullyAddedToCartPopUpDisplayed(), "Product successfully added to cart pop up is not displayed");

                landingPage.clickProductAddedToCartCloseButton();
            }
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Then("User is able to view added {int} products by hovering over the shopping cart")
    public void user_is_able_to_view_added_products_by_hovering_over_the_shopping_cart(int numberOfProductsInCart) throws Exception{
        try{
            //validating number of products displayed in shopping cart on top
            Assert.assertEquals(landingPage.getShoppingCardQuantity(), String.valueOf(this.base.products.size()));
            landingPage.hoverOverShoppingCart();
            //validating number of products displayed in panel after hovering over shopping cart
            Assert.assertEquals(landingPage.getNumberOfItemsInTheCart(), numberOfProductsInCart);

            //validate each item in the card
            for(int i=1;i<=numberOfProductsInCart;i++) {
                Assert.assertEquals(Integer.parseInt(landingPage.getQuantityOfNthItemsInShoppingCard(i).trim()), this.base.products.get(i-1).getProductQty());
                Assert.assertEquals(landingPage.getNameOfNthItemsInShoppingCard(i).trim(), this.base.products.get(i-1).getProductName());
                Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(landingPage.getPriceOfNthItemsInShoppingCard(i).trim())), this.base.products.get(i-1).getProductPrice());
            }

            //calculating shipping cost and total include shipping
            double shipping_price = Double.parseDouble(GeneralUtil.removeCurrencySymbol(landingPage.getShippingPriceInShoppingCard().trim()));
            double price_with_shipping = this.base.total_price + shipping_price;
            this.base.total_price = formatPriceToTwoDecimalPlaces(this.base.total_price);

            //validating price in cart is equal to products added to cart
            Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(landingPage.getTotalPriceInShoppingCard())), formatPriceToTwoDecimalPlaces(price_with_shipping), "Total price displayed in the cart is not correct");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("User is able to navigate to order history and details page and see order with reference number is shown as first record")
    public void user_is_able_to_navigate_to_order_history_and_details_page_and_see_order_with_reference_number_is_shown_as_first_record() throws Exception {
        try{
            landingPage.clickOnAccountButton();

            Assert.assertTrue(accountPage.isAccountTitleDisplayed());

            accountPage.clickOrderHistoryAndDetailsButton();
            //validating if latest order refence is the one that user has completed just now
            Assert.assertEquals(orderHistoryPage.getOrderReferenceOfNthItem(2), this.base.order_reference, "Order reference mismatch");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("User is able to logout from application")
    public void user_is_able_to_logout_from_application() throws Exception {
        try{
            landingPage.clickOnSignOutButton();

            Assert.assertTrue(landingPage.isSignInButtonDisplayed());
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}
