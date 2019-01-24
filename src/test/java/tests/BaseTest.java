package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.DriverFactory;
import utilities.Properties;
import utilities.logging.AllureTestListener;

import java.util.concurrent.TimeUnit;

@Listeners({AllureTestListener.class})
public abstract class BaseTest {
    public static WebDriver driver = null;

    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("chrome")String browser, @Optional("") String gridUrl) {

        if(gridUrl.equalsIgnoreCase("")){
            driver = DriverFactory.initDriver(browser);
        }
        else{
            driver = DriverFactory.initDriver(browser, gridUrl);
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
