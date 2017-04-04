package us;

import org.springframework.beans.factory.annotation.Autowired;
import us.model.User;
import us.repository.UserRepository;

/**
 * Created by jihun on 2017. 4. 4..
 */
public class HaveUserBaseTest extends BaseTest {
    @Autowired
    protected UserRepository userRepository;
    protected User test_meeting_maker;

    @Override
    public void setUp() {
        test_meeting_maker = new User("test@email.com", "test meeting maker", "test account number", "test password");
        if(!userRepository.exists(test_meeting_maker.getSsoId()))
            userRepository.save(test_meeting_maker);
        super.setUp();
    }

}
