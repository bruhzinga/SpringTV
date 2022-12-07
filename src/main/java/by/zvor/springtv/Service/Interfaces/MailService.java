package by.zvor.springtv.Service.Interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;
    ExecutorService executor = Executors.newFixedThreadPool(5);

    public void SendPasswordByEmail(String email, String password) {
        executor.submit(() ->
        {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Password");
            msg.setText("Your password is " + password);
            javaMailSender.send(msg);
        });

    }
}




