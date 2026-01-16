package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtils;

import java.io.File;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
//        String suiteName = context.getSuite().getName();
//        String timeStamp = String.valueOf(System.currentTimeMillis());
        String reportDirPath = System.getProperty("user.dir")
                            + File.separator + "target"
                            + File.separator + "extent-report";

        File reportDir = new File(reportDirPath);
        reportDir.mkdirs();

        String reportPath = reportDirPath + File.separator + "ExtentReport.html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass(result.getName() + ": Test Passed.");
//        String screenshotPath = ScreenshotUtils.takeScreenshot(BaseTest.getDriver(), result.getName());
//        extentTest.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtils.takeScreenshot(BaseTest.getDriver(), result.getName());
        extentTest.get().fail(result.getThrowable());
        extentTest.get().addScreenCaptureFromPath(screenshotPath);

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip(result.getName() + ": Test Failed.");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        extentTest.remove();
    }
}
