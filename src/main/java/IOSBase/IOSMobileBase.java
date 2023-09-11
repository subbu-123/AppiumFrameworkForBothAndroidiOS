package IOSBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import Utilities.Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class IOSMobileBase {
	
	
	public static ThreadLocal<IOSDriver> driver = new ThreadLocal<IOSDriver>();
	AppiumDriverLocalService service;
	Utils util = new Utils();
	FileInputStream fis;
	Properties prop = new Properties();
	
	
	@BeforeSuite
	public void startAppiumServer()
	{
		service = util.appiumServerInitialization();
		service.start();
		
	}
	
	@AfterSuite
	public void stopAppiumServer()
	{
		service.stop();
		
	}
	
	@BeforeMethod
	@Parameters({"deviceName","platformName","platformVersion"})
	public void driverInitialization(String deviceName, String platformName, String platformVersion) throws IOException
	{
		fis = new FileInputStream(new File(System.getProperty("user.dir") + "/globalConfig.properties"));
		prop.load(fis);
		String appName = prop.getProperty("IosAppName");
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.NO_RESET, false);
		cap.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
		cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/Resources/" + appName);

		driver.set(new IOSDriver(service.getUrl(), cap));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
	
	@AfterMethod
	public void driverTearDown()
	{
		//driver.closeApp();
		getDriver().quit();
	}
	
	
	public static IOSDriver getDriver()
	{
		return driver.get();
	}
	
	

}
