package org.example.base;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseSetup {
    protected SelfHealingDriver driver;

    public SelfHealingDriver getDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        // Kết nối tới Selenium Grid (Healenium thường chạy qua Docker)
        WebDriver delegate = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver = SelfHealingDriver.create(delegate);
        return driver;
    }
}