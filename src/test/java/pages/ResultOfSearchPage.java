package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import tests.BaseTest;

import java.util.List;

public class ResultOfSearchPage extends BasePage {
    @FindBy(xpath = "//div[@class='col-md-6 hidden-sm-down total-products']/p")
    private WebElement resultNumberOfProduct;

    @FindBy(xpath = "//div[@class='products row']/article")
    private List<WebElement> searchedProducts;

    @FindBy(xpath = "//ul[@class='page-list clearfix text-xs-center']/li[@class='current']/a")
    private List<WebElement> linksToPages;

    @FindBy(xpath = "//a[@rel='next']")
    private WebElement NextPageButton;

    @Step("Get number of products from write page result")
    public int getWritedNumberOfProducts(){
        return Integer.parseInt(resultNumberOfProduct.getText().replaceAll("[^0-9]", ""));
    }

    @Step("Get number of displayed products")
    public int getDisplayedNumberOfProducts(){
        return searchedProducts.size();
    }

    @Step("Get number of pages with products")
    public int getNumberOfPagesWithProducts(){
        return linksToPages.size();
    }

    @Step("Click on button \"Next page\"")
    public  void clickOnNextPageButton(){
        driverWait.waitForElementToBeClickable(NextPageButton);
        NextPageButton.click();
    }
}
