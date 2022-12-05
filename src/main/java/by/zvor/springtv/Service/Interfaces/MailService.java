package by.zvor.springtv.Service.Interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void SendPasswordByEmail(String email, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Password");
        msg.setText("Your password is " + password);
        javaMailSender.send(msg);
    }
}


