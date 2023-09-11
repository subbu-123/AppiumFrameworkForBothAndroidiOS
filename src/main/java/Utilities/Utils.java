package Utilities;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Utils {
	
	AppiumDriver driver;
	WebDriver wdriver;
	public WebDriverWait wait;
	
	public Utils(AppiumDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
	}
	
	public Utils(WebDriver driver) {

		this.wdriver = driver;
		wait = new WebDriverWait(this.wdriver, Duration.ofSeconds(30));
	}
	
	public Utils()
	{
		
	}
	
	
	
	public WebElement waitForElementToBeVisible(WebElement ele)
	{
		return wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public WebElement waitForElementToBeClickable(WebElement ele)
	{
		return wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	public void staticWait(long waitValue)
	{
		try {
			Thread.sleep(waitValue);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AppiumDriverLocalService appiumServerInitialization()
	{
		AppiumDriverLocalService service;
		service = new AppiumServiceBuilder()
				.usingDriverExecutable(new File("/usr/local/bin/node"))
				.withAppiumJS(new File("/usr/local/lib/node_modules/appium"))
				.usingAnyFreePort()
				.withIPAddress("127.0.0.1").withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withArgument(GeneralServerFlag.LOG_LEVEL, "error").build();

		return service;
	}
	
	public List<Map<String,Object>> getJsondata(String path)
	{
		String JsonContent = null;
		List<Map<String,Object>> data = null;
		try {
			JsonContent = FileUtils.readFileToString(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			data = mapper.readValue(JsonContent, new TypeReference<List<Map<String,Object>>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	public String captureScreenshot(AppiumDriver driver)
	{
		String screenshot = driver.getScreenshotAs(OutputType.BASE64);
		return screenshot;
	}
	
	
}
