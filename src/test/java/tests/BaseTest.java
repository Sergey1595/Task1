package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ResultOfSearchPage;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {
    MainPage mainPage;
    ResultOfSearchPage resultOfSearchPage;

    @BeforeTest
    @Parameters
    public void setUp() {
        //System.setProperty("webdriver.chrome.driver", "/home/sergey/Downloads/chromedriver");
        //System.setProperty("selenide.browser", "Chrome");
        Configuration.browser="firefox";
        open("http://prestashop-automation.qatestlab.com.ua/ru/");
    }


    @Test
    public void test1() {
        mainPage = new MainPage();

        Assert.assertTrue(mainPage.checkCurrencyOfProductsOnPage(mainPage.getCurrenceOfPageSetting()), "Product currency is incorrect");

        mainPage.setDollarCurrence();
        mainPage.searchProducts("dress");

        resultOfSearchPage = new ResultOfSearchPage();

        Assert.assertTrue(resultOfSearchPage.checkWrotePriceOfFound(), "Number of wrote products and displayed products does not match");
        Assert.assertTrue(mainPage.checkCurrencyOfProductsOnPage(mainPage.getCurrenceOfPageSetting()), "Currency of found products is not a dollar");

        resultOfSearchPage.setSortFromHightToLowByPrice();

        Assert.assertTrue(resultOfSearchPage.checkSortPricesFromHigtToLowOnPage(), "Sort from hight price to low price does not work correctly");
        Assert.assertTrue(resultOfSearchPage.checkCalculatedDescountPricesOnPage(), "Price with discount calculated incorrectly");

    }
}
