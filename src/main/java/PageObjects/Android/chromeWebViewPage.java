package PageObjects.Android;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.Utils;


public class chromeWebViewPage extends Utils {
	
	WebDriver driver;

	public chromeWebViewPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(className = "gLFyf")
	private WebElement searchBox;
	
	public void enterSearchField(String text)
	{
		waitForElementToBeVisible(searchBox).sendKeys(text);
		searchBox.sendKeys(Keys.ENTER);
		staticWait(5000);
	}
	
	

}
