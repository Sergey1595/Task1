package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.Properties;

import java.util.LinkedList;
import java.util.List;

public class MainPage extends BasePage {
    @FindBy (xpath = "//span[@class='expand-more _gray-darker hidden-sm-down']")
    private WebElement сurrencyButton;

    @FindBy (xpath = "//span[@class='price']")
    private List<WebElement> pricesOfProducts;

    @FindBy (xpath = "//a[@title='Доллар США']")
    private WebElement setDollarCurrenceButton;

    @FindBy (name = "s")
    private WebElement searchField;

    @FindBy (xpath = "//button/i[@class='material-icons search']")
    private WebElement searchButton;

    @Step("Set currence - dollar")
    public void setDollarCurrence(){
        сurrencyButton.click();
        driverWait.waitForElementToBeClickable(setDollarCurrenceButton);
        setDollarCurrenceButton.click();
    }

    @Step("Get currence of page setting")
    public String getCurrenceOfPageSetting(){
        return сurrencyButton.getText().substring(4);
    }


    @Step("Get currence of all products")
    public List<String> getCurrenceOfProducts(){
        List<String> currenceOfProducts = new LinkedList<>();
        for(int i = 0; i < pricesOfProducts.size(); i++)
            currenceOfProducts.add(pricesOfProducts.get(i).getText().replaceAll("[0-9, ]", ""));
        return currenceOfProducts;
    }

    @Step("Search products by name")
    public void searchProducts(String nameOfProduct){
        searchField.sendKeys(nameOfProduct);
        driverWait.waitForTextToBePresentInElement(nameOfProduct, searchField);
        searchButton.click();

    }
}
