package PageObjects.Android;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Android.AndroidMobileActions;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RegisterPage extends AndroidMobileActions {

	AppiumDriver driver;

	public RegisterPage(AppiumDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);

	}

	@AndroidFindBy(xpath = "//android.widget.Spinner")
	private WebElement countryDropdown;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement letShopBtn;

	@AndroidFindBy(xpath = "//android.widget.Toast")
	private WebElement toastMessage;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement nameField;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	private WebElement FemaleRadioButton;

	public void clickOnCountryDropdown() {
		waitForElementToBeVisible(countryDropdown).click();
	}

	public void selectCountry(String name) {
		scrollToElement(name);
		waitForElementToBeClickable(
				driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='" + name + "']"))).click();
	}

	public String validateErrorToastMsg() {
		return toastMessage.getText();
		

	}

	public void enterNameField(String name) {
		waitForElementToBeVisible(nameField).sendKeys(name);

	}

	public void selectFemaleRadioBtn() {
		waitForElementToBeClickable(FemaleRadioButton).click();

	}
	
	public ProductsPage clickOnLetShopButton() {
		waitForElementToBeClickable(letShopBtn).click();
		return new ProductsPage(driver);

	}
	
	
	
}
