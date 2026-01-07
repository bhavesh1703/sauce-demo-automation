package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;



public class LoginSmokeTest extends BaseTest {

    @Test
    public void verifyLoginPageTitle() {
        Assert.assertEquals(BaseTest.getDriver().getTitle(), "Swag Labs", "Title not matched.");
        System.out.println("Page Title: " + BaseTest.getDriver().getTitle());
    }
}
