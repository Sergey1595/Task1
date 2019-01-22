package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.DriverFactory;
import utilities.logging.AllureTestListener;

import java.util.concurrent.TimeUnit;

//@Listeners({AllureTestListener.class})
public abstract class BaseTest {
    public static WebDriver driver = null;

    @BeforeClass
    @Parameters({"selenium.browser"})
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
