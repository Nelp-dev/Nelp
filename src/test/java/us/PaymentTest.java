package us;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.base.HaveUserBaseTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentTest extends HaveUserBaseTest {
    public PaymentTest() {
        START_URL = BASE_URL + "meetings/2/";
    }

    @Override
    public void setUp() {
        super.setUp();
        super.login(test_users.get(0));

        driver.findElement(By.id("meeting_new_btn")).click();
        driver.findElement(By.id("meeting_name_input")).sendKeys("test meeting name");
        driver.findElement(By.id("meeting_location_input")).sendKeys("test meeting location");
        driver.findElement(By.id("meeting_time_input")).sendKeys("test meeting time");
        driver.findElement(By.id("meeting_submit_btn")).click();

        for(int i=1; i<test_users.size(); i++) {
            super.logout();
            participate(test_users.get(i), START_URL);
        }
    }

    @After
    public void tearDown() {
        super.logout();
    }

    @Test
    public void test_show_payment_info_in_meeting_detail_page() {
        super.logout();
        super.login(test_users.get(0));

        driver.get(START_URL);

        addPayment(test_users.get(0).getName(), "30000", START_URL);

        List<WebElement> receive_elements = driver.findElement(By.id("money_to_receive_list")).findElements(By.tagName("tr"));
        for (int i = 1; i < test_users.size(); i++) {
            Assert.assertThat(receive_elements.get(i - 1).findElements(By.tagName("td")).get(1).getText(), is("10000"));
        }
    }
}
