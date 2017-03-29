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
import static org.hamcrest.CoreMatchers.*;

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
    public void test_home_click_login(){
        driver.get(baseURL);
        driver.findElement(By.id("login")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL+"login"));
    }

    @Test
    public void test_home_click_signup(){
        driver.get(baseURL);
        driver.findElement(By.id("signup")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL+"signup"));
    }

    @Test
    public void test_home_click_logout(){
        driver.get(baseURL);
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("admin@nelp.us");
        driver.findElement(By.id("inputPassword")).sendKeys("1234");
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.id("logout")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL));
    }

    @Test
    public void test_home_click_newMeeting(){
        driver.get(baseURL);
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("inputEmail")).sendKeys("admin@nelp.us");
        driver.findElement(By.id("inputPassword")).sendKeys("1234");
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.id("new")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL+"meetings/new"));
    }

}
