package com.testinium.pages;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.testinium.methods.Methods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.Console;
import java.io.IOException;


public class ProductPage {

    Methods methods;
    Logger logger = LogManager.getLogger(ProductPage.class);

    CSVReader csvReader;

    public ProductPage(){
        methods = new Methods();

    }

    public void selectProductAndAddToCartThenRemove() throws CsvValidationException, IOException, InterruptedException {

        logger.info("Network.com.tr URL'e yönlendirilme kontrol ediliyor.");
        methods.checkUrl("https://www.network.com.tr/");

        logger.info("Çerez politikası popup'ı kapatılıyor.");
        methods.click(By.id("onetrust-accept-btn-handler"));

        logger.info("Arama kutucuğuna 'ceket' yazdırılıyor.");
        methods.sendKeys(By.cssSelector("input[id='search']"),"ceket");

        logger.info("Arama kutucuğuna gelip 'ENTER' tuşu basılıyor.");
        methods.sendKey(By.cssSelector("input[id='search']"),Keys.ENTER);

        logger.info("Sayfada 'Daha fazla göster' butonu görünene kadar scroll işlemi yapılıyor.");
        methods.scrollWithAction(By.cssSelector("[class='button -secondary -sm relative']"));

        logger.info("'Daha fazla göster' butonuna tıklanıyor.");
        methods.click(By.cssSelector("[class='button -secondary -sm relative']"));

        logger.info("Ürün arama sayfasının 2. sayfasına geçildiği kontrol ediliyor.");
        methods.waitBySeconds(3);
        methods.checkUrl("https://www.network.com.tr/search?searchKey=ceket&page=2");


        //bu alanda locator class değişkenlik gösterdiği için cssselector olan locator xpath ile değiştirildi.

        logger.info("Ürün arama sayfasındaki indirimli ilk ürüne hover yapılıyor.");
        methods.hoverElement(By.xpath("//div[contains(@class, 'product__discountPercent ')]"));

        //değişiklik yapıldı


        logger.info("Hover yapılan ürün için beden seçimi yapılıyor.");
        String size = methods.getText(By.cssSelector("[class='radio-box__label ']"));
        String discountedPrice = methods.getText(By.cssSelector("[class='product__price -actual']"));
        methods.click(By.cssSelector("[class='radio-box__label ']"));

        //hatalı locator düzenlendi

        logger.info("Sağ alt köşede açılan 'Sepete git' butonuna tıklanıyor.");
        methods.click(By.cssSelector("[class='button -primary header__basket--checkout header__basketModal -checkout']"));

        logger.info("Ürüne ait beden ve fiyat bilgisinin sepete doğru geldiği kontrol ediliyor.");
        String cartSize = methods.getText(By.xpath("//div[@class='cartItem__attr -size']//span[@class='cartItem__attrValue']"));
        String cartPrice = methods.getText(By.xpath("//div[@class='cartItem__prices']//span[@class='cartItem__price -sale']"));
        String normalPrice = methods.getText(By.cssSelector("[class='cartItem__price -old -labelPrice']"));
        Assert.assertEquals(size, cartSize);
        Assert.assertEquals(discountedPrice, cartPrice);

        logger.info("Sepetteki ürünün eski fiyatının indirimli fiyatından büyük olduğu kontrol ediliyor.");
        Assert.assertNotEquals(cartPrice, normalPrice);

        logger.info("Devam et butonuna tıklanır.");
        methods.click(By.cssSelector("[class='continueButton n-button large block text-center -primary']"));

        logger.info("E-posta alanına csv dosyasından okunan e-posta giriliyor.");
        methods.readEmailFromCsv("n-input-email");

        logger.info("Şifre alanına csv dosyasından okunan şifre giriliyor.");
        methods.readPasswordFromCsv("n-input-password");

        logger.info("Giriş yap butonunun geldiği kontrol ediliyor.");
        methods.isElementVisible(By.cssSelector("[class='n-button large block text-center -primary ']"));

        logger.info("Network logosuna tıklanır.");
        methods.click(By.cssSelector("[class='headerCheckout__logo']"));

        logger.info("Anasayfa'nın sağ üstünde bulunan çanta logosuna tıklanarak sepet ekranın sağ tarafında açılır.");
        methods.click(By.cssSelector("button[class='header__basketTrigger js-basket-trigger -desktop']"));

        logger.info("Ürün sepetten çıkarılır.");
        methods.click(By.cssSelector("[class='header__basketProductRemove']"));
        methods.click(By.cssSelector("button[data-remove-cart-item]"));

        logger.info("Sepetin boş olduğu kontrol ediliyor.");
        methods.isElementInvisible(By.cssSelector("[class='header__basket--count']"));



    }



}

