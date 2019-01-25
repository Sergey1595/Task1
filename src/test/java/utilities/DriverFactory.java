package utilities;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    public static WebDriver initDriver(String browser) {
        DesiredCapabilities capabilities;
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
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                capabilities.setCapability("requireWindowFocus", true);
                return new InternetExplorerDriver(capabilities);
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

    public static WebDriver initDriver(String browser, String gridUrl) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(DriverFactory.class.getResource("/geckodriver.exe").getFile()).getPath());
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                try {
                    return new RemoteWebDriver(new URL(gridUrl), optionsFirefox);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                return null;

            case "ie":
            case "internet explorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(DriverFactory.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                InternetExplorerOptions ieOptions = new InternetExplorerOptions().
                        requireWindowFocus().
                        setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT).
                        enablePersistentHovering().
                        destructivelyEnsureCleanSession();
                ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                try {
                    return new RemoteWebDriver(new URL(gridUrl), ieOptions);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                return null;
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
                ChromeOptions optionsChrome = new ChromeOptions();
                try {
                    return new RemoteWebDriver(new URL(gridUrl), optionsChrome);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                return null;
        }
    }
}
