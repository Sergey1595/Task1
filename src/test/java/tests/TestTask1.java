package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ResultOfSearchPage;

import java.util.List;

public class TestTask1 extends BaseTest{
    MainPage mainPage = null;
    ResultOfSearchPage resultOfSearchPage = null;

    @Test
    void compareCurrencyOfProducts(){
        mainPage = new MainPage();

        String currenceOfPageSetting = mainPage.getCurrenceOfPageSetting();

        List <String> currencyOfProducts = mainPage.getCurrenceOfProducts();
        for(int i = 0; i < currencyOfProducts.size(); i++)
            Assert.assertEquals(currenceOfPageSetting, currencyOfProducts.get(i), "Currency didn`t match");
    }

    @Test
    void checkResultOfSearch(){
        mainPage = new MainPage();
        mainPage.setDollarCurrence();
        mainPage.searchProducts("dress");

        resultOfSearchPage = new ResultOfSearchPage();
        int foundWroteNumberOfProduct = resultOfSearchPage.getWritedNumberOfProducts();
        int foundDisplayedNumberOfProduct = 0;

        if(resultOfSearchPage.getNumberOfPagesWithProducts() == 1){
            //compare found number of products and  displayed number of products on 1 page
            foundDisplayedNumberOfProduct = resultOfSearchPage.getDisplayedNumberOfProducts();
            Assert.assertEquals(foundWroteNumberOfProduct, foundDisplayedNumberOfProduct, "Number of wrote number of products and number of displayed product didnt match");

            //check dollar currency for all products on 1 page
            List <String> currencyOfProducts = mainPage.getCurrenceOfProducts();
            for(int i = 0; i < currencyOfProducts.size(); i++)
                Assert.assertEquals("$", currencyOfProducts.get(i), "Currency of products were not a dollar");
        }else{
            List <String> currencyOfProducts = null;

            //Sum products on all pages and check dollar currency for all products on all pages.
            for(int i = 0; i < resultOfSearchPage.getNumberOfPagesWithProducts(); i++){
                foundDisplayedNumberOfProduct += resultOfSearchPage.getDisplayedNumberOfProducts();

                currencyOfProducts = mainPage.getCurrenceOfProducts();
                for(int j = 0; j < currencyOfProducts.size(); j++)
                    Assert.assertEquals("$", currencyOfProducts.get(j), "Currency of products were not a dollar");
                resultOfSearchPage.clickOnNextPageButton();
            }
            //compare found number of products and  displayed number of products on all pages
            Assert.assertEquals(foundWroteNumberOfProduct, foundDisplayedNumberOfProduct, "Number of wrote number of products and number of displayed product didnt match");
        }
    }

}
