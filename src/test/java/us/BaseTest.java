package us;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected static WebDriver driver;
    protected String baseURL;

    @BeforeClass
    public static void setUpClass(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void tearDownClass(){
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get(baseURL);
    }
}
