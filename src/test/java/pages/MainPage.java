package pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import models.Product;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private ElementsCollection pricesOfProducts = $$x("//span[@class='price']");

    private SelenideElement searchField = $(By.name("s"));

    private SelenideElement searchButton = $x("//div[@id='search_widget']//button");

    private ElementsCollection fullPricesOfProducts = $$x("//div[@class='product-price-and-shipping']");

    private SelenideElement сurrencyButton = $x("//div[@id='_desktop_currency_selector']//span[@data-toggle]");

    private SelenideElement setDollarCurrenceButton = $x("//li/a[.='USD $']");

    @Step("Set currency - dollar")
    public void setDollarCurrence() {
        сurrencyButton.click();
        сurrencyButton.shouldBe(Condition.attribute("aria-expanded", "true"));
        setDollarCurrenceButton.click();
    }

    @Step("Get currence of page setting")
    public String getCurrenceOfPageSetting() {
        return сurrencyButton.shouldBe(visible).getText().substring(4);
    }

    @Step("Get currence of all products")
    public List<String> getCurrenceOfProducts() {
        List<String> currenceOfProducts = new LinkedList<>();
        for (SelenideElement priceOfProduct : pricesOfProducts)
            currenceOfProducts.add(priceOfProduct.getText().replaceAll("[0-9, ]", ""));
        return currenceOfProducts;
    }

    @Step("Search products by name")
    public void searchProducts(String nameOfProduct) {
        searchField.setValue(nameOfProduct).shouldHave(value(nameOfProduct));
        searchButton.click();
    }

    @Step("Check currency of products on page, true if all right")
    public boolean checkCurrencyOfProductsOnPage(String currency) {
        List<String> currencyOfProducts = getCurrenceOfProducts();
        for (String currencyOfProduct : currencyOfProducts) {
            if (!currency.equals(currencyOfProduct))
                return false;
        }
        return true;
    }

    @Step("Get list with products")
    public List<Product> getProductsFromPage() {
        String xpathSpansInFullPricesOfProducts = "./span";
        String xPathPriceInFullPricesOfProducts = "./span[@class='price']";
        String xPathDiscountPriceFullPricesOfProducts = "./span[@class='discount-percentage']";
        String xPathRegularPriceFullPricesOfProducts = "./span[@class='regular-price']";

        //wait for load first product and visible first product
        fullPricesOfProducts.shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1));
        fullPricesOfProducts.get(1).shouldBe(visible);

        int numberOfProductsOnPage = fullPricesOfProducts.size();
        System.out.println("Size = " + numberOfProductsOnPage);
        List<Product> products = new ArrayList<Product>(numberOfProductsOnPage);
        List<String> currencyes = getCurrenceOfProducts();
        for (int i = 0; i < numberOfProductsOnPage; i++) {
            SelenideElement fullPrice = fullPricesOfProducts.get(i);
            int discounts = 0;
            double prices = 0.0;
            double regularPrices = 0.0;

            fullPricesOfProducts.get(i).shouldBe(visible);

            SelenideElement price = fullPrice.$x(xPathPriceInFullPricesOfProducts).shouldBe(visible);

            //if product have discount
            if (fullPrice.$$x(xpathSpansInFullPricesOfProducts).size() > 1) {
                SelenideElement discount = fullPrice.$x(xPathDiscountPriceFullPricesOfProducts);
                SelenideElement regularPrice = fullPrice.$x(xPathRegularPriceFullPricesOfProducts);
                regularPrices = Double.parseDouble(regularPrice.getText()
                        .replaceAll("[^0-9,]", "")
                        .replaceAll("[,]", "."));
                discounts = (Integer.parseInt(discount.getText().replaceAll("[^0-9]", "")));
            }

            prices = Double.parseDouble(price.getText()
                    .replaceAll("[^0-9,]", "")
                    .replaceAll("[,]", "."));

            products.add(new Product(prices, regularPrices, discounts, currencyes.get(i)));
        }
        return products;
    }


}
