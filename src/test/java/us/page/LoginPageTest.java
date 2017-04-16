package us.page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.base.HaveUserBaseTest;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginPageTest extends HaveUserBaseTest {
    public LoginPageTest() {
        START_URL = BASE_URL + "login";
    }

    @Test
    public void test_login_title(){
        Assert.assertThat(driver.getTitle(),is("Login"));
    }
    @Test
    public void test_login_signupBTN(){
        driver.findElement(By.id("login_signup_btn")).click();
        Assert.assertThat(driver.getTitle(),is("Sign-Up"));
    }
    @Test
    public void test_login_loginBTN(){
        super.login(test_users.get(0));
    }

}
