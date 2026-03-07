package com.dvc.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.healenium.SelfHealingDriver;

public class IntroductionPage {
	SelfHealingDriver driver;
    WebDriverWait wait;
	StringBuilder testRailLog;
	
	@FindBy(className = "link")
	WebElement link;
	
	public IntroductionPage(SelfHealingDriver driver, StringBuilder testRailLog) {
    	this.testRailLog = testRailLog;
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
	
	public String getLink() {
	    return link.getAttribute("href");
	}
	
	public void clickLink() {
		testRailLog.append("Nhấn vào link \n");
		link.click();
	}
}
