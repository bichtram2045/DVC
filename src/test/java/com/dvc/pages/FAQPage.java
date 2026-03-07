package com.dvc.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.healenium.SelfHealingDriver;

public class FAQPage {
	SelfHealingDriver driver;
	WebDriverWait wait;
	StringBuilder testRailLog;

	@FindBy(id = "tuKhoa")
	WebElement searchInput;

	@FindBy(id = "doSearch")
	WebElement searchBtn;

	@FindBy(css = "#tatCa a.item")
	List<WebElement> results;

	@FindBy(className = "main-title-sub")
	WebElement titleDetail;

	@FindBy(className = "article")
	WebElement contentDetail;

	@FindBy(id = "mainTitle")
	WebElement resultMsg;

	@FindBy(className = "toast-warning")
	WebElement toastMsg;

	@FindBy(css = "#paginationKQTT li[jp-role='page'][jp-data='1']")
	WebElement pageOneItem;

	@FindBy(className = "next")
	WebElement nextBtn;

	@FindBy(className = "prev")
	WebElement prevBtn;

	@FindBy(className = "last")
	WebElement lastBtn;

	@FindBy(className = "first")
	WebElement firstBtn;

	@FindBy(id = "strLeftResult")
	WebElement dropdown;
	
	@FindBy(id = "totalRecord")
	WebElement totalRecords;

	public FAQPage(SelfHealingDriver driver, StringBuilder testRailLog) {
		this.testRailLog = testRailLog;
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	public void typeKey(String key) {
		testRailLog.append("Nhập từ khoá: " + key + "\n");
		searchInput.clear();
		searchInput.sendKeys(key);
	}

	public void clickSearchBtn() {
		testRailLog.append("Nhấn nút Tìm kiếm \n");
		searchBtn.click();
	}

	public int getResultsCount() {
		testRailLog.append("Lấy số câu hỏi trên trang \n");
		return results.size();
	}

	public void clickResultAtIndex(int index) {
		testRailLog.append("Nhấn vào câu hỏi thứ " + index + "\n");
		results.get(index).click();
	}

	public String getDetailFullText() {
		String title = titleDetail.getText();
		String body = contentDetail.getText();
		return (title + " " + body).toLowerCase();
	}

	public void goBack() {
		testRailLog.append("Quay lại trang Câu hỏi thường gặp \n");
		driver.navigate().back();
	}

	public String getErrorMessage() {
		testRailLog.append("Lấy nội dung thông báo \n");
		return resultMsg.getText();
	}

	public List<String> getAllResultText() {
		testRailLog.append("Lấy toàn bộ nội dung câu hỏi trên trang \n");
		List<String> texts = new ArrayList<>();
		for (WebElement r : results) {
			texts.add(r.getText());
		}
		return texts;
	}

	public String getToastMessage() {
		testRailLog.append("Lấy nội dung Toast \n");
		return toastMsg.getText();
	}

	public void searchWithLowerCase(String key) {
		typeKey(key.toLowerCase());
		clickSearchBtn();
	}

	public void searchWithUpperCase(String key) {
		typeKey(key.toUpperCase());
		clickSearchBtn();
	}

	public String getPageOneClass() {
		return pageOneItem.getAttribute("class");
	}

	public boolean isNextButtonAvailable() {
		String className = nextBtn.getAttribute("class");
		return !className.contains("disabled");
	}

	public void clickNext() {
		testRailLog.append("Nhấn nút '>' \n");
		nextBtn.click();
	}

	public WebElement getPageElement(int pageNum) {
		return driver.findElement(By.cssSelector("#paginationKQTT li.page[jp-data='" + pageNum + "']"));
	}

	public void clickPageNumber(int pageNum) {
		testRailLog.append("Nhấn vào số trang " + pageNum + "\n");
		getPageElement(pageNum).click();
	}

	public String getPageStatus(int pageNum) {
		return getPageElement(pageNum).getAttribute("class");
	}

	public void clickPrev() {
		testRailLog.append("Nhấn nút '<' \n");
		prevBtn.click();
	}

	public void clickLast() {
		testRailLog.append("Nhấn nút '>>' \n");
		lastBtn.click();
	}

	public void clickFirst() {
		testRailLog.append("Nhấn nút '<<' \n");
		firstBtn.click();
	}

	public List<String> getOverlappingItems(List<String> list1, List<String> list2) {
		testRailLog.append("Lấy nội dung trùng lặp giữa 2 trang \n");
		List<String> common = new ArrayList<>(list1);
		common.retainAll(list2);
		return common;
	}

	public void clickDropdown() {
		testRailLog.append("Nhấn vào Dropdown \n");
		dropdown.click();
	}

	public int selectDisplayLimit(int value) {
		testRailLog.append("Chọn giá trị " + value + "\n");
		Select select = new Select(dropdown);
		select.selectByValue(String.valueOf(value));
		String selectedValue = select.getFirstSelectedOption().getAttribute("value");
		return Integer.parseInt(selectedValue);
	}
	
	public int getTotalRecords() {
		testRailLog.append("Lấy số bản ghi \n");
		String value = totalRecords.getText().trim();
	    return Integer.parseInt(value);
    }
	
	public WebElement getPrevBtn() {
		testRailLog.append("nút '<'");
	    return prevBtn;
	}

	public WebElement getFirstBtn() {
		testRailLog.append("nút '<<'");
	    return firstBtn;
	}
	
	public WebElement getNextBtn() {
		testRailLog.append("nút '>'");
	    return nextBtn;
	}

	public WebElement getLastBtn() {
		testRailLog.append("nút '>>'");
	    return lastBtn;
	}

	public String getButtonStatus(WebElement element) {
		testRailLog.append("Lấy status của " + element + "\n");
	    return element.getAttribute("class");
	}
}