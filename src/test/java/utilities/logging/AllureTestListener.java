package utilities.logging;

import ru.yandex.qatools.allure.annotations.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import tests.BaseTest;

public class AllureTestListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot();
        attachPageSource();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        byte[] screenshotAs = null;
        try {
            screenshotAs = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            fail(e);
        }
        return screenshotAs;
    }

    @Attachment(value = "Page source", type = "text/html")
    private String attachPageSource() {
        try {
            return BaseTest.driver.getPageSource();
        } catch (Exception e) {
            fail(e);
        }
        return null;
    }

    @Attachment(value = "Unable to save page data")
    private String fail(Exception e) {
        return e.getMessage() + "\n" + e.getStackTrace().toString();
    }
}
