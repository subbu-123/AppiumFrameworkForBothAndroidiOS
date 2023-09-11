package PageObjects.Android;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Android.AndroidMobileActions;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductsPage extends AndroidMobileActions {

	AndroidDriver driver;

	public ProductsPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);

	}

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.androidsample.generalstore:id/appbar_btn_cart\")")
	private WebElement cartButton;

	@AndroidFindAll(value = { @AndroidBy(xpath = "//*[contains(@resource-id,'productName')]") })
	private List<WebElement> displayedProducts;

	public double addProductsToCart(List<String> productNames) {
		double sumTotalOfProducts = 0;

		for (String product : productNames) {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().className(\"android.support.v7.widget.RecyclerView\")).scrollIntoView(text(\""
							+ product + "\"))"));

			// List<WebElement> displayedProducts =
			// driver.findElements(AppiumBy.xpath("//*[contains(@resource-id,'productName')]"));
			for (int i = 0; i < displayedProducts.size(); i++) {
				if (displayedProducts.get(i).getText().equalsIgnoreCase(product)) {
					driver.findElement(
							AppiumBy.xpath("(//*[contains(@resource-id,'productAddCart')])[" + (i + 1) + "]")).click();
					String priceText = driver
							.findElement(
									AppiumBy.xpath("(//*[contains(@resource-id,'productPrice')])[" + (i + 1) + "]"))
							.getText().replace("$", "");
					sumTotalOfProducts = sumTotalOfProducts + Double.parseDouble(priceText);
					break;
				}
			}

		}

		return sumTotalOfProducts;
	}
	
	public CartPage clickOnCartButton()
	{
		waitForElementToBeClickable(cartButton).click();
		return new CartPage(driver);
	}
	
	
	
	
	
	

}
