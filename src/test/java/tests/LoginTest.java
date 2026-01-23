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
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Assert.assertTrue(products.isProductPageDisplayed(), "Product Page is not displayed.");
    }

    @Test(groups = "regression")
    public void verifyLoginPageTitle() {
        Assert.assertEquals(BaseTest.getDriver().getTitle(), "Swag Labs", "Title not matched.");
        System.out.println("Page Title: " + BaseTest.getDriver().getTitle());
    }

    @Test(groups = "regression")
    public void verifyInvalidLogin() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin("Invalid", "Wrong");

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //Validations
        Assert.assertTrue(login.isErrorMessageDisplayed());
        Assert.assertEquals(login.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service");

        login.clickErrorCloseButton();
    }

    @Test(groups = "smoke")
    public void verifyBlankUsernameField() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
//        if(login.isErrorMessageDisplayed()) {
//            System.out.println("Error is displayed.");
//        } else {
//            System.out.println("Error is not displayed.");
//        }
//        Assert.assertTrue(login.isErrorMessageDisplayed());
        login.setLogin("", ConfigReader.getPassword());

        //Validations
        Assert.assertTrue(login.isErrorMessageDisplayed());
        Assert.assertEquals(login.getErrorMessage(), "Epic sadface: Username is required");

        login.clickErrorCloseButton();
    }

    @Test(groups = "regression")
    public void verifyBlankPasswordField() {
        LoginPage login = new LoginPage(BaseTest.getDriver());
        login.setLogin(ConfigReader.getUsername(),"" );

        //Validations
        Assert.assertTrue(login.isErrorMessageDisplayed());
        Assert.assertEquals(login.getErrorMessage(), "Epic sadface: Password is required");

        login.clickErrorCloseButton();
    }

}
