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
public class DetailMeetingPageTest extends HaveUserBaseTest {
    public DetailMeetingPageTest() {
        START_URL = BASE_URL + "meetings/17";
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
        // 참가버튼을 눌렀을때 참가할 수 있다
        participate(test_users.get(0), START_URL);
        // 미팅 URL로 접근하면 미팅정보가 보인다
        Assert.assertThat(driver.getTitle(), is("Detail Meeting"));
        Assert.assertThat(driver.getCurrentUrl(), is(START_URL));
    }

    @Test
    public void test_modify_payment_info(){
        /* Can Modify Payment Info when logged-in */
        // Login
        login(test_users.get(0));
        


    }

}
