package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class LoginPage {

    protected WebDriver driver;
    private WaitUtils wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    //Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessageContainer = By.xpath("//div[@class='error-message-container error']/h3");


    //Methods
    private void setUsernameField(String username) {
        wait.waitForElementVisible(usernameField).sendKeys(username);

    }

    private void setPasswordField(String password) {
        wait.waitForElementVisible(passwordField).sendKeys(password);
    }

    private void clickLoginButton() {
        wait.waitForElementVisible(loginButton).click();
    }

    public void setLogin(String username, String password) {
        setUsernameField(username);
        setPasswordField(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        return BaseTest.getDriver().findElement(errorMessageContainer).isDisplayed();
    }

    public String getErrorMessage() {
        if(isErrorMessageDisplayed()) {
            return BaseTest.getDriver().findElement(errorMessageContainer).getText().trim();
        } else {
            return null;
        }
    }


}
