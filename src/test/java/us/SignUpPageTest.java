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
public class SignUpPageTest {
    private WebDriver driver;
    private String baseURL = "http://localhost:8080/signup";

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver");
        driver = new ChromeDriver();

    }
    @After
    public void tearDown(){
        driver.quit();
    }

    public void inputUserData(String ssoId,String name,String password,String accout_number){
        driver.findElement(By.id("signup_ssoId")).sendKeys(ssoId);
        driver.findElement(By.id("signup_name")).sendKeys(name);
        driver.findElement(By.id("signup_password")).sendKeys(password);
        driver.findElement(By.id("signup_account_number")).sendKeys(accout_number);
    }


    @Test
    public void test_signUp_title(){
        driver.get(baseURL);
        Assert.assertThat(driver.getTitle(),is("Sign-Up"));
    }


    @Test
    public void test_signUp_reset(){
        driver.get(baseURL);
        inputUserData("Test_ssoId@Test.com","Test_name","Test_password","Test_accountNumber");
        driver.findElement(By.id("signup_reset_btn")).click();
        Assert.assertThat(driver.findElement(By.id("signup_ssoId")).getText(),is(""));
        Assert.assertThat(driver.findElement(By.id("signup_name")).getText(),is(""));
        Assert.assertThat(driver.findElement(By.id("signup_password")).getText(),is(""));
        Assert.assertThat(driver.findElement(By.id("signup_account_number")).getText(),is(""));
    }

    @Test
    public void test_signUp_submit_success(){
        driver.get(baseURL);
        inputUserData("Test_ssoId@Test.com","Test_name","Test_password","Test_accountNumber");
        driver.findElement(By.id("signup_submit_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is("http://localhost:8080/"));
    }

    @Test
    public void test_signUp_submit_fail(){
        driver.get(baseURL);
        inputUserData("failTest","Test_name","Test_password","Test_accountNumber");
        driver.findElement(By.id("signup_submit_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL));
    }
}