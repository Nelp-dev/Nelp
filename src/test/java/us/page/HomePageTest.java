package us.page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.base.BaseTest;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomePageTest extends BaseTest {
    @Test
    public void test_home_title(){
        Assert.assertThat(driver.getTitle(),is("Nelp"));
    }

    @Test
    public void test_home_login(){
        driver.findElement(By.id("login_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(BASE_URL+"login"));
    }

    @Test
    public void test_home_signup(){
        driver.findElement(By.id("signup_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(),is(BASE_URL+"signup"));
    }
}
