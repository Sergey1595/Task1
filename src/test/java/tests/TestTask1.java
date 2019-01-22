package tests;

import org.testng.annotations.Test;
import pages.MainPage;

public class TestTask1 extends BaseTest{

    @Test
    void compareCurrencyOfProducts(){
        MainPage mainPage = new MainPage().open();
    }
}
