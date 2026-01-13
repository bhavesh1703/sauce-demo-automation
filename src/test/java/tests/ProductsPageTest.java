package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.AssertUtils;
import utils.ConfigReader;
import utils.RetryAnalyzer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductsPageTest extends BaseTest {

    @Test(groups = "regression")
    public void verifyProductCount() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin(ConfigReader.getUsername(), ConfigReader.getPassword());

        ProductsPage productsPage = new ProductsPage(BaseTest.getDriver());

        //Check the Product Count
//        Assert.assertTrue(productsPage.getTotalProductCount() > 0);
        AssertUtils.assertListNotEmpty(productsPage.getAllProductNames());
    }

    @Test(groups = "regression")
    public void verifyProductName() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin(ConfigReader.getUsername(), ConfigReader.getPassword());

        ProductsPage productsPage = new ProductsPage(BaseTest.getDriver());

        AssertUtils.assertListContains(productsPage.getAllProductNames(), ConfigReader.getValue("expectedFirstProduct"));
    }

    @Test(groups = {"smoke", "regression"})
    public void verifyProductFilter() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin(ConfigReader.getUsername(), ConfigReader.getPassword());

        ProductsPage productsPage = new ProductsPage(BaseTest.getDriver());

        //1. Store the prices in List of String (Unsorted, unformatted)
        List<String> priceString = productsPage.getAllPrices();
        List<Double> originalPriceList = new ArrayList<>(); //without filter

        for(String price : priceString) {
//            System.out.println(price);
            originalPriceList.add(Double.parseDouble(price.replace("$", "").trim()));
        }

        //2. Expected sorted list.
        List<Double> expectedSortedPrices = new ArrayList<>(originalPriceList);
        Collections.sort(expectedSortedPrices);

        //3. Apply the filter
        productsPage.applyFilter("Price (low to high)");

        //4. Get the Prices after filter
        List<String> filteredPriceString = productsPage.getAllPrices();
        List<Double> actualFilteredPrice = new ArrayList<>();

        for(String price : filteredPriceString) {
            actualFilteredPrice.add(Double.parseDouble(price.replace("$","").trim()));
        }

        //Validations
        Assert.assertEquals(actualFilteredPrice, expectedSortedPrices);

    }

    @Test(groups = {"smoke" ,"regression"},retryAnalyzer = RetryAnalyzer.class)
    public void verifyAddToCart() {
        LoginPage loginPage = new LoginPage(BaseTest.getDriver());
        loginPage.setLogin(ConfigReader.getUsername(), ConfigReader.getPassword());

        ProductsPage productsPage = new ProductsPage(BaseTest.getDriver());
        productsPage.addProductToCart("Sauce Labs Bolt T-Shirt");
        productsPage.clickOnCart();

        CartPage cartPage = new CartPage(BaseTest.getDriver());
        System.out.println(cartPage.getTotalItemsInCart());

        Assert.assertEquals(cartPage.getTotalItemsInCart(), 1);

    }

    @Test(groups = "regression")
    public void verifyDescriptionOfProduct() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin(ConfigReader.getUsername(), ConfigReader.getPassword());

        ProductsPage productsPage = new ProductsPage(BaseTest.getDriver());

        List<String> actualDesc = productsPage.getAllDescriptions();

        AssertUtils.assertListNotEmpty(actualDesc);
        Assert.assertEquals(actualDesc.size(), productsPage.getTotalProductCount());
    }

}
