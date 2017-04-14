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
public class DetailMeetingPageTest extends BaseTest {
    public DetailMeetingPageTest() {
        START_URL = BASE_URL + "meetings/2";
    }

    @Test
    public void test_not_login_show_meeting_info() {
        /* 로그인을 안한 상태에서 Detail 정보가 보여야 한다. */

        // 미팅 URL로 접근하면 미팅정보가 보인다
        driver.get(START_URL);
        Assert.assertThat(driver.getTitle(), is("Detail Meeting"));

        // 참가버튼을 눌렀을때 로그인화면으로 넘어간다.
        driver.findElement(By.id("join_meeting_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(), is(BASE_URL + "login"));

    }

    @Test
    public void test_login_show_meeting_info() {
        /* 로그인한 상태에서 Detail 정보가 보여야 한다. */
        // 로그인을 한다
        driver.findElement(By.id("login_btn")).click();
        driver.findElement(By.id("login_email_input")).sendKeys("test0@email.com");
        driver.findElement(By.id("login_password_input")).sendKeys("test password0");
        driver.findElement(By.id("login_submit_btn")).click();

        // 미팅 URL로 접근하면 미팅정보가 보인다
        driver.get(START_URL);
        Assert.assertThat(driver.getTitle(), is("Detail Meeting"));

        // 참가버튼을 눌렀을때 참가할 수 있다
        driver.findElement(By.id("join_meeting_btn")).click();
        Assert.assertThat(driver.getCurrentUrl(), is(START_URL));
    }

}
