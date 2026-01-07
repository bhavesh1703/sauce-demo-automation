package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.CommonActions;

import java.util.List;

public class CartPage {

    protected WebDriver driver;
    private CommonActions comm;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.comm = new CommonActions(driver);
    }


    private By checkOutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By cartItems = By.xpath("//div[@class='cart_item_label']");



    public String getCurrentUrl() {
        return comm.getCurrentUrl();
    }

    public boolean isCheckOutButtonDisplayed() {
        return comm.isElementDisplayed(checkOutButton);
    }

    public boolean isContinueShoppingButtonDisplayed() {
        return comm.isElementDisplayed(continueShoppingButton);
    }

    public void clickCheckOut() {
        comm.clickElement(checkOutButton);
    }

    public void clickContinueShop() {
        comm.clickElement(continueShoppingButton);
    }

    public int getTotalItemsInCart() {
        List<WebElement> items = BaseTest.getDriver().findElements(cartItems);
        return items.size();
    }
}
