package TestClasses.IOS;

import org.testng.Assert;
import org.testng.annotations.Test;

import IOSBase.IOSMobileBase;
import PageObjects.IOS.AlertViewPage;
import PageObjects.IOS.HomePage;
import Utilities.AppiumDriverClass;

public class KitCatalogApp extends IOSMobileBase{
	
	HomePage hp;
	AlertViewPage avp;
	
	@Test
	public void validateAlertViewPopup()
	{
		hp = new HomePage(AppiumDriverClass.getDriver());
		avp = hp.clickOnAlerViewOption();
		avp.clickOkCancelButton();
		String actualTitle = avp.alertPopupTitle();
		Assert.assertEquals(actualTitle, "A Short Title Is Best");
		avp.clickOkButtonOnPopup();
	}
	
	
	

}
