package Utilities.IOS;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import Utilities.Utils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class IOSMobileActions extends Utils{

	private IOSDriver driver;
	private JavascriptExecutor js;

	public IOSMobileActions(AppiumDriver driver) {
		super(driver);
		this.driver = (IOSDriver) driver;
		js = (JavascriptExecutor) this.driver;
	}
	

	public void longPressAction(WebElement ele) {
		js.executeScript("mobile: touchAndHold",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2.0));

	}

	public void scrollToElement(String predicateString) {
		js.executeScript("mobile: scroll",
				ImmutableMap.of("direction", "down", "predicateString", predicateString));
	}

	public void swipeActionByElement(WebElement ele) {
		js.executeScript("mobile: swipe",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "direction", "left" ));
	}

	public void dragDropAction(WebElement ele, int dropXcoordinate, int dropYcoordinate) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("duration", 1.0);
		params.put("fromX", 100);
		params.put("fromY", 100);
		params.put("toX", dropXcoordinate);
		params.put("toY", dropYcoordinate);
		params.put("element", ((RemoteWebElement) ele).getId());
		js.executeScript("mobile: dragFromToForDuration", params);
		
	}

}
