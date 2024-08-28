package Utilities;

import io.appium.java_client.AppiumDriver;

public class AppiumDriverClass {
	
	private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
	
	
	public static AppiumDriver getDriver()
	{
		return driver.get();
	}
	
	public static void setDriver(AppiumDriver Driver)
	{
		driver.set(Driver);
		
	}

}
