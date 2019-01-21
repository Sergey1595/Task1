package pages;

import org.openqa.selenium.WebDriver;
import utilities.logged.JavascriptExecutorLogged;
import utilities.logged.WebDriverWaitLogged;
import tests.BaseTest;


public abstract class BasePage {
    private WebDriver driver = null;
    protected WebDriverWaitLogged driverWait = null;
    private JavascriptExecutorLogged jsExecutor = null;

    public BasePage() {
        driver = BaseTest.driver;
        driverWait = new WebDriverWaitLogged(driver);
        jsExecutor = new JavascriptExecutorLogged(driver);
    }

    protected void open(String url) {
        driver.navigate().to(url);
    }

    protected void scrollPageDown() {
        jsExecutor.executeScript("window.scrollBy(0,document.documentElement.scrollHeight)");
    }
}
