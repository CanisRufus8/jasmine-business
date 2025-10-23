package vn.jasmine.service.mail;

import vn.jasmine.constants.JasmineMessageConst;
import vn.jasmine.dto.both.operation.mail.EmailDetailDto;
import vn.jasmine.exception.instance.SendingMailFailedException;
import vn.jasmine.security.service.IUserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements IEmailSenderService {

    private final JavaMailSender javaMailSender;
    private final IUserService userService;

    private static final String BASIC_NUMBER_CHAIN = "0123456789";
    private static final int OTP_LENGTH = 6;

    @Override
    public String sendForgotPassEmail(String email) {

        if (userService.existUserWithEmail(email)) {
            String response = "/reset-password?token=" + userService.getResetPasswordToken(email);
            EmailDetailDto emailDetailDto = new EmailDetailDto();
            emailDetailDto.setMailFrom("info.silvafarm@gmail.com");
            emailDetailDto.setMailTo(email);
            emailDetailDto.setMailSubject("SILVA FARM - RESET YOUR PASSWORD");
            emailDetailDto.setMailContent(response);
            sendEmail(emailDetailDto);

            return response;
        } else {
            throw new SendingMailFailedException(String.format(JasmineMessageConst.INVALID_EMAIL, email));
        }

    }

    @Override
    public void sendOTPEmail(EmailDetailDto emailDetailDto) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String htmlContent = "<div>This is the OTP for sign up: <strong> " + emailDetailDto.getMailContent() + "</strong></div>";
            mimeMessage.setContent(htmlContent, "text/html; charset=UTF-8");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessageHelper.setSubject(emailDetailDto.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(emailDetailDto.getMailFrom()));
            mimeMessageHelper.setTo(emailDetailDto.getMailTo());
            mimeMessageHelper.setText(htmlContent, true);
            mimeMessageHelper.setReplyTo(emailDetailDto.getMailFrom());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw new SendingMailFailedException(JasmineMessageConst.SENDING_MAIL_FAILED);
        }
    }

    @Override
    public String sendSignUpEmail(String email) {

        String otp = generateOtp();
        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setMailFrom("info.silvafarm@gmail.com");
        emailDetailDto.setMailTo(email);
        emailDetailDto.setMailSubject("SILVA FARM - SIGN UP YOUR ACCOUNT");
        emailDetailDto.setMailContent(otp);
        sendOTPEmail(emailDetailDto);

        return otp;
    }

    private String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        Random random = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(BASIC_NUMBER_CHAIN.length());
            char digit = BASIC_NUMBER_CHAIN.charAt(index);
            otp.append(digit);
        }

        return String.valueOf(otp);
    }


    private void sendEmail(EmailDetailDto emailDetailDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String htmlContent = "<div>Please visit this link to update you password: <a href=\"" + "http://localhost:3000" + emailDetailDto.getMailContent() + "\">Reset password</a></div>";
            mimeMessage.setContent(htmlContent, "text/html; charset=UTF-8");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
//        mimeMessageHelper.setSubject(mail.getMailSubject());
//        mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
//        mimeMessageHelper.setTo(mail.getMailTo());
//        mimeMessageHelper.setText(htmlContent, true);
//        mimeMessageHelper.setReplyTo(mail.getMailFrom());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

}
