package pages;

import io.qameta.allure.Step;
import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.blocks.СurrencySettingWebElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainPage extends BasePage {

    @FindBy(xpath = "//span[@class='price']")
    private List<WebElement> pricesOfProducts;

    @FindBy(name = "s")
    private WebElement searchField;

    @FindBy(xpath = "//div[@id='search_widget']//button")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='product-price-and-shipping']")
    private List<WebElement> fullPricesOfProducts;

    @FindBy(xpath = "//span[@class='expand-more _gray-darker hidden-sm-down']")
    private WebElement сurrencyButton;

    @FindBy(xpath = "//li/a[.='USD $']")
    private WebElement setDollarCurrenceButton;

    @Step("Set currence - dollar")
    public void setDollarCurrence() {
        сurrencyButton.click();
        setDollarCurrenceButton.click();
    }

    @Step("Get currence of page setting")
    public String getCurrenceOfPageSetting() {
        return сurrencyButton.getText().substring(4);
    }


    @Step("Get currence of all products")
    public List<String> getCurrenceOfProducts() {
        List<String> currenceOfProducts = new LinkedList<>();
        driverWait.waitForfElementsVisibility(pricesOfProducts);

        for (int i = 0; i < pricesOfProducts.size(); i++)
            currenceOfProducts.add(pricesOfProducts.get(i).getText().replaceAll("[0-9, ]", ""));
        return currenceOfProducts;
    }

    @Step("Search products by name")
    public void searchProducts(String nameOfProduct) {
        driverWait.waitForElementToBeClickable(searchField);
        searchField.sendKeys(nameOfProduct);
        driverWait.waitForTextToBePresentInElement(nameOfProduct, searchField);
        searchButton.click();

    }

    @Step("Check to rigt set currence of all product on page, true if all right")
    public boolean checkSettingCurrencyProductsOnPage() {
        String currencyOfPageSetting = getCurrenceOfPageSetting();
        return checkCurrencyOfProductsOnPage(currencyOfPageSetting);
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
        By spansInFullPricesOfProducts = By.xpath("./span");
        By priceInFullPricesOfProducts = By.xpath("./span[@class='price']");
        By discountPriceFullPricesOfProducts = By.xpath("./span[@class='discount-percentage']");
        By regularPriceFullPricesOfProducts = By.xpath("./span[@class='regular-price']");

        driverWait.waitForUrlContain("price");

        int numberOfProductsOnPage = fullPricesOfProducts.size();

        int[] discounts = new int[numberOfProductsOnPage];
        double[] prices = new double[numberOfProductsOnPage];
        double[] regularPrices = new double[numberOfProductsOnPage];
        List<Product> products = new ArrayList<Product>(numberOfProductsOnPage);
        List<String> currencys = getCurrenceOfProducts();

        for (int i = 0; i < numberOfProductsOnPage; i++) {
            discounts[i] = 0;
            prices[i] = 0.0;
            regularPrices[i] = 0.0;

            driverWait.waitForfElementsVisibility(fullPricesOfProducts);
            driverWait.waitForElementVisibility(fullPricesOfProducts.get(i).findElement(spansInFullPricesOfProducts));

            WebElement price = fullPricesOfProducts.get(i).findElement(priceInFullPricesOfProducts);

            //if product have discount
            if (fullPricesOfProducts.get(i).findElements(By.xpath("./span")).size() > 1) {
                WebElement discount = fullPricesOfProducts.get(i).findElement(discountPriceFullPricesOfProducts);
                WebElement regularPrice = fullPricesOfProducts.get(i).findElement(regularPriceFullPricesOfProducts);
                regularPrices[i] = Double.parseDouble(regularPrice.getText()
                        .replaceAll("[^0-9,]", "")
                        .replaceAll("[,]", "."));
                discounts[i] = (Integer.parseInt(discount.getText().replaceAll("[^0-9]", "")));
            }

            prices[i] = Double.parseDouble(price.getText()
                    .replaceAll("[^0-9,]", "")
                    .replaceAll("[,]", "."));

            products.add(new Product(prices[i], regularPrices[i], discounts[i], currencys.get(i)));
        }
        return products;
    }
}
