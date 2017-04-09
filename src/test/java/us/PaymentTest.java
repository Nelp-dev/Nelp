package us;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentTest extends HaveUserBaseTest {
    public PaymentTest() {
        START_URL = BASE_URL + "meetings/1/";
    }

    @Override
    public void setUp() {
        super.setUp();

        super.login(test_meeting_maker);

        driver.findElement(By.id("meeting_new_btn")).click();

        driver.findElement(By.id("meeting_name_input")).sendKeys("test meeting name");
        driver.findElement(By.id("meeting_location_input")).sendKeys("test meeting location");
        driver.findElement(By.id("meeting_time_input")).sendKeys("test meeting time");
        driver.findElement(By.id("meeting_submit_btn")).click();

        driver.get(BASE_URL);
        driver.findElement(By.id("logout_btn")).click();

        login(test_meeting_participation);

        driver.get(START_URL);
        driver.findElement(By.id("join_meeting_btn")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test
    public void test_show_payment_info_in_meeting_detail_page() {

    }
}
