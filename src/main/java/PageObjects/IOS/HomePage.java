package PageObjects.IOS;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import Utilities.IOS.IOSMobileActions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends IOSMobileActions{
	
	IOSDriver driver;
	
	public HomePage(IOSDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@iOSXCUITFindBy(accessibility = "Alert Views")
	private WebElement alertViewButton;
	
	public AlertViewPage clickOnAlerViewOption()
	{
		waitForElementToBeClickable(alertViewButton).click();
		return new AlertViewPage(driver);
	}
	

}
