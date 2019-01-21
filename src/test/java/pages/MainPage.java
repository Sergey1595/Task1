package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.logged.WebDriverWaitLogged;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainPage extends BasePage {
    WebDriver driver = null;
    WebDriverWaitLogged wait = null;
    public MainPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;

        wait = new WebDriverWaitLogged(driver);
    }

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

    public void setDollarCurrence(){
        сurrencyButton.click();
        wait.waitForElementToBeClickable(setDollarCurrenceButton);
        setDollarCurrenceButton.click();
    }

    public List<String> getCurrenceOfProducts(){
        List<String> currenceOfProducts = new LinkedList<>();
        for(int i = 0; i < pricesOfProducts.size(); i++){
            currenceOfProducts.add(pricesOfProducts.get(1).getText());
        }
        return currenceOfProducts;
    }

    public void searchProducts(String nameOfProduct){
        searchField.sendKeys(nameOfProduct);
        //wait need here
        searchButton.click();
    }
}
