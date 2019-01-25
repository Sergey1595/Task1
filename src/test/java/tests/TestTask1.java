package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ResultOfSearchPage;

public class TestTask1 extends BaseTest {
    MainPage mainPage;
    ResultOfSearchPage resultOfSearchPage;

    @Test
    void compareCurrencyOfProducts() {
        String dollarSymbvol = "$";
        mainPage = new MainPage();

        Assert.assertTrue(mainPage.checkCurrencyOfProductsOnPage(mainPage.getCurrenceOfPageSetting()), "Product currency is incorrect");

        mainPage.setDollarCurrence();
        mainPage.searchProducts("dress");

        resultOfSearchPage = new ResultOfSearchPage();

        Assert.assertTrue(resultOfSearchPage.checkWrotePriceOfFound(), "Number of wrote products and displayed products does not match");
        Assert.assertTrue(mainPage.checkCurrencyOfProductsOnPage(dollarSymbvol), "Currency of found products is not a dollar");

        resultOfSearchPage.setSortFromHightToLowByPrice();

        Assert.assertTrue(resultOfSearchPage.checkSortPricesFromHigtToLowOnPage(), "Sort from hight price to low price does not work correctly");
        Assert.assertTrue(resultOfSearchPage.checkCalculatedDescountPricesOnPage(), "Price with discount calculated incorrectly");
    }

}
