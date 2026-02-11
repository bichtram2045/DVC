package org.example.pages;

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

    public HomePage(SelfHealingDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void hoverBtn () {
    	Actions actions = new Actions(driver);
        actions.moveToElement(ttdvBtn).perform();
    }
    
    public SearchPage clickChtgBtn () {
    	chtgBtn.click();
    	return new SearchPage(driver);
    }
    
}