package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;

import java.util.List;

public class TestTask1 extends BaseTest{

    @Test
    void compareCurrencyOfProducts(){
        MainPage mainPage = new MainPage().open();

        String currenceOfPageSetting = mainPage.getCurrenceOfPageSetting().substring(4);

        List <String> currencyOfProducts = mainPage.getCurrenceOfProducts();
        for(int i = 0; i < currencyOfProducts.size(); i++){
            currencyOfProducts.set(i, currencyOfProducts.get(i).replaceAll("[0-9, ]", ""));
            Assert.assertEquals(currenceOfPageSetting, currencyOfProducts.get(i), "Currency didn`t match");
        }
    }


}
