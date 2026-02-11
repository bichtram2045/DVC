package org.example.pages;

import org.openqa.selenium.By;
import com.epam.healenium.SelfHealingDriver;

public class LoginPage {
    SelfHealingDriver driver;

    By txtUsername = By.id("username");
    By txtPassword = By.id("password");
    By btnLogin = By.cssSelector("button.radius");

    public LoginPage(SelfHealingDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(txtUsername).sendKeys(user);
        driver.findElement(txtPassword).sendKeys(pass);
        driver.findElement(btnLogin).click();
    }
}