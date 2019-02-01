package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.Product;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ResultOfSearchPage {
    private MainPage mainPage;

    public ResultOfSearchPage() {
        mainPage = new MainPage();
    }


    private SelenideElement resultNumberOfProduct = $x("//div[@class='col-md-6 hidden-sm-down total-products']/p");

    private ElementsCollection searchedProducts = $$x("//div[@class='products row']/article");

    //TODO make shother
    private ElementsCollection linksToPages = $$x("//ul[@class='page-list clearfix text-xs-center']/li[@class='current']/a");

    private SelenideElement nextPageButton = $x("//a[@rel='next']");

    private SelenideElement typeOfSortButton = $x("//a[@class='select-title']");

    private ElementsCollection tupeOfSortButtons = $$x("//div[@class='dropdown-menu']/a");

    private SelenideElement products = $(By.id("products"));

    @Step("Get number of products from write page result")
    public int getWritedNumberOfProducts() {
        resultNumberOfProduct.shouldBe(Condition.visible);
        return Integer.parseInt(resultNumberOfProduct.getText().replaceAll("[^0-9]", ""));
    }

    @Step("Get number of displayed products")
    public int getDisplayedNumberOfProducts() {
        return searchedProducts.size();
    }

    @Step("Get number of pages with products")
    public int getNumberOfPagesWithProducts() {
        return linksToPages.size();
    }

    @Step("Click on button \"Next page\"")
    public void clickOnNextPageButton() {
        nextPageButton.click();
    }

    @Step("Set sort from higt price to low price")
    public void setSortFromHightToLowByPrice() {
        int sortFromHihtToLowPrice = 4;
        typeOfSortButton.click();
        typeOfSortButton.shouldHave(Condition.attribute("aria-expanded", "true"));
        tupeOfSortButtons.get(sortFromHihtToLowPrice).click();
        typeOfSortButton.shouldHave(Condition.attribute("aria-expanded", "false"));
    }

    @Step("Check prices for products on one page, if true all right")
    public boolean checkCalculatedDescountPricesOnPage() {
        DecimalFormat df = new DecimalFormat("########.##");
        List<Product> products = mainPage.getProductsFromPage();

        for (Product product : products) {
            if (product.getDiscount() != 0)
                if (!df.format(product.getPrice()).equals(df.format(product.getRegularPrice() * (1 - (Double.valueOf(product.getDiscount())) / 100))))
                    return false;
        }
        return true;

    }

    @Step("Check sort fof higt price to low price, if true all right")
    public boolean checkSortPricesFromHigtToLowOnPage() {
        List<Product> products = mainPage.getProductsFromPage();
        for (int i = 0; i < products.size(); i++) {
            if (i < (products.size() - 1)) {
                if (products.get(i).getDiscount() == 0) {
                    if (!(products.get(i).getPrice() >= products.get(i + 1).getPrice()))
                        return false;
                } else {
                    if (!(products.get(i).getRegularPrice() >= products.get(i + 1).getPrice()))
                        return false;
                }
            }
        }
        return true;
    }

    @Step("Check wrote number of found products. Compare with displayed products")
    public boolean checkWrotePriceOfFound() {
        return getWritedNumberOfProducts() == getDisplayedNumberOfProducts();
    }
}
