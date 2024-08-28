package AndroidBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.net.*;

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

import Utilities.AppiumDriverClass;
import Utilities.Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AndroidMobileBase {

	AppiumDriverLocalService service;
	UiAutomator2Options options;
	FileInputStream fis;
	Properties prop = new Properties();
	Utils util = new Utils();

	@BeforeSuite(enabled = true)
	public void startAppiumServer() throws IOException {
		service = util.appiumServerInitialization();
		service.start();
		System.out.println("Appium driver started");
	}

	@AfterSuite(enabled = true)
	public void stopAppiumServer() {
		service.stop();
		System.out.println("Appium driver stopped");

	}

	@BeforeMethod
	@Parameters({ "deviceName", "platformName" })
	public void settingCapabilities(String deviceName, String platformName) throws IOException {
		fis = new FileInputStream(new File(System.getProperty("user.dir") + "/globalConfig.properties"));
		prop.load(fis);
		String appName = prop.getProperty("AndroidAppName");

		options = new UiAutomator2Options();
		options.setPlatformName(platformName).setAutomationName("uiautomator2").setDeviceName(deviceName)
				.setNoReset(false).setAutoGrantPermissions(true);

		if (prop.getProperty("appType").equals("Native")) {
			options.setApp(System.getProperty("user.dir") + "/Resources/" + appName);
		}

		else if (prop.getProperty("appType").equals("Hybrid")) {
			options.setApp(System.getProperty("user.dir") + "/Resources/" + appName)
			.setChromedriverUseSystemExecutable(false);
		}

		else if (prop.getProperty("appType").equals("Mobile Web")) {
			options.withBrowserName("chrome");
		} else {
			System.err.println("Please provide a correct app type in the global config file");
		}

		AppiumDriverClass.setDriver(new AndroidDriver(service.getUrl(), options));
		AppiumDriverClass.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterMethod
	public void driverTearDown() {
		AppiumDriverClass.getDriver().quit();
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

		// System.out.println("Starting emulator for '" + nameOfAVD + "' ...");
		System.out.println("Starting emulator for " + deviceName + " ...");
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

}
