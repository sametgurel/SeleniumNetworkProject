package com.testinium.driver;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    Logger logger = LogManager.getLogger(BaseTest.class);

    public static WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        logger.info("Driver URL'e yönlendiriliyor.");
        driver.get("https://www.network.com.tr/");


    }

    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();

        }
    }

}
