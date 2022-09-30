package com.testinium.tests;

import com.opencsv.exceptions.CsvValidationException;
import com.testinium.driver.BaseTest;
import com.testinium.pages.ProductPage;
import org.junit.Test;

import java.io.IOException;

public class ProductPageTest extends BaseTest {

    @Test
    public void productPageTest() throws CsvValidationException, IOException, InterruptedException {
        ProductPage productPage = new ProductPage();

        productPage.selectProductAndAddToCartThenRemove();


    }


}