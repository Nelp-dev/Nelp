package us;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomePageTest {
    private WebDriver driver;
    @Before
    public void setUpSelenium(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver-mac");
        driver = new ChromeDriver();
    }

    @Test
    public void test_home_title(){
        driver.get("http://localhost:8080/");
        Assert.assertThat(driver.getTitle(),is("홈"));
        driver.quit();
    }
}
