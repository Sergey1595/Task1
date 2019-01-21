package utilities.logged;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JavascriptExecutorLogged {
    private WebDriver driver;
    private JavascriptExecutor js;

    public JavascriptExecutorLogged(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    public void executeScript(String script, Object... args) {
        js.executeScript(script, args);
    }

}
