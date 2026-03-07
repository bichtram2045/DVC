package com.dvc.tests;

import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dvc.base.BaseSetup;
import com.dvc.pages.FAQPage;
import com.dvc.pages.HomePage;

public class FAQDisplayAndPaginationTest extends BaseSetup {
	HomePage homePage;
	FAQPage faqPage;

	@BeforeMethod
	public void setUp() throws MalformedURLException {
		driver = getDriver();
		driver.manage().window().maximize();
		driver.get("https://dichvucong.gov.vn/p/home/dvc-trang-chu.html");
		testRailLog = new StringBuilder();
		homePage = new HomePage(driver, testRailLog);
		faqPage = new FAQPage(driver, testRailLog);
	}

	@Test(priority = 1)
	public void verifyFirstPageIsDisplayedByDefault() {
		testCaseId = "46";
		String classValue;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		classValue = faqPage.getPageOneClass();
		Assert.assertTrue(classValue.contains("active"), "Lỗi: Trang 1 không được active.");
	}

	@Test(priority = 2)
	public void verifyDefaultNumberOfRecords() {
		testCaseId = "47";
		int records;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		records = faqPage.getResultsCount();
		Assert.assertEquals(records, 10, 
				"Lỗi: Số bản ghi mặc định không phải là 10. Thực tế là: " + records);
	}

	@Test(priority = 3)
	public void verifyNoInvalidRecords() {
		testCaseId = "48";
		List<String> data;
		int pageNum;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		pageNum = 1;
		while (true) {
			data = faqPage.getAllResultText();

			boolean isDataClean = !data.isEmpty() && !data.contains("null") && !data.contains("undefined");
			Assert.assertTrue(isDataClean, "Lỗi dữ liệu tại trang " + pageNum + ". Nội dung thực tế: " + data);

			if (faqPage.isNextButtonAvailable()) {
				faqPage.clickNext();
				pageNum++;
			} else {
				break;
			}
		}
	}

	@Test(priority = 4)
	public void verifyPageNavigationByPageNumber() {
		testCaseId = "49";
		int pageNumber;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		pageNumber = 3;
		faqPage.clickPageNumber(pageNumber);

		// Kiểm tra trang được chọn được active không
		String status = faqPage.getPageStatus(pageNumber);
		Assert.assertTrue(status.contains("active"),
				"Lỗi: Trang " + pageNumber + " chưa được active. Thực tế: " + status);
	}

	@Test(priority = 5)
	public void verifyNavigateWithPrevious() {
		testCaseId = "50";
		int pageNumber;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		pageNumber = 3;
		faqPage.clickPageNumber(pageNumber);

		faqPage.clickPrev();
		String status = faqPage.getPageStatus(pageNumber - 1);
		Assert.assertTrue(status.contains("active"),
				"Lỗi: Trang " + pageNumber + " chưa được active. Thực tế: " + status);
	}

	@Test(priority = 6)
	public void verifyNavigateWithNext() {
		testCaseId = "51";

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		faqPage.clickNext();

		String status = faqPage.getPageStatus(2);
		Assert.assertTrue(status.contains("active"), "Lỗi: Trang " + 2 + " chưa được active. Thực tế: " + status);
	}

	@Test(priority = 7)
	public void verifyNavigateToLastPage() {
		testCaseId = "52";

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		faqPage.clickLast();

		String status = faqPage.getPageStatus(946);
		Assert.assertTrue(status.contains("active"), 
				"Lỗi: Trang " + 946 + " chưa được active. Thực tế: " + status);
	}

	@Test(priority = 8)
	public void verifyNavigateToFirstPage() {
		testCaseId = "53";
		int pageNumber;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		pageNumber = 4;
		faqPage.clickPageNumber(pageNumber);
		faqPage.clickFirst();

		String status = faqPage.getPageStatus(1);
		Assert.assertTrue(status.contains("active"),
				"Lỗi: Trang " + pageNumber + " chưa được active. Thực tế: " + status);
	}

	@Test(priority = 9)
	public void verifyPageDataChangesAfterNavigation() {
		testCaseId = "54";
		List<String> listBefore, listAfter, common;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		listBefore = faqPage.getAllResultText();
		
		faqPage.clickNext();

		listAfter = faqPage.getAllResultText();
		
		common = faqPage.getOverlappingItems(listBefore, listAfter);

		// Xác minh danh sách kết quả sau khi chuyển trang là khác nhau
		Assert.assertTrue(common.isEmpty(),
	            "Lỗi: Có dữ liệu trùng giữa hai trang: " + common);
	}
	
	@Test(priority = 10)
	public void verifyItemsPerPageChange() {
		testCaseId = "55";
		int value, records;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		faqPage.clickDropdown();
		
		value = 20;
		faqPage.selectDisplayLimit(value);
		
		records = faqPage.getResultsCount();
		Assert.assertEquals(records, value, 
				"Lỗi: Số bản ghi không phải là " + value + ". Thực tế là: " + records);
	}
	
	@Test(priority = 11)
	public void verifyResetToFirstPage() {
		testCaseId = "56";
		int value, pageNumber;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		pageNumber = 4;
		faqPage.clickPageNumber(pageNumber);
		faqPage.clickDropdown();
		
		value = 20;
		faqPage.selectDisplayLimit(value);
		
		String status = faqPage.getPageStatus(1);
		Assert.assertTrue(status.contains("active"),
				"Lỗi: Trang " + pageNumber + " chưa được active. Thực tế: " + status);
	}
	
	@Test(priority = 12)
	public void verifyRemainingRecordsOnLastPage() {
		testCaseId = "57";
		int value, remainRecords, actualRecords;

		homePage.hoverBtn();
		homePage.clickChtgBtn();
		faqPage.clickDropdown();
		
		value = 20;
		faqPage.selectDisplayLimit(value);
		
		faqPage.clickLast();
		
		actualRecords = faqPage.getResultsCount();
		testRailLog.append("Giá trị records thực tế: " + actualRecords + "\n");
		
		remainRecords = faqPage.getTotalRecords() % value;
		testRailLog.append("Giá trị records mong đợi: " + remainRecords + "\n");

		Assert.assertEquals(remainRecords, actualRecords, 
				"Lỗi: Số bản ghi không phải là " + remainRecords + ". Thực tế là: " + actualRecords);
	}
	
	@Test(priority = 13)
	public void verifyNavButtonsOnFirstPage() {
		testCaseId = "58";
		String firstStatus, prevStatus;

		homePage.hoverBtn();
		homePage.clickChtgBtn();

		prevStatus = faqPage.getButtonStatus(faqPage.getPrevBtn());
		firstStatus = faqPage.getButtonStatus(faqPage.getFirstBtn());
		
		Assert.assertTrue(prevStatus.contains("disabled"),
		        "Nút '<' phải được vô hiệu hoá ở trang đầu tiên");
		
		Assert.assertTrue(firstStatus.contains("disabled"),
		        "Nút '<<' phải được vô hiệu hoá ở trang đầu tiên");
	}
	
	@Test(priority = 14)
	public void verifyNavButtonsOnLastPage() {
		testCaseId = "59";
		String lastStatus, nextStatus;

		homePage.hoverBtn();
		homePage.clickChtgBtn();
		
		faqPage.clickLast();

		nextStatus = faqPage.getButtonStatus(faqPage.getNextBtn());
		lastStatus = faqPage.getButtonStatus(faqPage.getLastBtn());
		
		Assert.assertTrue(nextStatus.contains("disabled"),
		        "Nút '>' phải được vô hiệu hoá ở trang cuối");
		
		Assert.assertTrue(lastStatus.contains("disabled"),
				"Nút '>>' phải được vô hiệu hoá ở trang cuối");
	}
}
