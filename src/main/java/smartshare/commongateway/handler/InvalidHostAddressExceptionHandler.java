package smartshare.commongateway.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smartshare.commongateway.exception.InvalidHostAddressException;


@RestControllerAdvice
public class InvalidHostAddressExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidHostAddressException(InvalidHostAddressException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}