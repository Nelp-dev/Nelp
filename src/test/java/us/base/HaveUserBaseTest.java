package us.base;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import us.model.User;
import us.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class HaveUserBaseTest extends BaseTest {
    @Autowired
    protected UserRepository userRepository;
    protected List<User> test_users = new ArrayList<>();

    private void addTestUser() {
        int user_index = test_users.size();
        test_users.add(new User(
                "test" + user_index + "@email.com",
                "test meeting maker" + user_index,
                "test account number" + user_index,
                "test password" + user_index));
        userRepository.save(test_users.get(user_index));
    }

    @Override
    public void setUp() {
        for (int i = test_users.size(); i < 3; i++)
            addTestUser();

        super.setUp();
    }

    public void login(User user) {
        driver.get(BASE_URL);
        driver.findElement(By.id("login_btn")).click();
        driver.findElement(By.id("login_email_input")).sendKeys(user.getSsoId());
        driver.findElement(By.id("login_password_input")).sendKeys(user.getPassword());
        driver.findElement(By.id("login_submit_btn")).click();
    }
    public void logout() {
        driver.get(BASE_URL);
        driver.findElement(By.id("logout_btn")).click();
    }

    public void participate(User user, String meeting_url) {
        login(user);

        driver.get(meeting_url);
        driver.findElement(By.id("join_meeting_btn")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void addPayment(String user_name, String amount, String meeting_url) {
        driver.get(meeting_url);
        driver.findElement(By.id("add_payment_btn")).click();
        List<WebElement> options = driver.findElements(By.id("payment_owner_name"));
        for(WebElement option : options) {
            if(option.getText().equals(user_name))
                option.click();
        }
        try{
            Thread.sleep(100);
        }catch(Exception e){}
        driver.findElement(By.id("payment_amount")).clear();
        driver.findElement(By.id("payment_amount")).sendKeys(amount);
        driver.findElement(By.id("payment_name")).sendKeys("test payment name");
        driver.findElement(By.id("payment_submit_button")).click();
    }
}
