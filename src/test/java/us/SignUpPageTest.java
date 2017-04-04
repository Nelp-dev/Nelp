package us;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignUpPageTest extends BaseTest {
    public SignUpPageTest() {
        baseURL = HOME_URL + "signup/";
    }

    public void inputUserData(String ssoId,String name,String password,String accout_number){
        driver.findElement(By.id("signup_ssoId")).sendKeys(ssoId);
        driver.findElement(By.id("signup_name")).sendKeys(name);
        driver.findElement(By.id("signup_password")).sendKeys(password);
        driver.findElement(By.id("signup_account_number")).sendKeys(accout_number);
    }

    @Test
    public void test_signUp_title(){
        Assert.assertThat(driver.getTitle(),is("Sign-Up"));
    }

    @Test
    public void test_signUp_reset(){
        inputUserData("Test_ssoId@Test.com","Test_name","Test_password","Test_accountNumber");
        driver.findElement(By.id("signup_reset_btn")).click();
        Assert.assertThat(driver.findElement(By.id("signup_ssoId")).getText(),is(""));
        Assert.assertThat(driver.findElement(By.id("signup_name")).getText(),is(""));
        Assert.assertThat(driver.findElement(By.id("signup_password")).getText(),is(""));
        Assert.assertThat(driver.findElement(By.id("signup_account_number")).getText(),is(""));
    }

    @Test
    public void test_signUp_submit_success(){
        inputUserData("Test_ssoId@Test.com","Test_name","Test_password","Test_accountNumber");
        driver.findElement(By.id("signup_submit_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(HOME_URL));
    }

    @Test
    public void test_signUp_submit_fail(){
        inputUserData("failTest","Test_name","Test_password","Test_accountNumber");
        driver.findElement(By.id("signup_submit_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(baseURL));
    }
}