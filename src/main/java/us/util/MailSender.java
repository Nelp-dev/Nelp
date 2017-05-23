package us.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * Created by jihun on 2017. 5. 23..
 */
public class MailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    private void send() {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo("cjh5414@gmail.co.uk");
            helper.setReplyTo("someone@localhost");
            helper.setFrom("someone@localhost");
            helper.setSubject("Lorem ipsum");
            helper.setText("Lorem ipsum dolor sit amet [...]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }
}
