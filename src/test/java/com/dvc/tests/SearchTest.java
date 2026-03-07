package com.dvc.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.dvc.base.BaseSetup;
import com.dvc.pages.FAQPage;
import com.dvc.pages.HomePage;
import com.dvc.utils.ExcelHelper;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;

public class SearchTest extends BaseSetup {
	HomePage homePage;
	FAQPage faqPage;
	ExcelHelper excel;

	@BeforeMethod
	public void setUp() throws MalformedURLException {
		driver = getDriver();
		driver.manage().window().maximize();
		driver.get("https://dichvucong.gov.vn/p/home/dvc-trang-chu.html");
		testRailLog = new StringBuilder();
		homePage = new HomePage(driver, testRailLog);
		faqPage = new FAQPage(driver, testRailLog);
	}

	@DataProvider(name = "searchData")
	public Object[][] getSearchData(Method method) throws Exception {
		excel = new ExcelHelper();
		excel.setExcelFile("src/test/resources/data/SearchData.xlsx", "Sheet1");
		
		return excel.getDataByTestCase(method.getName());
	}
	
	@Test(priority = 1, dataProvider = "searchData")
	public void searchWithValidKey(String key) {
		testCaseId = "60";
		
		int t;
		homePage.hoverBtn();
		homePage.clickChtgBtn();
		faqPage.typeKey(key);
		faqPage.clickSearchBtn();

		// Xác minh nội dung của mỗi item đều chứa từ khoá
		t = faqPage.getResultsCount();
		for (int i = 0; i < t; i++) {
			faqPage.clickResultAtIndex(i);

			String content = faqPage.getDetailFullText();
			Assert.assertTrue(content.contains(key), "Lỗi: Nội dung thứ " + i + " không chứa từ khóa!");
			testRailLog.append("Nội dung thứ " + i + " chứa từ khóa! \n");

			faqPage.goBack();
			faqPage.clickSearchBtn();
		}
	}

	@Test(priority = 2, dataProvider = "searchData")
	public void searchWithInvalidKey(String key) {
		testCaseId = "61";
		String message;
		
		homePage.hoverBtn();
		homePage.clickChtgBtn();
		faqPage.typeKey(key);
		faqPage.clickSearchBtn();

		message = faqPage.getErrorMessage();
		Assert.assertTrue(message.contains("Không tìm thấy kết quả"),
				"Lỗi: Thông báo không đúng. Thực tế là: " + message);
	}

	@Test(priority = 3, dataProvider = "searchData")
	public void searchWithEmptyKey(String key) {
		testCaseId = "62";
		List<String> dataBefore, dataAfter;
		String actualToast;
		
		homePage.hoverBtn();
		homePage.clickChtgBtn();

		dataBefore = faqPage.getAllResultText();

		faqPage.typeKey(key);
		faqPage.clickSearchBtn();

		actualToast = faqPage.getToastMessage();
		dataAfter = faqPage.getAllResultText();

		// Xác minh toast hiện đúng nội dung
		Assert.assertEquals(actualToast, "Cần nhập từ khóa cần tìm kiếm", "Lỗi: Thông báo không đúng nội dung!");

		// Xác minh danh sách không thay đổi
		Assert.assertEquals(dataAfter, dataBefore, "Lỗi: Danh sách kết quả đã bị thay đổi!");
	}

	@Test(priority = 4, dataProvider = "searchData")
	public void searchWithSpaceOnlyInput(String key) {
		testCaseId = "63";
		List<String> dataBefore, dataAfter;
		String actualToast;
		
		homePage.hoverBtn();
		homePage.clickChtgBtn();

		dataBefore = faqPage.getAllResultText();

		faqPage.typeKey(key);
		faqPage.clickSearchBtn();

		actualToast = faqPage.getToastMessage();
		dataAfter = faqPage.getAllResultText();

		// Xác minh toast hiện đúng nội dung
		Assert.assertEquals(actualToast, "Cần nhập từ khóa cần tìm kiếm", "Lỗi: Thông báo không đúng nội dung!");

		// Xác minh danh sách không thay đổi
		Assert.assertEquals(dataAfter, dataBefore, "Lỗi: Danh sách kết quả đã bị thay đổi!");
	}

	@Test(priority = 5, dataProvider = "searchData")
	public void searchWithCaseInsensitiveInput(String key) {
		testCaseId = "64";
		List<String> listLower, listUpper;
		
		homePage.hoverBtn();
		homePage.clickChtgBtn();

		faqPage.searchWithLowerCase(key);
		faqPage.clickSearchBtn();
		listLower = faqPage.getAllResultText();

		faqPage.searchWithUpperCase(key);
		faqPage.clickSearchBtn();
		listUpper = faqPage.getAllResultText();

		// Xác minh danh sách kết quả sau khi search bằng chữ hoa, thường là giống nhau
		Assert.assertEquals(listLower, listUpper, "Lỗi: Kết quả tìm kiếm chữ hoa/thường không giống nhau!");
	}

	@Test(priority = 6, dataProvider = "searchData")
	public void searchWithLeadingWhitespace(String key) {
		testCaseId = "65";
		int t;
		
		homePage.hoverBtn();
		homePage.clickChtgBtn();

		faqPage.typeKey(key);
		faqPage.clickSearchBtn();

		// Xác minh nội dung của mỗi item đều chứa từ khoá
		t = faqPage.getResultsCount();
		for (int i = 0; i < t; i++) {
			faqPage.clickResultAtIndex(i);

			String content = faqPage.getDetailFullText();
			Assert.assertTrue(content.contains(key.trim()), "Lỗi: Nội dung thứ " + i + " không chứa từ khóa!");

			faqPage.goBack();
			faqPage.clickSearchBtn();
		}
	}

	@Test(priority = 7, dataProvider = "searchData")
	public void searchWithTrailingWhitespace(String key) {
		testCaseId = "66";
		int t;
		
		homePage.hoverBtn();
		homePage.clickChtgBtn();

		faqPage.typeKey(key);
		faqPage.clickSearchBtn();

		// Xác minh nội dung của mỗi item đều chứa từ khoá
		t = faqPage.getResultsCount();
		for (int i = 0; i < t; i++) {
			faqPage.clickResultAtIndex(i);

			String content = faqPage.getDetailFullText();
			Assert.assertTrue(content.contains(key.trim()), "Lỗi: Nội dung thứ " + i + " không chứa từ khóa!");

			faqPage.goBack();
			faqPage.clickSearchBtn();
		}
	}

}
