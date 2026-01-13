package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.CommonActions;
import utils.WaitUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage {

    protected WebDriver driver;
    private CommonActions comm;
    private WaitUtils wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.comm = new CommonActions(driver);
        this.wait = new WaitUtils(driver);
    }

    private By productPageHeader = By.xpath("//span[.='Products']");
    private By productNameLocator = By.className("inventory_item_name");
    private By productPriceLocator = By.className("inventory_item_price");
    private By filterLocator = By.className("product_sort_container");
    private By cartIcon = By.id("shopping_cart_container");
    private By productDescLocator = By.xpath("//div[@class='inventory_item_desc']");

    public boolean isProductPageDisplayed() {
        return BaseTest.getDriver().findElement(productPageHeader).isDisplayed();

    }

    public List<String> getAllProductNames() {
        List<WebElement> products = driver.findElements(productNameLocator);
        List<String> productNames = new ArrayList<>();

        for(WebElement product : products) {
            String text = product.getText().trim();
            productNames.add(text);
        }
        return productNames;
    }

    public List<String> getAllPrices() {
        List<WebElement> prices = BaseTest.getDriver().findElements(productPriceLocator);
        List<String> productPrices = new ArrayList<>();

        for(WebElement price : prices) {
            String text = price.getText().trim();
            productPrices.add(text);
        }
        return productPrices;
    }

    public List<String> getAllDescriptions() {
        List<WebElement> descriptions = BaseTest.getDriver().findElements(productDescLocator);
        List<String> productDescs = new ArrayList<>();

        for(WebElement desc : descriptions) {
            String text = desc.getText().trim();
            productDescs.add(text);
        }
        return productDescs;
    }

    public int getTotalProductCount() {
        List<WebElement> products = BaseTest.getDriver().findElements(productNameLocator);
        return products.size();
    }

    public void applyFilter(String option) {
        WebElement dropdown = BaseTest.getDriver().findElement(filterLocator);
        dropdown.click();
        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    public void addProductToCart(String productName) {
        String targetProduct = productName.toLowerCase().replaceAll(" ", "-").trim();

        By addCartBtn = By.name("add-to-cart-" + targetProduct);
        System.out.println(addCartBtn);
        WebElement button = wait.waitForElementVisible(addCartBtn);
        System.out.println("Button found - :" + button.isDisplayed());
        button.click();
        }

    public void clickOnCart() {
        wait.waitForElementVisible(cartIcon);
        comm.clickElement(cartIcon);
    }

}


