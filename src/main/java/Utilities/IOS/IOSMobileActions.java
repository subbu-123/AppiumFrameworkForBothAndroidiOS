package Utilities.IOS;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableList;
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

	public void swipeScrollActionByScreenSize(String direction, double scrollRatio, Duration duration) {

		Dimension size = driver.manage().window().getSize();
		Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
		int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
		int top = midPoint.y - (int) (midPoint.y * scrollRatio);
		int left = midPoint.x - (int) (midPoint.x * scrollRatio);
		int right = midPoint.x + (int) (midPoint.x * scrollRatio);
		
		Point start;
		Point end;
		
		if (direction.equalsIgnoreCase("UP")) {
			start = new Point(midPoint.x, top);
			end = new Point(midPoint.x, bottom);
		} else if (direction.equalsIgnoreCase("DOWN")) {
			start = new Point(midPoint.x, bottom);
			end = new Point(midPoint.x, top);
		} else if (direction.equalsIgnoreCase("LEFT")) {
			start = new Point(left, midPoint.y); 
			end = new Point(right, midPoint.y);
		} else if (direction.equalsIgnoreCase("RIGHT")) {
			start = new Point(right, midPoint.y);
			end = new Point(left, midPoint.y);
		} else {
			throw new Error("Scroll direction should be left right up or down");
		}

		PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence seq = new Sequence(input, 0);
        seq.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        seq.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        seq.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        seq.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(ImmutableList.of(seq));
		
		
	}
}
