package us;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.model.User;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingTest extends HaveUserBaseTest {
    private User test_meeting_maker;

    @Override
    public void setUp() {
        super.setUp();
        test_meeting_maker = test_users.get(0);
    }

    @Test
    public void test_user_create_meeting(){
        super.login(test_meeting_maker);

        driver.findElement(By.id("meeting_new_btn")).click();

        Assert.assertThat(driver.getTitle(), is("Create Meeting"));

        driver.findElement(By.id("meeting_name_input")).sendKeys("test meeting name");
        driver.findElement(By.id("meeting_location_input")).sendKeys("test meeting location");
        driver.findElement(By.id("meeting_time_input")).sendKeys("test meeting time");
        driver.findElement(By.id("meeting_submit_btn")).click();

        Assert.assertThat(driver.getTitle(), is("Detail Meeting"));
        Assert.assertThat(driver.findElement(By.id("meeting_name")).getText(), is("test meeting name"));
        Assert.assertThat(driver.findElement(By.id("meeting_location")).getText(), is("test meeting location"));
        Assert.assertThat(driver.findElement(By.id("meeting_time")).getText(), is("test meeting time"));
        Assert.assertThat(driver.findElement(By.id("meeting_url")).getText(), is(BASE_URL + "meetings/1"));

        Assert.assertThat(driver.findElement(By.id("meeting_participant")).getText(), is(test_meeting_maker.getName()));
    }
}
