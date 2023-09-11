package PageObjects.IOS;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import Utilities.IOS.IOSMobileActions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AlertViewPage extends IOSMobileActions{
	
	IOSDriver driver;
	
	public AlertViewPage(IOSDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'Okay / Cancel'`]")
	private WebElement okayCancelButton;
	
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' AND value contains 'Title'")
	private WebElement popupTitle;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeScrollView[2]/**/XCUIElementTypeButton[`label == 'OK'`]")
	private WebElement okayButton;

	public void clickOkCancelButton()
	{
		waitForElementToBeClickable(okayCancelButton).click();
	}
	
	public String alertPopupTitle()
	{
		return popupTitle.getText();
	}
	
	public void clickOkButtonOnPopup()
	{
		waitForElementToBeClickable(okayButton).click();
	}
	
	
	
	
	
}
