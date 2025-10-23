package vn.jasmine.security.exception.handler;

import vn.jasmine.exception.repsonse.ErrorResponseBody;
import vn.jasmine.security.exception.instance.BlackListTokenException;
import vn.jasmine.security.exception.instance.ExpiredAccessTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class JWTExceptionHandler {

    @ExceptionHandler(value = BlackListTokenException.class)
    public ResponseEntity<ErrorResponseBody> handleBlackListTokenException(BlackListTokenException e) {
        ErrorResponseBody errorMessage = new ErrorResponseBody(
                406,
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorMessage);
    }

    @ExceptionHandler(value = ExpiredAccessTokenException.class)
    public ResponseEntity<ErrorResponseBody> handleExpiredAccessTokenException(ExpiredAccessTokenException e) {
        ErrorResponseBody errorMessage = new ErrorResponseBody(
                401,
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

}