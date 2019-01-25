package pages;

import io.qameta.allure.Step;
import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @FindBy(xpath = "//div[@id='_desktop_currency_selector']//span[@data-toggle]")
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

        for (WebElement priceOfProduct : pricesOfProducts)
            currenceOfProducts.add(priceOfProduct.getText().replaceAll("[0-9, ]", ""));
        return currenceOfProducts;
    }

    @Step("Search products by name")
    public void searchProducts(String nameOfProduct) {
        driverWait.waitForElementToBeClickable(searchField);
        searchField.sendKeys(nameOfProduct);
        driverWait.waitForTextToBePresentInElement(nameOfProduct, searchField);
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
        By spansInFullPricesOfProducts = By.xpath("./span");
        By priceInFullPricesOfProducts = By.xpath("./span[@class='price']");
        By discountPriceFullPricesOfProducts = By.xpath("./span[@class='discount-percentage']");
        By regularPriceFullPricesOfProducts = By.xpath("./span[@class='regular-price']");

        driverWait.waitForUrlContain("price");

        int numberOfProductsOnPage = fullPricesOfProducts.size();

        List<Product> products = new ArrayList<Product>(numberOfProductsOnPage);
        List<String> currencyes = getCurrenceOfProducts();

        for (int i = 0; i < numberOfProductsOnPage; i++) {
            WebElement fullPrice = fullPricesOfProducts.get(i);
            int discounts = 0;
            double prices = 0.0;
            double regularPrices = 0.0;

            driverWait.waitForfElementsVisibility(fullPricesOfProducts);
            driverWait.waitForElementVisibility(fullPrice.findElement(spansInFullPricesOfProducts));

            WebElement price = fullPrice.findElement(priceInFullPricesOfProducts);

            //if product have discount
            if (fullPrice.findElements(By.xpath("./span")).size() > 1) {
                WebElement discount = fullPrice.findElement(discountPriceFullPricesOfProducts);
                WebElement regularPrice = fullPrice.findElement(regularPriceFullPricesOfProducts);
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
