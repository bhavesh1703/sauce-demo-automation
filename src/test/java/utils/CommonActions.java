package utils;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonActions {

    protected WebDriver driver;
    private WaitUtils wait;

    public CommonActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(BaseTest.getDriver());
    }

    public void clickElement(By element) {
        BaseTest.getDriver().findElement(element).click();
    }

    public boolean isElementDisplayed(By element) {
        return BaseTest.getDriver().findElement(element).isDisplayed();
    }

    public void enterInput(By element, String input) {
        WebElement inputField = BaseTest.getDriver().findElement(element);
        inputField.clear();
        inputField.sendKeys(input);
    }

    public String getCurrentUrl() {
      return BaseTest.getDriver().getCurrentUrl();
    }

    public String getCurrentTitle() {
        return BaseTest.getDriver().getTitle();
    }

}
