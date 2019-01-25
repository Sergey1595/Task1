package tests;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import utilities.DriverFactory;
import utilities.Properties;
import utilities.logging.AllureTestListener;
import utilities.logging.EventHandler;

import java.util.concurrent.TimeUnit;

@Listeners({AllureTestListener.class})
public abstract class BaseTest {
    public static EventFiringWebDriver driver = null;

    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("chrome") String browser, @Optional("") String gridUrl) {
        if (gridUrl.equalsIgnoreCase("")) {
            driver = new EventFiringWebDriver(DriverFactory.initDriver(browser));
            driver.register(new EventHandler());
        } else {
            driver = new EventFiringWebDriver(DriverFactory.initDriver(browser, gridUrl));
            driver.register(new EventHandler());
        }

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        driver.navigate().to(Properties.getBaseUrl());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
