package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.JavascriptExecutorLogged;
import utilities.WebDriverWaitLogged;
import tests.BaseTest;


public abstract class BasePage {
    private WebDriver driver;
    protected WebDriverWaitLogged driverWait;
    private JavascriptExecutorLogged jsExecutor;

    public BasePage() {
        driver = BaseTest.driver;
        driverWait = new WebDriverWaitLogged(driver);
        jsExecutor = new JavascriptExecutorLogged(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    protected void open(String url) {
        driver.navigate().to(url);
    }

    @Step
    protected void scrollPageDown() {
        jsExecutor.executeScript("window.scrollBy(0,document.documentElement.scrollHeight)");
    }
}
