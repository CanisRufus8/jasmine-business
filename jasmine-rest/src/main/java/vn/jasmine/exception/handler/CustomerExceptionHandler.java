package vn.jasmine.exception.handler;

import vn.jasmine.exception.instance.ProductNotFoundException;
import vn.jasmine.exception.repsonse.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleProductNotFoundException(ProductNotFoundException e) {
        ErrorResponseBody errorMessage = new ErrorResponseBody(
                400,
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
