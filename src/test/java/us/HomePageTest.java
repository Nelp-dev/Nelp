package us;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomePageTest {
    private WebDriver driver;
    private String baseURL = "http://localhost:8080/";
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver");
        driver = new ChromeDriver();
    }
    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void test_home_title(){
        driver.get(baseURL);
        Assert.assertThat(driver.getTitle(),is("Nelp"));
    }

    @Test
    public void test_home_login(){
        driver.get(baseURL);
        driver.findElement(By.id("login_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL+"login"));
    }

    @Test
    public void test_home_signup(){
        driver.get(baseURL);
        driver.findElement(By.id("signup_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL+"signup"));
    }
}
