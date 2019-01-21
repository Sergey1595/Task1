package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;

public class DriverFactory {
    public static WebDriver initDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(DriverFactory.class.getResource("/geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
            case "internet explorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(DriverFactory.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                InternetExplorerOptions ieOptions = new InternetExplorerOptions()
                        .requireWindowFocus()
                        .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT)
                        .enablePersistentHovering()
                        .destructivelyEnsureCleanSession();
                ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                return new InternetExplorerDriver(ieOptions);
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }
}
