package radix.home.work.gps.tracker.gateway.exception;

import org.springframework.http.HttpStatus;

public interface InternalException {

    String getErrorMessage();

    HttpStatus getHttpStatus();
}
