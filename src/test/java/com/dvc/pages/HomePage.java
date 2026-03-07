package com.dvc.pages;

import java.time.Duration;
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

public class HomePage {
    SelfHealingDriver driver;
    WebDriverWait wait;
	StringBuilder testRailLog;
    
    @FindBy(xpath = "//nav//a[contains(., 'Thông tin và dịch vụ')]")
	WebElement ttdvBtn;
    
    @FindBy(xpath = "//nav//a[contains(., 'Câu hỏi thường gặp')]")
	WebElement chtgBtn;
    
    @FindBy(id = "tuKhoa")
	WebElement searchInput;
    
    @FindBy(id = "doSearch")
	WebElement searchBtn;
    
    @FindBy(css = "#tatCa a.item")
    List<WebElement> results;
    
    @FindBy(xpath = "//a[contains(text(),'Hỗ trợ')]")
	WebElement supBtn;
    
    @FindBy(xpath = "//a[contains(text(),'Giới thiệu')]")
	WebElement introBtn;

    public HomePage(SelfHealingDriver driver, StringBuilder testRailLog) {
    	this.testRailLog = testRailLog;
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public void hoverBtn () {
    	testRailLog.append("Hover vào nút 'Thông tin và dịch vụ' \n");
    	Actions actions = new Actions(driver);
        actions.moveToElement(ttdvBtn).perform();
    }
    
    public FAQPage clickChtgBtn () {
    	testRailLog.append("Nhấn nút 'Câu hỏi thường gặp' \n");
    	chtgBtn.click();
    	return new FAQPage(driver, testRailLog);
    }
    
    public void hoverSupBtn () {
    	testRailLog.append("Hover vào nút 'Hỗ trợ' \n");
    	Actions actions = new Actions(driver);
        actions.moveToElement(supBtn).perform();
    }
    
    public IntroductionPage clickIntroBtn () {
    	testRailLog.append("Nhấn nút 'Giới thiệu' \n");
    	introBtn.click();
    	return new IntroductionPage(driver, testRailLog);
    }
}