package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
//import java.time.Duration;
//import static java.time.Duration.ofSeconds;


public class Lab1 {
    private WebDriver chromeDriver;

    private static final String baseUrl = "https://www.nmu.org.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUP() {
        //Run driver
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        //set fullscreen
        chromeOptions.addArguments("--start-fullscreen");
        //setup wait for loading elements
        
        //chromeOptions.setImplicitWaitTimeout(ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);

    }
    @BeforeMethod
    public void preconditions() {
        //open main page
        chromeDriver.get(baseUrl);
    }

    public void tearDown() { chromeDriver.quit(); }

    @Test
    public void testHeaderExists() {
        WebElement header = chromeDriver.findElement(By.id("header"));
        //verification
        Assert.assertNotNull(header);
    }

    @Test
    public void testClickOnForStudent() {
        //find element by xpath
        WebElement forStudentButton = chromeDriver.findElement(By.xpath("/html/body/center/div[4]/div/div[1]/ul/li[4]/a"));
        //verification
        Assert.assertNotNull(forStudentButton);
        forStudentButton.click();
        //verification page changed
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), baseUrl);
    }

    @Test
    public void testSearchFieldOnForStudentPage() {
        String studentPageUrl = "content/student_life/students/";
        chromeDriver.get(baseUrl + studentPageUrl);
        //find element by tagName
        WebElement searchField = chromeDriver.findElement(By.tagName("input"));
        //verification
        Assert.assertNotNull(searchField);
        //different params of searchField
        System.out.println( String.format("Name attribute: %s", searchField.getAttribute("name")) +
                String.format("\n ID attribute: %s", searchField.getAttribute("id")) +
                String.format("\n Type attribute: %s", searchField.getAttribute("type")) +
                String.format("\n Value attribute: %s", searchField.getAttribute("value")) +
                String.format("\n Position: (%d;%d)", searchField.getLocation().x, searchField.getLocation().y) +
                String.format("\n Size: %dx%d", searchField.getSize().height, searchField.getSize().width)
        );
        //input value
        String inputValue = "I need info";
        searchField.sendKeys(inputValue);
        //verification text
        Assert.assertEquals(searchField.getText(), inputValue);
        //click enter
        searchField.sendKeys(Keys.ENTER);
        //verification page changed
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(),studentPageUrl);
    }

    @Test
    public void testSlider() {
        //find element by class name
        WebElement nextButton = chromeDriver.findElement(By.className("next"));
        //find element by css selector
        WebElement nextButtonByCss = chromeDriver.findElement(By.cssSelector("a.next"));
        //verification equality
        Assert.assertEquals(nextButton, nextButtonByCss);

        WebElement previousButton = chromeDriver.findElement(By.className("prev"));
        for(int i = 0; i < 20; i++) {
            if(nextButton.getAttribute("class").contains("disabled")) {
                previousButton.click();
                Assert.assertTrue(previousButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(nextButton.getAttribute("class").contains("disabled"));
            }
            else {
                nextButton.click();
                Assert.assertTrue(nextButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(previousButton.getAttribute("class").contains("disabled"));
            }
        }
    }
}


