package org.example.testcases;

import org.example.base.BaseSetup;
import org.example.pages.HomePage;
import org.example.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.util.List;

public class SearchTest extends BaseSetup {
    HomePage homePage;
    SearchPage searchPage;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        driver = getDriver();
        driver.manage().window().maximize();
        driver.get("https://dichvucong.gov.vn/p/home/dvc-trang-chu.html");
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
    }

    @Test (priority = 1)
    public void searchWithValidKey() {
    	int t;
    	String key = "công nghiệp";
        homePage.hoverBtn();
        homePage.clickChtgBtn();
        searchPage.typeKey(key);
        searchPage.clickSearchBtn();
        
        // Xác minh nội dung của mỗi item đều chứa từ khoá
        t = searchPage.getResultsCount();
        for (int i = 0; i < t; i++) {
            searchPage.clickResultAtIndex(i);

            String content = searchPage.getDetailFullText();
            Assert.assertTrue(content.contains(key), 
                "Lỗi: Nội dung thứ " + i + " không chứa từ khóa!");

            searchPage.goBack();
            searchPage.clickSearchBtn();
        }
    }
    
    @Test (priority = 2)
    public void searchWithInvalidKey() {
    	String message;
    	String key = "abc123.";
    	homePage.hoverBtn();
        homePage.clickChtgBtn();
        searchPage.typeKey(key);
        searchPage.clickSearchBtn();
        
        message = searchPage.getErrorMessage();
        Assert.assertTrue(message.contains("Không tìm thấy kết quả"), 
                "Lỗi: Thông báo không đúng. Thực tế là: " + message);
    }
    
    @Test (priority = 3)
    public void searchWithEmptyKey() {
    	List<String> dataBefore, dataAfter;
    	String actualToast;
    	String key = "";
    	homePage.hoverBtn();
        homePage.clickChtgBtn();
        
        dataBefore = searchPage.getAllResultText();
        
        searchPage.typeKey(key);
        searchPage.clickSearchBtn();
        
        actualToast = searchPage.getToastMessage();
        dataAfter = searchPage.getAllResultText();
        
        // Xác minh toast hiện đúng nội dung
        Assert.assertEquals(actualToast, "Cần nhập từ khóa cần tìm kiếm", 
        		"Lỗi: Thông báo không đúng nội dung!");
        
        // Xác minh danh sách không thay đổi
        Assert.assertEquals(dataAfter, dataBefore, 
        		"Lỗi: Danh sách kết quả đã bị thay đổi!");   
    }
    
    @Test (priority = 4)
    public void searchWithSpaceOnlyInput() {
    	List<String> dataBefore, dataAfter;
    	String actualToast;
    	String key = "";
    	homePage.hoverBtn();
        homePage.clickChtgBtn();
        
        dataBefore = searchPage.getAllResultText();
        
        searchPage.typeKey(key);
        searchPage.clickSearchBtn();
        
        actualToast = searchPage.getToastMessage();
        dataAfter = searchPage.getAllResultText();
        
        // Xác minh toast hiện đúng nội dung
        Assert.assertEquals(actualToast, "Cần nhập từ khóa cần tìm kiếm",
        		"Lỗi: Thông báo không đúng nội dung!");
        
        // Xác minh danh sách không thay đổi
        Assert.assertEquals(dataAfter, dataBefore, 
        		"Lỗi: Danh sách kết quả đã bị thay đổi!");
    }
    
    @Test (priority = 5)
    public void searchWithCaseInsensitiveInput() {
    	List<String> listLower, listUpper;
    	String key = "Y TẾ";
    	homePage.hoverBtn();
        homePage.clickChtgBtn(); 
        
        searchPage.searchWithLowerCase(key);
        searchPage.clickSearchBtn();
        listLower = searchPage.getAllResultText();
        
        searchPage.searchWithUpperCase(key);
        searchPage.clickSearchBtn();
        listUpper = searchPage.getAllResultText();
        
        // Xác minh danh sách kết quả sau khi search bằng chữa hoa, thường là giống nhau
        Assert.assertEquals(listLower, listUpper, 
                "Lỗi: Kết quả tìm kiếm chữ hoa/thường không giống nhau!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
