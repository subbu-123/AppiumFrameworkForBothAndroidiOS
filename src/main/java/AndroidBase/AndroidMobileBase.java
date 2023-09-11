package AndroidBase;

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
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AndroidMobileBase {
	
	private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<AndroidDriver>();
	AppiumDriverLocalService service;
	DesiredCapabilities cap;
	FileInputStream fis;
	Properties prop = new Properties();
	Utils util = new Utils();
	
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
	@Parameters({"deviceName","platformName"})
	public void settingCapabilities(String deviceName, String platformName) throws IOException
	{
		fis = new FileInputStream(new File(System.getProperty("user.dir") + "/globalConfig.properties"));
		prop.load(fis);
		String appName = prop.getProperty("AndroidAppName");
		
		cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.NO_RESET, false);
		cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		
		if(prop.getProperty("appType").equals("Native"))
		{
			cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/Resources/" + appName);
		}
		
		else if(prop.getProperty("appType").equals("Hybrid"))
		{
			cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/Resources/" + appName);
			cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_USE_SYSTEM_EXECUTABLE, true);
		}
		
		else if(prop.getProperty("appType").equals("Mobile Web"))
		{
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
		}
		else
		{
			System.err.println("Please provide a correct app type in the global config file");
		}
		
		setDriver(new AndroidDriver(service.getUrl(), cap));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@AfterMethod
	public void driverTearDown()
	{
		getDriver().closeApp();
		getDriver().quit();
	}
	
	/*
	 * @BeforeMethod public void driverInitialization() {
	 * 
	 * }
	 */
	
	/*
	 * @AfterMethod public void driverTearDown() { getDriver().closeApp();
	 * getDriver().quit(); }
	 */
	
	@BeforeSuite(enabled = true)
	@Parameters("deviceName")
	public void launchEmulator(String deviceName) throws IOException {

		fis = new FileInputStream(new File(System.getProperty("user.dir") + "/globalConfig.properties"));
		prop.load(fis);
		
		String sdkPath = prop.getProperty("sdkPath");
		String emulatorPath = sdkPath + "emulator" + File.separator + "emulator";

		//System.out.println("Starting emulator for '" + nameOfAVD + "' ...");
		System.out.println("Starting emulator for 'Pixel 5' ...");
		String[] aCommand = new String[] { emulatorPath, "-avd", deviceName.replace(" ", "_") };
		try {

			Process process = new ProcessBuilder(aCommand).start();
			process.waitFor(60, TimeUnit.SECONDS);
			System.out.println("Emulator launched successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite(enabled = true)
	public void closeEmulator() throws IOException {

		fis = new FileInputStream(new File(System.getProperty("user.dir") + "/globalConfig.properties"));
		prop.load(fis);
		
		String sdkPath = prop.getProperty("sdkPath");
		String adbPath = sdkPath + "platform-tools" + File.separator + "adb";
		System.out.println("Killing emulator...");
		String[] aCommand = new String[] { adbPath, "emu", "kill" };
		try {
			Process process = new ProcessBuilder(aCommand).start();
			process.waitFor(30, TimeUnit.SECONDS);
			System.out.println("Emulator closed successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static AndroidDriver getDriver()
	{
		return driver.get();
	}
	
	public void setDriver(AndroidDriver driver)
	{
		this.driver.set(driver);
	}
	
}
