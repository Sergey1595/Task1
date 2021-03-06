package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebDriverWaitLogged {
    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriverWaitLogged(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    public void waitForElementInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForTextToBePresentInElement(String text, WebElement element) {
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    public void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementAtributeToBe(WebElement element, String atribute, String valueOfAtribute) {
        wait.until(ExpectedConditions.attributeToBe(element, atribute, valueOfAtribute));
    }

    public void waitForUrlContain(String str) {
        wait.until(ExpectedConditions.urlContains(str));
    }

    public void waitForfElementsVisibility(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

}
