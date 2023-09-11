package TestListeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import AndroidBase.AndroidMobileBase;
import Utilities.ExtentManager;
import Utilities.Utils;
import io.appium.java_client.AppiumDriver;

public class AndroidListeners implements ITestListener{

	ExtentReports extent;
	ExtentTest extentTest;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	Utils util = new Utils();
	AppiumDriver driver;
	
	@Override
	public void onTestStart(ITestResult result) {
	
		
		extent = ExtentManager.getInstance();
		extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	
		test.get().pass("TEST PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		try {
			//driver =  (AppiumDriver) result.getTestClass().getRealClass().get(AndroidMobileBase.getDriver());
			Object obj = result.getInstance();
			driver = ((AndroidMobileBase)obj).getDriver();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		String screenshotPath = util.captureScreenshot(driver);
		test.get().fail(result.getThrowable(),
				MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath,result.getMethod().getMethodName()).build());

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		
		ExtentManager.createReport();
	}

	@Override
	public void onFinish(ITestContext context) {

		extent.flush();
	}
	
	

}
