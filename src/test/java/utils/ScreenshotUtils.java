package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String takeScreenshot(WebDriver driver, String testName) {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date());

        String filename = testName + "_" + timeStamp + ".png";

        String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/";
        String destPath = screenshotDir + filename;

        try{
            File directory = new File(screenshotDir);
            if(!directory.exists()) {
                directory.mkdirs();
            }
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(destPath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "screenshots/" + filename;

    }
}