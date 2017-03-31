package us;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.model.User;
import us.repository.UserRepository;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by jihun on 2017. 3. 31..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingTest {
    private WebDriver driver;
    private String baseURL = "http://localhost:8080/";
    @Autowired
    private UserRepository userRepository;
    private User test_user;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver");
        driver = new ChromeDriver();

        test_user = new User("test@email.com", "test name", "test account number", "test password");
        if(!userRepository.exists(test_user.getSsoId()))
            userRepository.save(test_user);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void test_user_create_meeting(){
        driver.get(baseURL);
        driver.findElement(By.id("login")).click();

        WebElement email_box = driver.findElement(By.id("login_email_input"));
        email_box.sendKeys(test_user.getSsoId());
        email_box.sendKeys(Keys.ENTER);

        WebElement password_box = driver.findElement(By.id("login_password_input"));
        password_box.sendKeys(test_user.getPassword());
        password_box.sendKeys(Keys.ENTER);

        driver.findElement(By.id("login-submit")).click();
        Assert.assertEquals(driver.getCurrentUrl(), baseURL+"login");
    }
}
