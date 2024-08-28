package TestClasses.Android;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import AndroidBase.AndroidMobileBase;
import PageObjects.Android.CartPage;
import PageObjects.Android.ProductsPage;
import PageObjects.Android.RegisterPage;
import PageObjects.Android.chromeWebViewPage;
import Utilities.AppiumDriverClass;
import Utilities.Utils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class GeneralStore extends AndroidMobileBase {
	
	RegisterPage rp;
	ProductsPage pp;
	CartPage cp;
	chromeWebViewPage cwp;
	
	@Test(enabled = true, dataProvider = "shopFields")
	public void validationOfErrorMessageOnRegisterScreen(Map<String,Object> inputData) {  

		rp = new RegisterPage(AppiumDriverClass.getDriver());
		rp.clickOnCountryDropdown();
		
		String CountryName = (String) inputData.get("CountryName");
		rp.selectCountry(CountryName);
		rp.clickOnLetShopButton();
		String actualMsg = rp.validateErrorToastMsg();
		Assert.assertEquals(actualMsg, "Please enter your name");
	}
	

	@Test(enabled = false, dataProvider = "shopFields")
	public void validateSuccesfulRegistration(Map<String,Object> inputData){

		rp = new RegisterPage(AppiumDriverClass.getDriver());
		rp.clickOnCountryDropdown();
		
		String CountryName = (String)inputData.get("CountryName");
		rp.selectCountry(CountryName);
		
		String Name = (String)inputData.get("name");
		rp.enterNameField(Name);
		rp.selectFemaleRadioBtn();
		rp.clickOnLetShopButton();
	}
	
	
	@Test(enabled = false, dataProvider = "shopFields")
	public void validateProductsAddedToCart(Map<String,Object> inputData){

		rp = new RegisterPage(AppiumDriverClass.getDriver());
		rp.clickOnCountryDropdown();
		
		String CountryName = (String)inputData.get("CountryName");
		rp.selectCountry(CountryName);
		
		String Name = (String)inputData.get("name");
		rp.enterNameField(Name);
		
		rp.selectFemaleRadioBtn();
		pp = rp.clickOnLetShopButton();
		
		List<String> productNames = new ArrayList<>();
		productNames = (List<String>) inputData.get("productNames");
		double sumTotalOfProducts = pp.addProductsToCart(productNames);
		
		cp = pp.clickOnCartButton();
		double totalAmount = cp.getCartTotalAmount();
		Assert.assertEquals(totalAmount, sumTotalOfProducts);

	}
	
	@Test(enabled = false, dataProvider = "shopFields")
	public void validateUserAbleToCheckout(Map<String,Object> inputData){

		rp = new RegisterPage(AppiumDriverClass.getDriver());
		rp.clickOnCountryDropdown();
		
		String CountryName = (String)inputData.get("CountryName");
		rp.selectCountry(CountryName);
		
		String Name = (String)inputData.get("name");
		rp.enterNameField(Name);
		
		rp.selectFemaleRadioBtn();
		pp = rp.clickOnLetShopButton();
		
		List<String> productNames = new ArrayList<>();
		productNames = (List<String>) inputData.get("productNames");
		pp.addProductsToCart(productNames);
		
		cp = pp.clickOnCartButton();
		cp.clickOnCheckBox();
		cp.clickAndValidateTermsConditions();
		cp.clickOnProceedButton();
		
	}
	
	@Test(enabled = true, dataProvider = "shopFields")
	public void validateUserCompletesCheckout(Map<String,Object> inputData){

		rp = new RegisterPage(AppiumDriverClass.getDriver());
		rp.clickOnCountryDropdown();

		String CountryName = (String)inputData.get("CountryName");
		rp.selectCountry(CountryName);
		
		String Name = (String)inputData.get("name");
		rp.enterNameField(Name);
		
		rp.selectFemaleRadioBtn();
		pp = rp.clickOnLetShopButton();
		
		List<String> productNames = new ArrayList<>();
		productNames = (List<String>) inputData.get("productNames");
		pp.addProductsToCart(productNames);
		
		cp = pp.clickOnCartButton();
		cp.clickOnCheckBox();
		cp.clickAndValidateTermsConditions();
		cwp = cp.clickOnProceedButton();
		cwp.enterSearchField("modi");
		
	}
	
	
	
	@DataProvider(name = "shopFields")
	public Iterator<Map<String, Object>> getData()
	{
		Utils util = new Utils();
		List<Map<String,Object>> data = util.getJsondata(System.getProperty("user.dir") + "/TestData/generalStore.json");
		return data.iterator();
		
	}
	
	
}
