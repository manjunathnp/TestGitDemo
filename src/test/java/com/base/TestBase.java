package com.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

public class TestBase {
    public WebDriver driver;
    public Properties properties;
    public WebDriverWait wait;

    @BeforeClass
    public void launch() throws IOException {
        // Loading config.properties File
        FileReader file = new FileReader("Properties/config.properties");
        properties =new Properties();
        properties.load(file);



        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void captureScreenshot(String destFileName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        File destFile = new File(properties.getProperty("screenshotDir"));
        FileUtils.copyFile(srcFile, new File(destFile + File.separator + destFileName + ".png"));
    }

    public void elementClick(WebElement element){
        wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void fluentElementClick(WebElement element){
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(30)).
                pollingEvery(Duration.ofSeconds(6)).
                ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

}
