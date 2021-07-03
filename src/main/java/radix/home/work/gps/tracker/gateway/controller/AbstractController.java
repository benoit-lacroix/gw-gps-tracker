package radix.home.work.gps.tracker.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import radix.home.work.gps.tracker.gateway.exception.InternalException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @ExceptionHandler
    protected ResponseEntity<String> handleException(Exception t) {
        log.error("Exception handled:", t);
        if (t instanceof InternalException) {
            InternalException ie = (InternalException) t;
            return new ResponseEntity<>(ie.getErrorMessage(), ie.getHttpStatus());
        } else {
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
