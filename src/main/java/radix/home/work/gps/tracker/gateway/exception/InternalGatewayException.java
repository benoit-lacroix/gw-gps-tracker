package radix.home.work.gps.tracker.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@SuppressWarnings("java:S1170") // Constants used in non-static context (instance of exceptions)
public class InternalGatewayException extends RuntimeException implements InternalException {

    private final String errorMessage = "Internal Gateway Error";

    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

}
