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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginTest extends BaseTest {

    @Test(groups = "smoke", retryAnalyzer = RetryAnalyzer.class)
    public void verifyValidLogin() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        System.out.println("Driver is : " + BaseTest.getDriver());
        login.setLogin("standard_user", "secret_sauce");

        //Validations
        Assert.assertEquals(BaseTest.getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Login Navigated to wrong page");


        ProductsPage products = new ProductsPage(BaseTest.getDriver());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(products.isProductPageDisplayed(), "Product Page is not displayed.");
    }

    @Test(groups = "smoke")
    public void verifyInvalidLogin() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin("Invalid", "Wrong");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Validations
        Assert.assertTrue(login.isErrorMessageDisplayed());
        Assert.assertEquals(login.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service");
    }

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

    @Test(groups = "regression")
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

    @Test(groups = "smoke", retryAnalyzer = RetryAnalyzer.class)
    public void verifyAddToCart() {
        LoginPage loginPage = new LoginPage(BaseTest.getDriver());
        loginPage.setLogin(ConfigReader.getUsername(), ConfigReader.getPassword());

        ProductsPage productsPage = new ProductsPage(BaseTest.getDriver());
        productsPage.addProductToCart("Sauce Labs Bolt T-Shirt");
        productsPage.clickOnCart();

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        CartPage cartPage = new CartPage(BaseTest.getDriver());
        System.out.println(cartPage.getTotalItemsInCart());

        Assert.assertEquals(cartPage.getTotalItemsInCart(), 1);

    }
}
