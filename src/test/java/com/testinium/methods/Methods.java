package com.testinium.methods;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.testinium.driver.BaseTest;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Methods {

    WebDriver driver;
    FluentWait<WebDriver> wait;
    JavascriptExecutor jsdriver;

    Logger logger = LogManager.getLogger(Methods.class);

    public Methods(){
        driver = BaseTest.driver;
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(300)).ignoring(NoSuchElementException.class);
        jsdriver = (JavascriptExecutor) driver;
    }

    public WebElement findElement(By by){
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }

    public void click(By by){
        findElement(by).click();

    }

    public void sendKeys(By by,String text){
        findElement(by).clear();
        findElement(by).sendKeys(text);
    }

    public void sendKey(By by,Keys key){
        findElement(by).sendKeys(key);
    }

    public void hoverElement(By by){
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(by)).perform();
    }

    public void waitBySeconds(long seconds){
        try {
            Thread.sleep(seconds*1000);

        } catch (Exception e){
            e.printStackTrace();

        }

    }


    public boolean isHomePageVisible(By by){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;

        } catch (Exception e){
            return false;

        }

    }

    public boolean isCartFilled(By by, String text){
        try {
            wait.until(ExpectedConditions.textToBe(by, text));
            //System.out.println("Sepette " + text + " ürün var.");
            logger.info("Sepette " + text + " ürün var");
            return true;

        } catch (Exception e){
            return false;

        }

    }


    public Select getSelect(By by){
        return new Select(findElement(by));


    }

    public boolean isElementClickable(By by){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
            return true;

        }catch (Exception e){
            return false;
        }

    }

    public boolean isElementVisible(By by){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;

        } catch (Exception e){
            return false;

        }

    }

    public boolean isElementInvisible(By by){
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;

        } catch (Exception e){

            return false;
        }
    }


    public void scrollWithAction(By by){
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(by)).build().perform();

    }

    public void scrollWithJS(By by){
        jsdriver.executeScript("arguments[0].scrollIntoView();",findElement(by));
    }

    public void selectByText(By by, String text){
        getSelect(by).selectByVisibleText(text);

    }

    public String getAttribute(By by, String attributeName){
        return findElement(by).getAttribute(attributeName);

    }

    public String getValue(By by){
        return jsdriver.executeScript("return arguments[0].value",findElement(by)).toString();
    }

    public void switchToTest(){
        driver.navigate().to("https://demoqa.com/browser-windows");
        String currentWindow = driver.getWindowHandle();

        findElement(By.id("tabButton")).click();

        waitBySeconds(4);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();

        for (String window : windowHandles){
            if (!currentWindow.equals(window)){
                driver.switchTo().window(window);
                waitBySeconds(3);
            }
        }

        System.out.println("Yeni pencere text " + findElement(By.id("sampleHeading")).getText());
        driver.close();

        waitBySeconds(3);
        driver.switchTo().window(currentWindow);

    }

    public void checkUrl(String checkedUrl) {
        //String URL = driver.getCurrentUrl();
        //Assert.assertEquals(URL, checkedUrl);
        wait.until(ExpectedConditions.urlToBe(checkedUrl));

    }

    public String getText(By by){
        return findElement(by).getText();

    }

    public void readEmailFromCsv(String id) throws IOException, CsvValidationException, InterruptedException{
        String csvPath = "src/test/resources/searchdata.csv";
        CSVReader csvReader;
        String[] csvCell;
        csvReader = new CSVReader(new FileReader((csvPath)));
        csvCell = csvReader.readNext();
        String data1 = csvCell[0];
        sendKeys(By.id(id),data1);

    }

    public void readPasswordFromCsv(String id) throws IOException, CsvValidationException, InterruptedException{
        String csvPath = "src/test/resources/searchdata.csv";
        CSVReader csvReader;
        String[] csvCell;
        csvReader = new CSVReader(new FileReader((csvPath)));
        csvCell = csvReader.readNext();
        String data2 = csvCell[1];
        sendKeys(By.id(id),data2);

    }



}
