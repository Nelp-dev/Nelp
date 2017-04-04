package us;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.model.User;
import us.repository.UserRepository;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingTest extends BaseTest{
    @Autowired
    private UserRepository userRepository;
    private User test_meeting_maker;

    @Override
    public void setUp(){
        super.setUp();
        test_meeting_maker = new User("test@email.com", "test meeting maker", "test account number", "test password");
        if(!userRepository.exists(test_meeting_maker.getSsoId()))
            userRepository.save(test_meeting_maker);
    }

    @Test
    public void test_user_create_meeting(){
        driver.get(baseURL);
        driver.findElement(By.id("login_btn")).click();
        driver.findElement(By.id("login_email_input")).sendKeys(test_meeting_maker.getSsoId());
        driver.findElement(By.id("login_password_input")).sendKeys(test_meeting_maker.getPassword());
        driver.findElement(By.id("login_submit_btn")).click();

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
        Assert.assertThat(driver.findElement(By.id("meeting_url")).getText(), is(baseURL + "meetings/1"));

        Assert.assertThat(driver.findElement(By.id("meeting_participant")).getText(), is(test_meeting_maker.getName()));
    }
}
