package radix.home.work.gps.tracker.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException implements InternalException {
    private final String errorMessage;
    private final HttpStatus httpStatus;


    public CustomException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public CustomException(HttpStatus httpStatus) {
        this.errorMessage = httpStatus.getReasonPhrase();
        this.httpStatus = httpStatus;
    }
}
