package Utilities.Android;

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

public class AndroidMobileActions extends Utils{

	private AndroidDriver driver;
	private JavascriptExecutor js;

	public AndroidMobileActions(AppiumDriver driver) {
		super(driver);
		this.driver = (AndroidDriver) driver;
		js = (JavascriptExecutor) this.driver;
	}

	public void longPressAction(WebElement ele) {
		js.executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));

	}

	public void scrollByScreenSize() {
		js.executeScript("mobile: scrollGesture", ImmutableMap.of("left", 100, "top", 100, "width", 200, "height", 200,
				"direction", "down", "percent", 10.0));
	}

	public void scrollToElement(String text) {
		this.driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"))"));
	}

	public void swipeActionByElement(WebElement ele) {
		js.executeScript("mobile: swipeGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "direction", "left", "percent", 0.75));
	}
	
	public void dragDropAction(WebElement ele, int dropXcoordinate, int dropYcoordinate) {
		js.executeScript("mobile: dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "endX",
				dropXcoordinate, "endY", dropYcoordinate));
	}


}
