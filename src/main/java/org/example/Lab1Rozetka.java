package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;

public class Lab1Rozetka {
    private WebDriver chromeDriver;

    private static final String baseUrl = "https://rozetka.com.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUP() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--start-fullscreen");
        this.chromeDriver = new ChromeDriver(chromeOptions);

    }
    @BeforeMethod
    public void preconditions() {

        chromeDriver.get(baseUrl);
    }

    public void tearDown() { chromeDriver.quit(); }

    @Test
    public void testClickOnElement() {

        WebElement OnElementButton = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/aside/rz-main-page-sidebar/div[1]/rz-sidebar-fat-menu/div/ul/li[1]/a"));

        Assert.assertNotNull(OnElementButton);
        OnElementButton.click();

        Assert.assertEquals(chromeDriver.getCurrentUrl(), baseUrl);
    }

    @Test
    public void testSearchFieldOnMainPage() {

        WebElement searchField = chromeDriver.findElement(By.tagName("input"));

        Assert.assertNotNull(searchField);

        String inputValue = "Відеокарти";
        searchField.sendKeys(inputValue);

        searchField.sendKeys(Keys.ENTER);
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(),baseUrl);
    }

    @Test
    public void testFindElementByClassName() {

        WebElement headerlayout = chromeDriver.findElement(By.className("header-layout"));
        Assert.assertNotNull(headerlayout);
    }

    @Test
    public void testIsDisplayedForTile() {

        WebElement tileElement = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/main/rz-main-page-content/rz-goods-sections/section[1]/rz-goods-section/ul/li[1]/rz-app-tile/div"));
        Assert.assertTrue(tileElement.isDisplayed(),"Елемент не існує");
    }

}
