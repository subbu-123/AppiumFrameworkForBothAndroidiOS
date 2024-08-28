package PageObjects.Android;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Android.AndroidMobileActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.SupportsContextSwitching;

public class CartPage extends AndroidMobileActions {

	AppiumDriver driver;

	public CartPage(AppiumDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);

	}
	
	@AndroidFindBy(xpath = "//*[contains(@resource-id,'totalAmountLbl')]")
	private WebElement totalAmountField;
	
	@AndroidFindBy(xpath = "//android.widget.CheckBox")
	private WebElement checkBox;
	
	@AndroidFindBy(xpath = "//*[contains(@resource-id,'termsButton')]")
	private WebElement termsButton;
	
	@AndroidFindBy(xpath = "//*[contains(@text,'CLOSE')]")
	private WebElement closeButton;
	
	@AndroidFindBy(xpath = "//*[contains(@resource-id,'btnProceed')]")
	private WebElement proceedButton;
	
	public double getCartTotalAmount()
	{
		String totalAmountText = totalAmountField.getText().replace("$", "");
		double totalAmount = Double.parseDouble(totalAmountText);
		return totalAmount;
	}
	
	public void clickOnCheckBox()
	{
		waitForElementToBeClickable(checkBox).click();
	}
	
	public void clickAndValidateTermsConditions()
	{
		longPressAction(termsButton);
		waitForElementToBeClickable(closeButton).click();
	}
	
	public chromeWebViewPage clickOnProceedButton()
	{
		waitForElementToBeClickable(proceedButton).click();
		staticWait(3000);
		Set<String> handles = ((SupportsContextSwitching) this.driver).getContextHandles();
		handles.forEach(handle -> {
			if(handle.contains("WEBVIEW"))
			{
				((SupportsContextSwitching) this.driver).context(handle);
			}
		});
		return new chromeWebViewPage(this.driver);
	}
	
	

}
