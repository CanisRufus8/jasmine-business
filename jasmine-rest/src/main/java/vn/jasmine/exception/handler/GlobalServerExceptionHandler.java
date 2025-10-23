package vn.jasmine.exception.handler;

import vn.jasmine.exception.instance.GlobalServerException;
import vn.jasmine.exception.repsonse.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class GlobalServerExceptionHandler {

    @ExceptionHandler(value = GlobalServerException.class)
    public ResponseEntity<ErrorResponseBody> handleGlobalException(GlobalServerException e) {

        ErrorResponseBody errorMessage = new ErrorResponseBody(
                500,
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
