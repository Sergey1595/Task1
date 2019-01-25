package pages;

import io.qameta.allure.Step;
import models.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.DecimalFormat;
import java.util.List;

public class ResultOfSearchPage extends BasePage {
    @FindBy(xpath = "//div[@class='col-md-6 hidden-sm-down total-products']/p")
    private WebElement resultNumberOfProduct;

    @FindBy(xpath = "//div[@class='products row']/article")
    private List<WebElement> searchedProducts;

    //TODO make shother
    @FindBy(xpath = "//ul[@class='page-list clearfix text-xs-center']/li[@class='current']/a")
    private List<WebElement> linksToPages;

    @FindBy(xpath = "//a[@rel='next']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//a[@class='select-title']")
    private WebElement typeOfSortButton;

    @FindBy(xpath = "//div[@class='dropdown-menu']/a")
    private List<WebElement> tupeOfSortButtons;


    @FindBy(id = "products")
    private WebElement products;

    @Step("Get number of products from write page result")
    public int getWritedNumberOfProducts() {
        driverWait.waitForElementVisibility(resultNumberOfProduct);
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
        driverWait.waitForElementToBeClickable(nextPageButton);
        nextPageButton.click();
    }

    @Step("Set sort from higt price to low price")
    public void setSortFromHightToLowByPrice() {
        int sortFromHihtToLowPrice = 4;
        driverWait.waitForElementToBeClickable(typeOfSortButton);
        typeOfSortButton.click();
        driverWait.waitForElementAtributeToBe(typeOfSortButton, "aria-expanded", "true");
        driverWait.waitForElementToBeClickable(tupeOfSortButtons.get(sortFromHihtToLowPrice));
        tupeOfSortButtons.get(4).click();

    }


    @Step("Check prices for products on one page, if true all right")
    public boolean checkCalculatedDescountPricesOnPage() {
        DecimalFormat df = new DecimalFormat("########.##");
        MainPage mainPage = new MainPage();
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
        MainPage mainPage = new MainPage();
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
