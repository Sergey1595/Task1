package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ResultOfSearchPage;

import java.text.DecimalFormat;
import java.util.List;

public class TestTask1 extends BaseTest{
    MainPage mainPage = null;
    ResultOfSearchPage resultOfSearchPage = null;

    @Test
    void compareCurrencyOfProducts(){
        mainPage = new MainPage();

        //check correct setting of currency on page
        String currenceOfPageSetting = mainPage.getCurrenceOfPageSetting();
        List <String> currencyOfProducts = mainPage.getCurrenceOfProducts();
        for(int i = 0; i < currencyOfProducts.size(); i++)
            Assert.assertEquals(currenceOfPageSetting, currencyOfProducts.get(i), "Currency didn`t match");

        mainPage.setDollarCurrence();
        mainPage.searchProducts("dress");

        resultOfSearchPage = new ResultOfSearchPage();
        resultOfSearchPage.setSortFromHightToLowByPrice();

        int foundWroteNumberOfProduct = resultOfSearchPage.getWritedNumberOfProducts();
        int foundDisplayedNumberOfProduct = 0;

        for(int i = 0;i < resultOfSearchPage.getNumberOfPagesWithProducts(); i++){
            DecimalFormat df = new DecimalFormat("########.##");

            foundDisplayedNumberOfProduct += resultOfSearchPage.getDisplayedNumberOfProducts();

            //get discount, price, discountPrice
            List<Integer> discounts = resultOfSearchPage.getDiscountsOfAllProducts();
            List<Double> prices = resultOfSearchPage.getPricesOfAllProduct();
            List<Double> regularPrice = resultOfSearchPage.getRegularPricesOfAllProduct();

            //check currency of products (need after get discount, price, discountPrice because need wait of load new products)
            currencyOfProducts = mainPage.getCurrenceOfProducts();

            double calculatedPrice = 0;
            for(int j = 0; j < currencyOfProducts.size(); j++){
                Assert.assertEquals("$", currencyOfProducts.get(j), "Currency of products were not a dollar");

                //check to correct sort by price from hight to low. If product have a discount then use a regularPrice
                if(j < (currencyOfProducts.size() - 1)){
                    if(discounts.get(j) == 0){
                        Assert.assertTrue(prices.get(j) >= prices.get(j + 1), "Products sorted incorrect by price from hight to low");
                    }else{
                        Assert.assertTrue(regularPrice.get(j) >= prices.get(j + 1), "Products sorted incorrect by price from hight to low");
                    }
                }

                //check correct of calculate price with discount
                if(discounts.get(j) != 0){
                    Assert.assertEquals(df.format(prices.get(j)), df.format(regularPrice.get(j) * (1 - ((discounts.get(j).doubleValue()))/100)), "Calculated price after discount and price from page didnt match");
                }
            }
            if(resultOfSearchPage.getNumberOfPagesWithProducts() != 1)
                resultOfSearchPage.clickOnNextPageButton();
        }

        //check number of products
        Assert.assertEquals(foundWroteNumberOfProduct, foundDisplayedNumberOfProduct, "Number of wrote number of products and number of displayed product didnt match");
    }

}
