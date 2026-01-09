package com.esia.big_shop_backend.infrastrucute.email;

import com.esia.big_shop_backend.application.port.output.EmailPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAdapter implements EmailPort {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
        // TODO: Implement email with attachment functionality
        log.warn("Email with attachment not yet implemented");
        throw new UnsupportedOperationException("Email with attachment not yet implemented");
    }

    @Override
    public void sendVerificationEmail(String to, String verificationLink) {
        String subject = "Verify your email address";
        String body = String.format(
                "Hello,\n\n" +
                "Please click the link below to verify your email address:\n\n" +
                "%s\n\n" +
                "If you did not create an account, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Big Shop Team",
                verificationLink
        );
        sendEmail(to, subject, body);
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        String subject = "Reset your password";
        String body = String.format(
                "Hello,\n\n" +
                "We received a request to reset your password. " +
                "Please click the link below to reset your password:\n\n" +
                "%s\n\n" +
                "If you did not request a password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Big Shop Team",
                resetLink
        );
        sendEmail(to, subject, body);
    }
}
