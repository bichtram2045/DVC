package org.example.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.healenium.SelfHealingDriver;

public class SearchPage {
    SelfHealingDriver driver;
    WebDriverWait wait;
    
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

    public SearchPage(SelfHealingDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public void typeKey (String key) {
    	searchInput.clear();
    	searchInput.sendKeys(key);
    }
    
    public void clickSearchBtn () {
    	searchBtn.click();
    }
    
    public int getResultsCount() {
        return results.size();
    }
    
    public void clickResultAtIndex(int index) {
        results.get(index).click();
    }

    public String getDetailFullText() {
        String title = titleDetail.getText();
        String body = contentDetail.getText();
        return (title + " " + body).toLowerCase();
    }

    public void goBack() {
        driver.navigate().back();
    }
    
    public String getErrorMessage() {
        return resultMsg.getText();
    }
    
    public List<String> getAllResultText() {
        List<String> texts = new ArrayList<>();
        for (WebElement r : results) {
            texts.add(r.getText());
        }
        return texts;
    }
    
    public String getToastMessage() {
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
    
}