package vn.jasmine.service.mail;


import vn.jasmine.dto.both.operation.mail.EmailDetailDto;

public interface IEmailSenderService {

    String sendForgotPassEmail(String email);

    void sendOTPEmail(EmailDetailDto emailDetailDto);

    String sendSignUpEmail(String email);

}