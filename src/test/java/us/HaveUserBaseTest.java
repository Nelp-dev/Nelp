package us;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import us.model.User;
import us.repository.UserRepository;

public class HaveUserBaseTest extends BaseTest {
    @Autowired
    protected UserRepository userRepository;
    protected User test_meeting_maker;
    protected User test_meeting_participation;

    @Override
    public void setUp() {
        test_meeting_maker = new User(
                "test@email.com",
                "test meeting maker",
                "test account number",
                "test password");
        if(!userRepository.exists(test_meeting_maker.getSsoId()))
            userRepository.save(test_meeting_maker);
        test_meeting_participation = new User(
                "test2@email.com",
                "test meeting participation",
                "test account number2",
                "test password");
        if(!userRepository.exists(test_meeting_participation.getSsoId()))
            userRepository.save(test_meeting_participation);

        super.setUp();
    }

    public void login(User user) {
        driver.get(BASE_URL);
        driver.findElement(By.id("login_btn")).click();
        driver.findElement(By.id("login_email_input")).sendKeys(user.getSsoId());
        driver.findElement(By.id("login_password_input")).sendKeys(user.getPassword());
        driver.findElement(By.id("login_submit_btn")).click();
    }
}
