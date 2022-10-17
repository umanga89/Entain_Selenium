package steps;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import pageObjects.LandingPage;
import pageObjects.SearchResultsPage;
import pageObjects.ShoppingCartPage;
import pojos.Product_Properties;
import util.BaseUtil;
import util.GeneralUtil;

import java.text.DecimalFormat;

import static util.GeneralUtil.formatPriceToTwoDecimalPlaces;

public class ShoppingCartSteps extends BaseUtil{

    private BaseUtil base;
    LandingPage landingPage;
    SearchResultsPage searchResultsPage;
    ShoppingCartPage shoppingCartPage;

    public ShoppingCartSteps(BaseUtil base){
        this.base = base;
        landingPage = new LandingPage(this.base.driver);
        searchResultsPage = new SearchResultsPage(this.base.driver);
        shoppingCartPage = new ShoppingCartPage(this.base.driver);
        BaseUtil.logger = LogManager.getLogger(ShoppingCartSteps.class.getName());
    }

    @Then("User is able to see added {int} products with their prices upon navigating to shopping cart")
    public void user_is_able_to_see_added_products_with_their_prices_upon_navigating_to_shopping_cart(int numberOfProducts) throws Exception {
        try{

            landingPage.clickOnShoppingCartIconToNavigateToShoppingCart();
            Assert.assertTrue(shoppingCartPage.isShoppingCartTitleDisplayed());
            Assert.assertEquals(shoppingCartPage.getNumberOfItemsInShoppingCart(), numberOfProducts, "Number of items in shopping cart does not match with expected items");

            for(int i=1;i==numberOfProducts;i++) {
                validateNthItemInShoppingCart(this.base.products.get(i-1), i, shoppingCartPage);
            }

            validateShoppingCartFields();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Then("User is able to increase quantity of item {int} by {int} in the cart and adjust the shopping cart accordingly")
    public void user_is_able_to_increase_quantity_of_item_in_the_cart_and_adjust_the_shopping_cart_accordingly(int itemNo,int qty ) throws Exception{
        try{
            int currentQtyOfItem = Integer.parseInt(shoppingCartPage.getProductQuantityOfNthItemInShoppingCart(itemNo));
            int newQty=0;
            for(int i=0;i<qty;i++) {
                shoppingCartPage.clickQtyUpButtonOfNthItemInShoppingCart(itemNo);
                newQty = qty+currentQtyOfItem;
                this.base.products.get(itemNo-1).setProductQty(newQty);
                this.base.total_price = this.base.total_price + (this.base.products.get(itemNo-1).getProductPrice() * qty);
                Assert.assertTrue(shoppingCartPage.waitUntilQuantityIfProductIsUpdatedInShoppingCart(itemNo, newQty));
            }

            validateNthItemInShoppingCart(this.base.products.get(itemNo-1), itemNo, shoppingCartPage);

            validateShoppingCartFields();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Then("User is able to decrease quantity of item {int} by {int} in the cart and adjust the shopping cart accordingly")
    public void user_is_able_to_decrease_quantity_of_item_by_in_the_cart_and_adjust_the_shopping_cart_accordingly(int itemNo,int qty ) throws Exception {
        try{
            int currentQtyOfItem = Integer.parseInt(shoppingCartPage.getProductQuantityOfNthItemInShoppingCart(itemNo));
            int newQty=0;
            for(int i=0;i<qty;i++) {
                shoppingCartPage.clickQtyDownButtonOfNthItemInShoppingCart(itemNo);
                newQty = currentQtyOfItem - qty;
                this.base.products.get(itemNo-1).setProductQty(newQty);
                this.base.total_price = this.base.total_price - (this.base.products.get(itemNo-1).getProductPrice() * qty);
                Assert.assertTrue(shoppingCartPage.waitUntilQuantityIfProductIsUpdatedInShoppingCart(itemNo, newQty));
            }

            validateNthItemInShoppingCart(this.base.products.get(itemNo-1), itemNo, shoppingCartPage);

            validateShoppingCartFields();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Then("User is able to remove item {int} from the cart and adjust the shopping cart accordingly")
    public void user_is_able_to_remove_item_from_the_cart_and_adjust_the_shopping_cart_accordingly(int itemNo) throws Exception {
        try{
            shoppingCartPage.clickRemoveButtonOfNthItemInShoppingCart(itemNo);

            this.base.total_price = formatPriceToTwoDecimalPlaces((this.base.total_price - this.base.products.get(itemNo-1).getProductPrice()));
            this.base.products.remove(itemNo);

            Assert.assertTrue(shoppingCartPage.waitUntilDeletedItemIsRemovedFromShoppingCart( this.base.products.get(itemNo-1).getProductName()));

            validateShoppingCartFields();

        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void validateNthItemInShoppingCart(Product_Properties product, int item, ShoppingCartPage shoppingCartPageObj) {
        try{
            Assert.assertEquals(shoppingCartPageObj.getProductNameOfNthItemInShoppingCart(item), product.getProductName(), "Product name mismatch");
            Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPageObj.getUnitPriceOfNthItemsInShoppingCard(item))), product.getProductPrice(), "Product unit price mismatch");
            Assert.assertEquals(Integer.parseInt(shoppingCartPageObj.getProductQuantityOfNthItemInShoppingCart(item)), product.getProductQty(), "Product quantity mismatch");

            double totalProductPrice = product.getProductPrice() * product.getProductQty();

            Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPageObj.getProductItemTotalOfNthItemInShoppingCart(item))), totalProductPrice, "Product total price mismatch");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }

    public void validateShoppingCartFields() {
        try{
            Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPage.getTotalOfProductsInCart())),this.base.total_price,"Mismatch in total of products in cart with calculated total");

            double total_shipping = Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPage.getTotalOfShippingInCart()));
            double total_with_shipping = total_shipping + this.base.total_price;

            Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPage.getTotalWithShippingInCart())), formatPriceToTwoDecimalPlaces(total_with_shipping), "Mismatch in total with shipping");

            double total_tax = Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPage.getTotalTaxInCart()));
            double total_with_tax = total_tax + total_with_shipping;

            Assert.assertEquals(Double.parseDouble(GeneralUtil.removeCurrencySymbol(shoppingCartPage.getTotalPriceInCart())), formatPriceToTwoDecimalPlaces(total_with_tax), "Mismatch in total with tax");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }
}
