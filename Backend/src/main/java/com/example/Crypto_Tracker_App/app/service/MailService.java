package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.entity.VerificationEmail;
import com.example.Crypto_Tracker_App.app.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    void sendMail(VerificationEmail verificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("cyptoTracker@gmail.com");
            messageHelper.setTo(verificationEmail.getToEmail());
            messageHelper.setSubject(verificationEmail.getSubject());
            messageHelper.setText(verificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new AppException("Exception when sending mail to " + verificationEmail.getToEmail(), e);
        }
    }
}
