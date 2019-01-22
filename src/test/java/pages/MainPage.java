package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;
import utilities.Properties;

import java.util.LinkedList;
import java.util.List;

public class MainPage extends BasePage {
    WebDriver driver;

    @FindBy (className = "expand-more _gray-darker hidden-sm-down")
    private WebElement сurrencyButton;

    @FindBy (xpath = "//span[@class='price']")
    private List<WebElement> pricesOfProducts;

    @FindBy (xpath = "//a[@title='Доллар США']")
    private WebElement setDollarCurrenceButton;

    @FindBy (name = "s")
    private WebElement searchField;

    @FindBy (xpath = "//button/i[@class='material-icons search']")
    private WebElement searchButton;

    @Step("Open main page")
    public MainPage open(){
        open(Properties.getBaseUrl());
        return this;
    }

    @Step("Set currence - dollar")
    public void setDollarCurrence(){
        сurrencyButton.click();
        driverWait.waitForElementToBeClickable(setDollarCurrenceButton);
        setDollarCurrenceButton.click();
    }


    @Step("Get currence of all products")
    public List<String> getCurrenceOfProducts(){
        List<String> currenceOfProducts = new LinkedList<>();
        for(int i = 0; i < pricesOfProducts.size(); i++){
            currenceOfProducts.add(pricesOfProducts.get(i).getText());
        }
        return currenceOfProducts;
    }

    @Step("Search products by name")
    public void searchProducts(String nameOfProduct){
        searchField.sendKeys(nameOfProduct);
        driverWait.waitForTextToBePresentInElement(nameOfProduct, searchField);
        searchButton.click();

    }
}
