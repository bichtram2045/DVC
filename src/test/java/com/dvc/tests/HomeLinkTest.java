package com.dvc.tests;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import com.dvc.base.BaseSetup;
import com.dvc.pages.HomePage;
import com.dvc.pages.IntroductionPage;

public class HomeLinkTest extends BaseSetup {

	HomePage homePage;
	IntroductionPage introductionPage;
	
	@BeforeMethod
	public void setUp() throws MalformedURLException {
		driver = getDriver();
		driver.manage().window().maximize();
		driver.get("https://dichvucong.gov.vn/p/home/dvc-trang-chu.html");
		testRailLog = new StringBuilder();
		homePage = new HomePage(driver, testRailLog);
		introductionPage = new IntroductionPage(driver, testRailLog);
	}
	
	@Test(priority = 1)
	public void verifyLink() {
		testCaseId = "67";
		String url;
		int statusCode;

		homePage.hoverSupBtn();
		homePage.clickIntroBtn();
		url = introductionPage.getLink();
		introductionPage.clickLink();
		
		statusCode = RestAssured.get(url).getStatusCode();
		Assert.assertEquals(statusCode, 200, 
	            "Link bị lỗi. Status Code trả về là: " + statusCode);
	}
}
