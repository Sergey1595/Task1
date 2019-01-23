package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import tests.BaseTest;

import java.util.LinkedList;
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

    @FindBy(xpath = "//div[@class='product-price-and-shipping']")
    private List<WebElement> fullPricesOfProducts;

    @FindBy(id = "products")
    private WebElement products;

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
    public void clickOnNextPageButton(){
        driverWait.waitForElementToBeClickable(nextPageButton);
        nextPageButton.click();
    }

    @Step("Set sort from higt price to low price")
    public void setSortFromHightToLowByPrice(){
        driverWait.waitForElementToBeClickable(typeOfSortButton);
        typeOfSortButton.click();
        driverWait.waitForElementAtributeToBe(typeOfSortButton, "aria-expanded", "true");
        driverWait.waitForElementToBeClickable(tupeOfSortButtons.get(4));
        tupeOfSortButtons.get(4).click();

    }

    @Step("Get list with discounts")
    public List<Integer> getDiscountsOfAllProducts(){
        List<Integer> discounts = new LinkedList<Integer>();

        driverWait.waitForUrlContain("price");
        for(WebElement element : fullPricesOfProducts){
            driverWait.waitForElementVisibility(element.findElement(By.xpath("./span")));
            if(element.findElements(By.xpath("./span")).size() > 1){
                WebElement discount = element.findElement(By.xpath("./span[@class='discount-percentage']"));
                discounts.add(Integer.parseInt(discount.getText().replaceAll("[^0-9]", "")));
            }else{
                discounts.add(0);
            }
        }
        return discounts;
    }

    @Step("Get list with prices")
    public List<Double> getPricesOfAllProduct(){
        List<Double> prices = new LinkedList<Double>();
        driverWait.waitForUrlContain("price");
        for(WebElement element : fullPricesOfProducts){
            driverWait.waitForElementVisibility(element);
            WebElement price = element.findElement(By.xpath("./span[@class='price']"));
            prices.add(Double.parseDouble(price.getText().replaceAll("[^0-9,]", "").replaceAll("[,]", ".")));
        }
        return prices;
    }


    @Step("Get list with discount prices")
    public List<Double> getRegularPricesOfAllProduct(){
        List<Double> prices = new LinkedList<Double>();
        driverWait.waitForUrlContain("price");
        for(WebElement element : fullPricesOfProducts){
            driverWait.waitForElementVisibility(element);
            if(element.findElements(By.xpath("./span")).size() > 1){
                WebElement price = element.findElement(By.xpath("./span[@class='regular-price']"));
                prices.add(Double.parseDouble(price.getText().replaceAll("[^0-9,]", "").replaceAll("[,]", ".")));
            }else{
                prices.add(0.0);
            }
        }
        return prices;
    }


}
